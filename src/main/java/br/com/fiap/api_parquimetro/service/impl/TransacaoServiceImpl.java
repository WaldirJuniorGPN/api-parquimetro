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
    public TransacaoIniciadaResponseDto iniciarTransacaoTempoFlexivel(TransacaoRequestFlexivelDto dto) {
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TipoTransacao.TEMPO_FLEXIVEL, null);
    }

    @Override
    public TransacaoIniciadaResponseDto iniciarTransacaoTempoFixo(TransacaoRequestFixoDto dto) {
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TipoTransacao.TEMPO_FIXO, dto.duracao());
    }

    @Override
    public TransacaoFinalizadaResponseDto finalizarTransacao(Long id) {
        var transacao = this.buscarTransacao(id);
        transacao.setHoraDaSaida(LocalDateTime.now());
        var veiculo = transacao.getVeiculo();
        var parquimetro = transacao.getParquimetro();

        processarSaida(transacao, veiculo, parquimetro, transacao.getHoraDaSaida());

        this.parquimetroService.liberarParquimetro(parquimetro);
        this.salvarNoBanco(transacao);

        log.debug(ConstantesUtils.SAIDA_REGISTRADA_TRANSACAO, id);
        return new TransacaoFinalizadaResponseDto(transacao);
    }

    @Override
    public Page<TransacaoPagamentoPendenteResponseDto> listarTransacoesPendentes(Pageable pageable) {
        return this.transacaoRepository.findAllByPagamentoPendenteTrueAndAtivoTrue(pageable)
                .orElseThrow(this::throwPropertyReferenceException)
                .map(TransacaoPagamentoPendenteResponseDto::new);
    }

    @Override
    public Page<TransacaoFinalizadaResponseDto> listarTransacoesConcluidas(Pageable pageable) {
        return this.transacaoRepository.findAllByPagamentoPendenteFalseAndAtivoTrue(pageable)
                .orElseThrow(this::throwPropertyReferenceException)
                .map(TransacaoFinalizadaResponseDto::new);
    }

    @Override
    @Cacheable(value = "transacoes", key = "#id")
    public Object buscarPorId(Long id) {
        var transacao = this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
        return transacao.isPagamentoPendente() ? new TransacaoPagamentoPendenteResponseDto(transacao) : new TransacaoFinalizadaResponseDto(transacao);
    }

    @Cacheable(value = "transacoes", key = "#id")
    public Transacao buscarTransacao(Long id) {
        return this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
    }

    private TransacaoIniciadaResponseDto registrarEntrada(Long veiculoId, Long parquimetroId, TipoTransacao tipo, Integer duracao){
        var transacao = new Transacao();
        var veiculo = this.veiculoService.buscarVeiculo(veiculoId);
        var parquimetro = this.parquimetroService.buscarParquimetro(parquimetroId);
        this.parquimetroService.ocuparParquimetro(parquimetro);

        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(tipo);

        if (tipo == TipoTransacao.TEMPO_FIXO) {
            transacao.setTempoEstacionado(Duration.ofHours(duracao));
            var valorPago = this.pagamentoService.calcularValorFixo(transacao.getInputDate(), duracao, parquimetro.getTarifa());
            transacao.setValorPago(valorPago);
            transacao.setPagamentoPendente(false);
        }

        this.salvarNoBanco(transacao);
        log.debug(ConstantesUtils.ENTRADA_REGISTRADA_VEICULO, veiculoId);
        return new TransacaoIniciadaResponseDto(transacao);
    }

    private void processarSaida(Transacao transacao, Veiculo veiculo, Parquimetro parquimetro, LocalDateTime dataHoraSaida) {
        if (transacao.getTipo() == TipoTransacao.TEMPO_FLEXIVEL) {
            var valorPago = this.pagamentoService.calcularValorFlexivel(transacao.getInputDate(), dataHoraSaida, parquimetro.getTarifa());
            transacao.setTempoEstacionado(Duration.between(transacao.getInputDate(), dataHoraSaida));
            this.pagamentoService.processarPagamento(transacao, valorPago);
        } else if (transacao.getTipo() == TipoTransacao.TEMPO_FIXO) {
            var horaDaEntrada = transacao.getInputDate();
            var duracaoEstabelecida = transacao.getTempoEstacionado();
            var duracaoAtual = Duration.between(horaDaEntrada, dataHoraSaida);

            if (duracaoAtual.toHours() > duracaoEstabelecida.toHours()) {
                var valorPago = this.pagamentoService.calcularValorFixo(horaDaEntrada, (int) duracaoEstabelecida.toHours(), parquimetro.getTarifa());
                valorPago = this.pagamentoService.calcularTarifaAdicional(valorPago, horaDaEntrada, duracaoEstabelecida.toHours(), parquimetro.getTarifa());
                transacao.setTempoEstacionado(duracaoAtual);
                this.pagamentoService.processarPagamento(transacao, valorPago);
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
