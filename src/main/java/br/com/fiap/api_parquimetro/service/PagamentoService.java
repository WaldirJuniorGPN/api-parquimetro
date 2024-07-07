package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PagamentoService {
    void processar(Transacao transacao, BigDecimal valorPago);

    BigDecimal calcularValorFlexivel(LocalDateTime horaEntrdada, LocalDateTime horaSaida, Calculadora calculadora);

    BigDecimal calcularValorFixo(LocalDateTime horaDaEntrada, Integer duracao,  Calculadora calculadora);

    BigDecimal calcularAdicional(BigDecimal valorAPagar, LocalDateTime horaDaEntrada, long duracao, Calculadora calculadora);
}
