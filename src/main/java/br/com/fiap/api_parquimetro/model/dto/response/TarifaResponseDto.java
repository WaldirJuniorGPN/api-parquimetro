package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Tarifa;

import java.math.BigDecimal;

public record TarifaResponseDto(Long id, BigDecimal tarifaPorHora, BigDecimal tarifaAdicional) {
    public TarifaResponseDto(Tarifa tarifa) {
        this(tarifa.getId(), tarifa.getTarifaPorHora(), tarifa.getTarifaAdicional());
    }
}
