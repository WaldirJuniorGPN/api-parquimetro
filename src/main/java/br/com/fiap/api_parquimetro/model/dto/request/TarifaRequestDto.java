package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TarifaRequestDto(Long id,
                               @NotBlank
                                 BigDecimal tarifaHora,
                               @NotBlank
                                 BigDecimal tarifaAdicional,
                               @NotBlank
                                 Boolean status) {
}
