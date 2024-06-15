package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculadoraRequestDto(
        @NotNull
        @DecimalMin(value = "0.00", inclusive = true)
        BigDecimal tarifaPorHora,
        @NotNull
        @DecimalMin(value = "0.00", inclusive = true)
        BigDecimal tarifaAdicional
) {
}
