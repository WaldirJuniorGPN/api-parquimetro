package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.ParquimetroFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import org.springframework.stereotype.Component;


@Component
public class ParquimetroFactoryImpl implements ParquimetroFactory {
    @Override
    public Parquimetro criar(ParquimetroRequestDto dto) {
        var parquimetro = new Parquimetro();
          parquimetro.setLocalizacao(dto.localizacao());
          parquimetro.setStatus(dto.status());

        return parquimetro;
    }

    @Override
    public void atualizar(Parquimetro parquimetro, ParquimetroRequestDto dto) {

            if(!parquimetro.getLocalizacao().equals(dto.localizacao())){
                parquimetro.setLocalizacao(dto.localizacao());
            }
            if (!parquimetro.getStatus().equals(dto.status())){
                parquimetro.setStatus(dto.status());
            }
    }
}
