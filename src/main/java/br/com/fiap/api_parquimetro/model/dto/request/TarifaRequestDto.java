package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TarifaRequestDto(
        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal tarifaFixa,
        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal tarifaVariavelPorHora,
        @NotNull
        @DecimalMin(value = "0.00")
        int duracaoFixa
) {
}
