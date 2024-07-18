package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Tarifa;

import java.math.BigDecimal;

public record TarifaResponseDto(Long id, BigDecimal tarifaFlexivelPorHora, BigDecimal tarifaAdicional,
                                BigDecimal tarifaFixaPorHora) {
    public TarifaResponseDto(Tarifa tarifa) {
        this(tarifa.getId(), tarifa.getTarifaFlexivelPorHora(), tarifa.getTarifaFixaPorHora(), tarifa.getTarifaAdicional());
    }
}
