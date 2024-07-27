package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.response.ReciboResponseDto;
import br.com.fiap.api_parquimetro.service.EmailService;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.service.ReciboService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static br.com.fiap.api_parquimetro.model.enums.TipoTransacao.TEMPO_FIXO;
import static br.com.fiap.api_parquimetro.utils.ConstantesUtils.RECIBO_GERADO;

@RequiredArgsConstructor
@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {

    private final ReciboService reciboService;
    private final EmailService emailService;

    @Override
    public void processarPagamento(Transacao transacao, BigDecimal valorAPager) {
        log.info(ConstantesUtils.PAGAMENTO_PROCESSADO_SUCESSO);

        var reciboResponseDto = reciboService.gerarRecibo(transacao);
        enviarEmail(transacao, valorAPager, reciboResponseDto);
        log.debug(RECIBO_GERADO, reciboResponseDto);
    }

    private void enviarEmail(Transacao transacao, BigDecimal valorPago, ReciboResponseDto reciboResponseDto) {
        var emailTemplate = emailService.getTemplate(transacao, valorPago, reciboResponseDto);
        emailService.enviarEmail(transacao.getVeiculo().getMotorista().getEmail(), "Recibo de Estacionamento", emailTemplate);
    }



    @Override
    public BigDecimal calcularValorFlexivel(Transacao transacao, Tarifa tarifa) {

        var duracao = Duration.between(transacao.getInputDate(), transacao.getHoraDaSaida());
        if (transacao.getTipo().equals(TEMPO_FIXO)) {
            duracao = duracao.minus(Duration.ofHours(tarifa.getDuracaoFixa()));
        }
        var horas = duracao.toHours();
        var minutos = duracao.toMinutesPart();
        var totalHoras = horas + (minutos >= 0 ? 1 : 0);

        return tarifa.getTarifaVariavelPorHora().multiply(BigDecimal.valueOf(totalHoras));
    }

    @Override
    public BigDecimal calcularValorFixo(LocalDateTime horaDaEntrada, Integer duracao, Tarifa tarifa) {
        return tarifa.getTarifaFixa().multiply(BigDecimal.valueOf(duracao));
    }

    @Override
    public BigDecimal calcularTarifaAdicional(LocalDateTime horaDaEntrada, long duracao, Tarifa tarifa) {
        var duracaoExcedente = Duration.between(horaDaEntrada.plusHours(duracao), LocalDateTime.now());
        var minutosExcedentes = duracaoExcedente.toMinutes();
        return tarifa.getTarifaVariavelPorHora().multiply(BigDecimal.valueOf(minutosExcedentes));
    }
}