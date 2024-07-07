package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculadoraRequestDto(
        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal tarifaPorHora,
        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal tarifaAdicional,
        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal tarifaFixaPorHora
) {
}
