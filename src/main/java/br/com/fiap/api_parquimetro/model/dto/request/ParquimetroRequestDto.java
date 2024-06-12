package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.Status;
import jakarta.validation.constraints.NotBlank;

public record ParquimetroRequestDto(
        @NotBlank
        String localizacao,

        Status status
) {


}
