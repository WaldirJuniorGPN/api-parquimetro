package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Veiculo;

import java.time.LocalDateTime;

public record VeiculoResponseDto(Long id, String placaDoveiculo, String modelo, String cor) {
    public VeiculoResponseDto(Veiculo veiculo) {
        this(veiculo.getId(), veiculo.getPlacaDoVeiculo(), veiculo.getModelo(), veiculo.getCor());
    }
}
