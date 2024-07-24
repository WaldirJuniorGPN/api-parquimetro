package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PagamentoService {
    void processarPagamento(Transacao transacao, BigDecimal valorPago);

    BigDecimal calcularValorFlexivel(LocalDateTime horaEntrdada, LocalDateTime horaSaida, Tarifa tarifa);

    BigDecimal calcularValorFixo(LocalDateTime horaDaEntrada, Integer duracao,  Tarifa tarifa);

    BigDecimal calcularTarifaAdicional(LocalDateTime horaDaEntrada, long duracao, Tarifa tarifa);
}
