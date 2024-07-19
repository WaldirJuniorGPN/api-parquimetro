package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {
    @Override
    public void processarPagamento(Transacao transacao, BigDecimal valorPago) {
        transacao.setPagamentoPendente(false);
        transacao.setValorPago(valorPago);
        log.info(ConstantesUtils.PAGAMENTO_PROCESSADO_SUCESSO);
    }

    @Override
    public BigDecimal calcularValorFlexivel(LocalDateTime horaEntrada, LocalDateTime horaSaida, Tarifa tarifa) {
        var duracao = Duration.between(horaEntrada, horaSaida);
        var horas = duracao.toHours();
        var valorTotal = tarifa.getTarifaFlexivelPorHora().multiply(BigDecimal.valueOf(horas));
        var minutosExcedentes = duracao.toMinutes() % 60;

        if (minutosExcedentes > 0) {
            valorTotal = this.calcularTarifaAdicional(valorTotal, horaEntrada, horas, tarifa);
        }

        return valorTotal;
    }

    @Override
    public BigDecimal calcularValorFixo(LocalDateTime horaDaEntrada, Integer duracao, Tarifa tarifa) {
        return tarifa.getTarifaFixaPorHora().multiply(BigDecimal.valueOf(duracao));
    }

    @Override
    public BigDecimal calcularTarifaAdicional(BigDecimal valorAPagar, LocalDateTime horaDaEntrada, long duracao, Tarifa tarifa) {
        var duracaoExcedente = Duration.between(horaDaEntrada.plusHours(duracao), LocalDateTime.now());
        var minutosExcedentes = duracaoExcedente.toMinutes();
        var tarifaAdicional = tarifa.getTarifaAdicional().multiply(BigDecimal.valueOf(minutosExcedentes));
        return valorAPagar.add(tarifaAdicional);
    }
}