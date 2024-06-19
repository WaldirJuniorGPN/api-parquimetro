package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDto(
        @NotNull
        Long veiculoId,
        @NotNull
        Long parquimetroId,
        Long agenteId
) {
}
