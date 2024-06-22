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
    public BigDecimal calcularValor(LocalDateTime horaEntrada, LocalDateTime horaSaida, Calculadora calculadora) {
        var duracao = Duration.between(horaEntrada, horaSaida);
        var horas = duracao.toHours();
        var valorTotal = calculadora.getTarifaPorHora().multiply(BigDecimal.valueOf(horas));
        var minutosExcedentes = duracao.toMinutes() % 60;
        if (minutosExcedentes > 0) {
            valorTotal = valorTotal.add(calculadora.getTarifaAdicional());
        }
        return valorTotal;
    }
}
