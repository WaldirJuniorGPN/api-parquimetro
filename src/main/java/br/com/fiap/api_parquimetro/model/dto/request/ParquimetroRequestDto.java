package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ParquimetroRequestDto(
        @NotBlank
        String localizacao,
        @Valid
        StatusRequestDto status
) {
}
