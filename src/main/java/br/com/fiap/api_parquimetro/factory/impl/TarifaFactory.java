package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("tarifaFactory")
public class TarifaFactory implements EntityFactory<Tarifa, TarifaRequestDto> {

    @Override
    public Tarifa criar(TarifaRequestDto dto) {
        var tarifa = new Tarifa();
        tarifa.setTarifaFlexivelPorHora(dto.tarifaFlexivelPorHora());
        tarifa.setTarifaAdicional(dto.tarifaAdicional());
        tarifa.setTarifaFixaPorHora(dto.tarifaFixaPorHora());
        return tarifa;
    }

    @Override
    public void atualizar(Tarifa tarifa, TarifaRequestDto dto) {
        if (!tarifa.getTarifaFlexivelPorHora().equals(dto.tarifaFlexivelPorHora())) {
            tarifa.setTarifaFlexivelPorHora(dto.tarifaFlexivelPorHora());
        }
        if (!tarifa.getTarifaAdicional().equals(dto.tarifaAdicional())) {
            tarifa.setTarifaAdicional(dto.tarifaAdicional());
        }
        if (!tarifa.getTarifaFixaPorHora().equals(dto.tarifaFixaPorHora())) {
            tarifa.setTarifaFixaPorHora(dto.tarifaFixaPorHora());
        }
    }
}
