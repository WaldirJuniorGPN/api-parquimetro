package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.service.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {
    @Override
    public void processar(Transacao transacao, BigDecimal valorPago) {
        transacao.setPagamentoPendente(false);
        transacao.setValorPago(valorPago);
        log.info("Pagamento processado com sucesso!");
    }

    @Override
    public BigDecimal calcularValorFlexivel(LocalDateTime horaEntrada, LocalDateTime horaSaida, Calculadora calculadora) {
        var duracao = Duration.between(horaEntrada, horaSaida);
        var horas = duracao.toHours();
        var valorTotal = calculadora.getTarifaPorHora().multiply(BigDecimal.valueOf(horas));
        var minutosExcedentes = duracao.toMinutes() % 60;

        if (minutosExcedentes > 0) {
            valorTotal = this.calcularAdicional(valorTotal, horaEntrada, horas, calculadora);
        }

        return valorTotal;
    }

    @Override
    public BigDecimal calcularValorFixo(LocalDateTime horaDaEntrada, Integer duracao, Calculadora calculadora) {
        return calculadora.getTarifaFixaPorHora().multiply(BigDecimal.valueOf(duracao));
    }

    @Override
    public BigDecimal calcularAdicional(BigDecimal valorAPagar, LocalDateTime horaDaEntrada, long duracao, Calculadora calculadora) {
        var duracaoExcedente = Duration.between(horaDaEntrada.plusHours(duracao), LocalDateTime.now());
        var minutosExcedentes = duracaoExcedente.toMinutes();
        var tarifaAdicional = calculadora.getTarifaAdicional().multiply(BigDecimal.valueOf(minutosExcedentes));
        return valorAPagar.add(tarifaAdicional);
    }
}