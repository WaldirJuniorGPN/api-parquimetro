package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.enums.TipoPagamento;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestFixoDto(
        @NotNull
        Long veiculoId,
        @NotNull
        Long parquimetroId,
        @NotNull
        TipoPagamento tipoPagamento
) {
}
