package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("motoristaFactory")
public class MotoristaFactoryImpl implements EntityFactory<Motorista, MotoristaRequestDto> {

    @Override
    public Motorista criar(MotoristaRequestDto dto) {
        var motorista = new Motorista();
        motorista.setCnh(dto.cnh());
        motorista.setNome(dto.nome());
        motorista.setTelefone(dto.telefone());
        return motorista;
    }

    @Override
    public void atualizar(Motorista motorista, MotoristaRequestDto dto) {
        if (!motorista.getCnh().equals(dto.cnh())) {
            motorista.setCnh(dto.cnh());
        }
        if (!motorista.getNome().equals(dto.nome())) {
            motorista.setNome(dto.nome());
        }
        if (!motorista.getTelefone().equals(dto.telefone())) {
            motorista.setTelefone(dto.telefone());
        }
    }
}
