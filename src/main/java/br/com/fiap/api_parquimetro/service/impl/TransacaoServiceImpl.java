package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.TipoTransacao;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntradaTempoFlexivel(TransacaoRequestFlexivelDto dto) {
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TipoTransacao.TEMPO_FLEXIVEL, null);
    }

    @Override
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntradaTempoFixo(TransacaoRequestFixoDto dto) {
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TipoTransacao.TEMPO_FIXO, dto.duracao());
    }

    @Override
    public ResponseEntity<TransacaoFinalizadaResponseDto> registrarSaida(Long id) {
        var transacao = this.buscarTransacao(id);
        var veiculo = transacao.getVeiculo();
        var parquimetro = transacao.getParquimetro();
        var dataHoraSaida = LocalDateTime.now();

        processarSaida(transacao, veiculo, parquimetro, dataHoraSaida);

        this.parquimetroService.liberarParquimetro(parquimetro);
        this.veiculoService.registrarSaida(veiculo, dataHoraSaida);
        this.salvarNoBanco(transacao);

        log.debug(ConstantesUtils.SAIDA_REGISTRADA_TRANSACAO, id);
        return ResponseEntity.ok(new TransacaoFinalizadaResponseDto(transacao));
    }

    @Override
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> buscarTransacoesPendentesDePagamento(Pageable pageable) {
        var page = this.transacaoRepository.findAllByPagamentoPendenteTrueAndAtivoTrue(pageable)
                .orElseThrow(this::throwPropertyReferenceException)
                .map(TransacaoPagamentoPendenteResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<Page<TransacaoFinalizadaResponseDto>> buscarTransacoesPagas(Pageable pageable) {
        var page = this.transacaoRepository.findAllByPagamentoPendenteFalseAndAtivoTrue(pageable)
                .orElseThrow(this::throwPropertyReferenceException)
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

    private ResponseEntity<TransacaoIniciadaResponseDto> registrarEntrada(Long veiculoId, Long parquimetroId, TipoTransacao tipo, Integer duracao){
        var transacao = new Transacao();
        var veiculo = this.veiculoService.buscarVeiculo(veiculoId);
        var parquimetro = this.parquimetroService.buscarParquimetro(parquimetroId);
        this.veiculoService.registrarEntrada(veiculo);
        this.parquimetroService.ocuparParquimetro(parquimetro);

        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(tipo);

        if (tipo == TipoTransacao.TEMPO_FIXO) {
            transacao.setTempoEstacionado(Duration.ofHours(duracao));
            var valorPago = this.pagamentoService.calcularValorFixo(veiculo.getHoraDaEntrada(), duracao, parquimetro.getCalculadora());
            transacao.setValorPago(valorPago);
            transacao.setPagamentoPendente(false);
        }

        this.salvarNoBanco(transacao);
        log.debug(ConstantesUtils.ENTRADA_REGISTRADA_VEICULO, veiculoId);
        return ResponseEntity.ok(new TransacaoIniciadaResponseDto(transacao));
    }

    private void processarSaida(Transacao transacao, Veiculo veiculo, Parquimetro parquimetro, LocalDateTime dataHoraSaida) {
        if (transacao.getTipo() == TipoTransacao.TEMPO_FLEXIVEL) {
            var valorPago = this.pagamentoService.calcularValorFlexivel(veiculo.getHoraDaEntrada(), dataHoraSaida, parquimetro.getCalculadora());
            transacao.setTempoEstacionado(Duration.between(veiculo.getHoraDaEntrada(), dataHoraSaida));
            this.pagamentoService.processar(transacao, valorPago);
        } else if (transacao.getTipo() == TipoTransacao.TEMPO_FIXO) {
            var horaDaEntrada = veiculo.getHoraDaEntrada();
            var duracaoEstabelecida = transacao.getTempoEstacionado();
            var duracaoAtual = Duration.between(horaDaEntrada, dataHoraSaida);

            if (duracaoAtual.toHours() > duracaoEstabelecida.toHours()) {
                var valorPago = this.pagamentoService.calcularValorFixo(horaDaEntrada, (int) duracaoEstabelecida.toHours(), parquimetro.getCalculadora());
                valorPago = this.pagamentoService.calcularAdicional(valorPago, horaDaEntrada, duracaoEstabelecida.toHours(), parquimetro.getCalculadora());
                transacao.setTempoEstacionado(duracaoAtual);
                this.pagamentoService.processar(transacao, valorPago);
            } else {
                transacao.setTempoEstacionado(duracaoAtual);
            }
        }
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        log.error(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }

    private ControllerNotFoundException throwNotFoundException() {
        log.error(ConstantesUtils.ENTIDADE_NAO_ENCONTRADA);
        return new ControllerNotFoundException(ConstantesUtils.ENTIDADE_NAO_ENCONTRADA);
    }

    private void salvarNoBanco(Transacao transacao) {
        this.transacaoRepository.save(transacao);
    }
}
