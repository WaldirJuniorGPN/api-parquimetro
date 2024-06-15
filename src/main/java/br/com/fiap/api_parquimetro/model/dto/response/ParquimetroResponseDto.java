package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.Status;

public record ParquimetroResponseDto(Long id, String localizacao, Status status) {
    public ParquimetroResponseDto(Parquimetro parquimetro) {
        this(parquimetro.getId(), parquimetro.getLocalizacao(), parquimetro.getStatus());
    }
}
