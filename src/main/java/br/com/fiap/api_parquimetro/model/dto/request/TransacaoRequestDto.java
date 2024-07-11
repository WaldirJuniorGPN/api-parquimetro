package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.service.validation.NotPastValid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransacaoRequestDto(
        @NotNull
        Long veiculoId,
        @NotNull
        @NotPastValid
        LocalDateTime horaDaEntrada,
        @NotNull
        Long parquimetroId,
        Long agenteId
) {
}
