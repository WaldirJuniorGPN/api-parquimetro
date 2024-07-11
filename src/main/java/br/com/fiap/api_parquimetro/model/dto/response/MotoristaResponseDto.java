package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Motorista;

public record MotoristaResponseDto(Long id, String cnh, String nome, String telefone, String email) {

    public MotoristaResponseDto(Motorista motorista) {
        this(motorista.getId(), motorista.getCnh(), motorista.getNome(), motorista.getTelefone(), motorista.getEmail());
    }
}
