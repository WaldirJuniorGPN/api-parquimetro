package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;
import br.com.fiap.api_parquimetro.service.validation.ValidStatus;

public record StatusRequestDto(
        @ValidStatus(message = "Status inválido. As opções válidas são: LIVRE ou MANUTENÇÃO.")
        StatusParquimetro statusParquimetro
) {
}
