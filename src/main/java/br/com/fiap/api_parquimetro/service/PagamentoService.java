package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PagamentoService {
    void processar(Transacao transacao, BigDecimal valorPago);

    BigDecimal calcularValor(LocalDateTime horaEntrdada, LocalDateTime horaSaida, Calculadora calculadora);
}
