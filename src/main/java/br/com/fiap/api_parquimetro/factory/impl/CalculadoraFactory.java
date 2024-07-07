package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("calculadoraFactory")
public class CalculadoraFactory implements EntityFactory<Calculadora, CalculadoraRequestDto> {

    @Override
    public Calculadora criar(CalculadoraRequestDto dto) {
        var calculadora = new Calculadora();
        calculadora.setTarifaPorHora(dto.tarifaPorHora());
        calculadora.setTarifaAdicional(dto.tarifaAdicional());
        calculadora.setTarifaFixaPorHora(dto.tarifaFixaPorHora());
        return calculadora;
    }

    @Override
    public void atualizar(Calculadora calculadora, CalculadoraRequestDto dto) {
        if (!calculadora.getTarifaPorHora().equals(dto.tarifaPorHora())) {
            calculadora.setTarifaPorHora(dto.tarifaPorHora());
        }
        if (!calculadora.getTarifaAdicional().equals(dto.tarifaAdicional())) {
            calculadora.setTarifaAdicional(dto.tarifaAdicional());
        }
        if (!calculadora.getTarifaFixaPorHora().equals(dto.tarifaFixaPorHora())) {
            calculadora.setTarifaFixaPorHora(dto.tarifaFixaPorHora());
        }
    }
}
