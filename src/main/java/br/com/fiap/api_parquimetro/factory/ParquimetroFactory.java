package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;



public interface ParquimetroFactory {

    Parquimetro criar(ParquimetroRequestDto dto);
    void atualizar(Parquimetro parquimetro, ParquimetroRequestDto dto);

}
