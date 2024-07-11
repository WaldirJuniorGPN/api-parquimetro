package br.com.fiap.api_parquimetro.scheduler;

import br.com.fiap.api_parquimetro.model.TipoTransacao;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.EmailService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertaScheduler {

    private final TransacaoRepository transacaoRepository;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void verificarTransacoes() {
        log.debug(ConstantesUtils.VERIFICANDO_TRANSACOES_FIXAS);

        List<Transacao> transacaos = transacaoRepository.findAllByPagamentoPendenteTrueAndTipo(TipoTransacao.TEMPO_FIXO);

        for (Transacao transacao : transacaos) {
            Duration duracaoRestante = Duration.between(LocalDateTime.now(), transacao.getInputDate().plus(transacao.getTempoEstacionado()));
            if (duracaoRestante.toMinutes() <= ConstantesUtils.TEMPO_ALERTA_MINUTOS) {
                enviarAlerta(transacao);
            }
        }
    }

    private void enviarAlerta(Transacao transacao) {
        String mensagem = String.format(ConstantesUtils.MENSAGEM_ALERTA_TEMPO_ESTACIONADO, transacao.getVeiculo().getPlacaDoVeiculo(), transacao.getParquimetro().getId());
        emailService.enviarEmail(transacao.getVeiculo().getMotorista().getEmail(), ConstantesUtils.ASSUNTO_ALERTA_TEMPO_ESTACIONADO, mensagem);
        log.debug(ConstantesUtils.ALERTA_ENVIADO_PARA_CONDUTOR, transacao.getVeiculo().getPlacaDoVeiculo());
    }
}
