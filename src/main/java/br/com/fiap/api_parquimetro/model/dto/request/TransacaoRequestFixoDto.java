package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransacaoRequestFixoDto(
        @NotNull
        Long veiculoId,
        @NotNull
        Long parquimetroId,
        @NotNull
        @Pattern(regexp = "^[1-9]\\d*$", message = "A duração deve ser de no mínimo 1 hora")
        Integer duracao,
        @NotNull
        @Pattern(regexp = "^(CREDITO|DEBITO|PIX)$", message = "Tipo de pagamento inválido. Os tipos válidos são: CREDITO, DEBITO, PIX")
        TipoPagamento tipoPagamento
) {
}
