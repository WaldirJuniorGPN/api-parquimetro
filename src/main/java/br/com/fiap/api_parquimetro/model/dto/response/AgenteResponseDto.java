package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Agente;

public record AgenteResponseDto(Long id, String matricula, String nome, String areaDeAtuacao) {

    public AgenteResponseDto(Agente agente) {
        this(agente.getId(), agente.getMatricula(), agente.getNome(), agente.getAreaDeAtuacao());
    }
}
