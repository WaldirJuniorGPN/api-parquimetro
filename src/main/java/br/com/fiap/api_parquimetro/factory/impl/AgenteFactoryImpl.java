package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.AgenteFactory;
import br.com.fiap.api_parquimetro.model.Agente;
import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import org.springframework.stereotype.Component;

@Component
public class AgenteFactoryImpl implements AgenteFactory {
    @Override
    public Agente criar(AgenteRequestDto dto) {
        var agente = new Agente();
        agente.setNome(dto.nome());
        agente.setMatricula(dto.matricula());
        agente.setAreaDeAtuacao(dto.areaDeAtuacao());
        return agente;
    }

    @Override
    public void atualizar(Agente agente, AgenteRequestDto dto) {
        if (!agente.getNome().equals(dto.nome())) {
            agente.setNome(dto.nome());
        }
        if (!agente.getMatricula().equals(dto.matricula())) {
            agente.setMatricula(dto.matricula());
        }
        if (!agente.getAreaDeAtuacao().equals(dto.areaDeAtuacao())) {
            agente.setAreaDeAtuacao(dto.areaDeAtuacao());
        }
    }
}
