package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.model.*;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final ParquimetroRepository parquimetroRepository;
    private final PagamentoService pagamentoService;

    @Override
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntrada(TransacaoRequestDto dto) {
        var transacao = new Transacao();
        var veiculo = this.buscarVeiculo(dto.veiculoId());
        var parquimetro = this.buscarParquimetro(dto.parquimetroId());

        veiculo.setHoraDaEntrada(LocalDateTime.now());
        parquimetro.setStatus(Status.OCUPADO);
        transacao.setVeiculo(veiculo);

        this.transacaoRepository.save(transacao);
        this.veiculoRepository.save(veiculo);
        this.parquimetroRepository.save(parquimetro);

        log.debug("Entrada registrada para o veículo ID: {}", dto.veiculoId());
        return ResponseEntity.ok(new TransacaoIniciadaResponseDto(transacao));
    }

    @Override
    public ResponseEntity<TransacaoFinalizadaResponseDto> registrarSaida(Long id) {
        var transacao = this.buscarTransacao(id);
        var veiculo = transacao.getVeiculo();
        var calculadora = transacao.getParquimetro().getCalculadora();
        var parquimetro = transacao.getParquimetro();
        parquimetro.setStatus(Status.LIVRE);
        veiculo.setHoraDaSaida(LocalDateTime.now());

        var ducacao = Duration.between(veiculo.getHoraDaEntrada(), veiculo.getHoraDaSaida());
        var valorPago = this.calcularValor(ducacao, calculadora);

        transacao.setValorPago(valorPago);
        transacao.setPagamentoPendente(false);

        this.pagamentoService.processar();

        this.transacaoRepository.save(transacao);
        this.parquimetroRepository.save(parquimetro);

        log.debug("Saída registrada para a transação ID: {}", id);
        return ResponseEntity.ok(new TransacaoFinalizadaResponseDto(transacao));
    }

    @Override
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> buscarTransacoesPendentesDePagamento(Pageable pageable) {
        var page = this.transacaoRepository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException)
                .map(TransacaoPagamentoPendenteResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<Page<TransacaoFinalizadaResponseDto>> buscarTransacoesPagas(Pageable pageable) {
        var page = this.transacaoRepository.findAllByPagamentoPendenteFalseAndAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException)
                .map(TransacaoFinalizadaResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<?> buscarPorId(Long id) {
        var transacao = this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
        var dto = transacao.isPagamentoPendente() ? new TransacaoPagamentoPendenteResponseDto(transacao) : new TransacaoFinalizadaResponseDto(transacao);
        return ResponseEntity.ok(dto);
    }

    private BigDecimal calcularValor(Duration duracao, Calculadora calculadora) {
        var horas = duracao.toHours();
        var valorTotal = calculadora.getTarifaPorHora().multiply(BigDecimal.valueOf(horas));

        var minutosExcedentes = duracao.toMinutes() % 60;
        if (minutosExcedentes > 0) {
            valorTotal = valorTotal.add(calculadora.getTarifaAdicional());
        }
        return valorTotal;
    }

    @Cacheable(value = "parquimetros", key = "#id")
    private Parquimetro buscarParquimetro(Long id) {
        return this.parquimetroRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
    }

    @Cacheable(value = "veiculos", key = "#id")
    private Veiculo buscarVeiculo(Long id) {
        return this.veiculoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
    }

    @Cacheable(value = "transacoes", key = "#id")
    private Transacao buscarTransacao(Long id) {
        return this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        log.error("Parâmetros do Json incorretos");
        return new ControllerPropertyReferenceException("Parâmetros do Json incorretos");
    }

    private ControllerNotFoundException throwNotFoundException() {
        log.error("Entidade não encontrada");
        return new ControllerNotFoundException("Entidade não encontrada");
    }
}
