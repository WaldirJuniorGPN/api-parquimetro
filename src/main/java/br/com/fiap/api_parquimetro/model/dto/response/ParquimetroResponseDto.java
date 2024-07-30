package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;

public record ParquimetroResponseDto(Long id, String localizacao, StatusParquimetro statusParquimetro, Long tarifaId) {
    public ParquimetroResponseDto(Parquimetro parquimetro) {
        this(parquimetro.getId(), parquimetro.getLocalizacao(), parquimetro.getStatusParquimetro(), parquimetro.getTarifa().getId());
    }
}
