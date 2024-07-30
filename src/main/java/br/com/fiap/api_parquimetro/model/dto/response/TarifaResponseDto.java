package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Tarifa;

import java.math.BigDecimal;

public record TarifaResponseDto(Long id, BigDecimal tarifaFixa, BigDecimal tarifaVariavelPorHora, int duracaoFixa) {
    public TarifaResponseDto(Tarifa tarifa) {
        this(tarifa.getId(), tarifa.getTarifaFixa(), tarifa.getTarifaVariavelPorHora(), tarifa.getDuracaoFixa());
    }
}
