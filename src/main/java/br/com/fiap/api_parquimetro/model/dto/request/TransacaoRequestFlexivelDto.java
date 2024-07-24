package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.enums.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransacaoRequestFlexivelDto(
        @NotNull
        Long veiculoId,
        @NotNull
        Long parquimetroId,
        @NotNull
        @Pattern(regexp = "^(CREDITO|DEBITO)$", message = "Tipo de pagamento inválido. Os tipos válidos são: CREDITO ou DEBITO")
        TipoPagamento tipoPagamento
) {
}
