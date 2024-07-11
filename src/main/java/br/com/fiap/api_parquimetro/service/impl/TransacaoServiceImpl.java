package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final PagamentoService pagamentoService;
    private final VeiculoService veiculoService;
    private final ParquimetroService parquimetroService;

    @Override
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntrada(TransacaoRequestDto dto) {
        var transacao = new Transacao();
        var veiculo = this.veiculoService.buscarVeiculo(dto.veiculoId());
        var parquimetro = this.parquimetroService.buscarParquimetro(dto.parquimetroId());
        this.parquimetroService.ocuparParquimetro(parquimetro);
        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setHoraDaEntrada(dto.horaDaEntrada());
        this.salvarNoBanco(transacao);
        log.debug("Entrada registrada para o veículo ID: {}", dto.veiculoId());
        return ResponseEntity.ok(new TransacaoIniciadaResponseDto(transacao));
    }

    @Override
    public ResponseEntity<TransacaoFinalizadaResponseDto> registrarSaida(Long id) {
        var dataHoraSaida = LocalDateTime.now();
        var transacao = this.buscarTransacao(id);
        var parquimetro = transacao.getParquimetro();
        var valorPago = this.pagamentoService.calcularValor(transacao.getHoraDaEntrada(), dataHoraSaida, parquimetro.getCalculadora());
        this.pagamentoService.processar(transacao, valorPago);
        this.parquimetroService.liberarParquimetro(parquimetro);
        transacao.setHoraDaSaida(dataHoraSaida);
        this.salvarNoBanco(transacao);
        log.debug("Saída registrada para a transação ID: {}", id);
        return ResponseEntity.ok(new TransacaoFinalizadaResponseDto(transacao));
    }

    @Override
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> buscarTransacoesPendentesDePagamento(Pageable pageable) {
        var page = this.transacaoRepository.findAllByPagamentoPendenteTrueAndAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException)
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
    @Cacheable(value = "transacoes", key = "#id")
    public ResponseEntity<?> buscarPorId(Long id) {
        var transacao = this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
        var dto = transacao.isPagamentoPendente() ? new TransacaoPagamentoPendenteResponseDto(transacao) : new TransacaoFinalizadaResponseDto(transacao);
        return ResponseEntity.ok(dto);
    }

    @Cacheable(value = "transacoes", key = "#id")
    public Transacao buscarTransacao(Long id) {
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

    private void salvarNoBanco(Transacao transacao) {
        this.transacaoRepository.save(transacao);
    }
}
