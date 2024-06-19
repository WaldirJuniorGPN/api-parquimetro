package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.Status;
import br.com.fiap.api_parquimetro.service.validation.ValidStatus;

public record StatusRequestDto(
        @ValidStatus(message = "Status inválido. As opções válidas são: LIVRO ou MANUTENCAO.")
        Status status
) {
}
