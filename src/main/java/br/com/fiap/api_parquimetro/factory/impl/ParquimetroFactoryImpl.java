package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("parquimetroFactory")
public class ParquimetroFactoryImpl implements EntityFactory<Parquimetro, ParquimetroRequestDto> {

    @Override
    public Parquimetro criar(ParquimetroRequestDto dto) {
        var parquimetro = new Parquimetro();
        parquimetro.setLocalizacao(dto.localizacao());
        parquimetro.setStatus(dto.status().status());
        return parquimetro;
    }

    @Override
    public void atualizar(Parquimetro parquimetro, ParquimetroRequestDto dto) {
        if (!parquimetro.getLocalizacao().equals(dto.localizacao())) {
            parquimetro.setLocalizacao(dto.localizacao());
        }
        if (!parquimetro.getStatus().equals(dto.status().status())) {
            parquimetro.setStatus(dto.status().status());
        }
    }
}
