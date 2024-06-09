package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;

public interface MotoristaFactory {

    Motorista criar(MotoristaRequestDto dto);

    void atualizar(Motorista motorista, MotoristaRequestDto dto);
}
