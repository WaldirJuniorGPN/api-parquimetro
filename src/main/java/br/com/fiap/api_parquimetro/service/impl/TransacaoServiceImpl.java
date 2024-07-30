package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.model.enums.StatusTransacao;
import br.com.fiap.api_parquimetro.model.enums.TipoTransacao;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static br.com.fiap.api_parquimetro.model.enums.StatusParquimetro.LIVRE;
import static br.com.fiap.api_parquimetro.model.enums.StatusTransacao.EM_ABERTO;
import static br.com.fiap.api_parquimetro.model.enums.StatusTransacao.FINALIZADA;
import static br.com.fiap.api_parquimetro.model.enums.TipoPagamento.PIX;
import static br.com.fiap.api_parquimetro.model.enums.TipoTransacao.TEMPO_FIXO;
import static br.com.fiap.api_parquimetro.model.enums.TipoTransacao.TEMPO_FLEXIVEL;
import static br.com.fiap.api_parquimetro.utils.ConstantesUtils.ENTRADA_REGISTRADA_VEICULO;
import static br.com.fiap.api_parquimetro.utils.ConstantesUtils.PARQUIMETRO_INDISPONIVEL;
import static br.com.fiap.api_parquimetro.utils.ConstantesUtils.VEICULO_JA_ESTACIONADO;
import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoServiceImpl implements TransacaoService {
    public static final int HORAS_NO_TEMPO_FLEXIVEL = 1;
    private final TransacaoRepository transacaoRepository;
    private final PagamentoService pagamentoService;
    private final VeiculoService veiculoService;
    private final ParquimetroService parquimetroService;

    @Override
    @Transactional
    public TransacaoIniciadaResponseDto iniciarTransacaoTempoFlexivel(TransacaoRequestFlexivelDto dto) {
        if (dto.tipoPagamento() == PIX) {
            throw new ControllerPropertyReferenceException(ConstantesUtils.PIX_NAO_SUPORTADO);
        }
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TEMPO_FLEXIVEL);
    }

    @Override
    @Transactional
    public TransacaoIniciadaResponseDto iniciarTransacaoTempoFixo(TransacaoRequestFixoDto dto) {
        return registrarEntrada(dto.veiculoId(), dto.parquimetroId(), TEMPO_FIXO);
    }

    @Override
    public TransacaoFinalizadaResponseDto finalizarTransacao(Long id) {
        var transacao = this.buscarTransacao(id);
        if (transacao.getStatus().equals(FINALIZADA)) {
            throw new ControllerPropertyReferenceException(ConstantesUtils.TRANSACAO_FINALIZADA);
        }

        transacao.setHoraDaSaida(LocalDateTime.now());
        var parquimetro = transacao.getParquimetro();

        if (transacao.isPagamentoPendente()) {
            processarSaida(transacao, parquimetro);

        } else {
            transacao.setTempoEstacionado(Duration.between(transacao.getInputDate(), transacao.getHoraDaSaida()));
        }

        this.parquimetroService.liberarParquimetro(parquimetro);
        transacao.setStatus(FINALIZADA);
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

    public Transacao buscarTransacao(Long id) {
        return this.transacaoRepository.findByIdAndAtivoTrue(id).orElseThrow(this::throwNotFoundException);
    }

    @Override
    public Transacao adicionaHoraExtra (Transacao transacao) {
        transacao.setTempoLimite(transacao.getTempoLimite().plusHours(1));
        transacao.setPagamentoPendente(true);

        return transacaoRepository.save(transacao);
    }

    private TransacaoIniciadaResponseDto registrarEntrada(Long veiculoId, Long parquimetroId, TipoTransacao tipo){
        var transacao = new Transacao();
        var veiculo = this.veiculoService.buscarVeiculo(veiculoId);
        var parquimetro = this.parquimetroService.buscarParquimetro(parquimetroId);
        if (!parquimetro.getStatusParquimetro().equals(LIVRE)) {
            throw new ControllerPropertyReferenceException(PARQUIMETRO_INDISPONIVEL);
        }
        if (!transacaoRepository.findByVeiculoIdAndStatus(veiculo.getId(), EM_ABERTO).isEmpty()) {
            throw new ControllerPropertyReferenceException(VEICULO_JA_ESTACIONADO);
        }
        this.parquimetroService.ocuparParquimetro(parquimetro);

        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(tipo);
        transacao.setInputDate(LocalDateTime.now());

        if (tipo == TEMPO_FIXO) {
            var valorAPagar = parquimetro.getTarifa().getTarifaFixa();
            transacao.setTempoLimite(transacao.getInputDate().plusHours(parquimetro.getTarifa().getDuracaoFixa()));
            pagamentoService.processarPagamento(transacao, valorAPagar);
            transacao.setPagamentoPendente(false);
            transacao.setValorPago(valorAPagar);
        } else {
            transacao.setTempoLimite(transacao.getInputDate().plusHours(HORAS_NO_TEMPO_FLEXIVEL));
        }

        this.salvarNoBanco(transacao);
        log.debug(ENTRADA_REGISTRADA_VEICULO, veiculoId);
        return new TransacaoIniciadaResponseDto(transacao);
    }

    private void processarSaida(Transacao transacao, Parquimetro parquimetro) {

        var valorAPagar = this.pagamentoService.calcularValorFlexivel(transacao, parquimetro.getTarifa());
        transacao.setTempoEstacionado(Duration.between(transacao.getInputDate(), transacao.getHoraDaSaida()));
        this.pagamentoService.processarPagamento(transacao, valorAPagar);
        transacao.setPagamentoPendente(false);
        var valorPagoAtual = transacao.getValorPago() != null ? transacao.getValorPago() : ZERO;
        transacao.setValorPago(valorPagoAtual.add(valorAPagar));
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
