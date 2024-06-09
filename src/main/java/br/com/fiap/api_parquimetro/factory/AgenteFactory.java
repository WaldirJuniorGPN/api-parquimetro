package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Agente;
import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;

public interface AgenteFactory {

    Agente criar(AgenteRequestDto dto);

    void atualizar(Agente agente, AgenteRequestDto dto);
}
