package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AgenteRequestDto(
        @NotBlank
        String matricula,
        @NotBlank
        String nome,
        @NotBlank
        String areaDeAtuacao
) {
}
