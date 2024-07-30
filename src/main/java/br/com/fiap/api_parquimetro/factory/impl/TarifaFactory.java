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
        tarifa.setTarifaFixa(dto.tarifaFixa());
        tarifa.setTarifaVariavelPorHora(dto.tarifaVariavelPorHora());
        tarifa.setDuracaoFixa(dto.duracaoFixa());
        return tarifa;
    }

    @Override
    public void atualizar(Tarifa tarifa, TarifaRequestDto dto) {
        if (!tarifa.getTarifaFixa().equals(dto.tarifaFixa())) {
            tarifa.setTarifaFixa(dto.tarifaFixa());
        }
        if (!tarifa.getTarifaVariavelPorHora().equals(dto.tarifaVariavelPorHora())) {
            tarifa.setTarifaVariavelPorHora(dto.tarifaVariavelPorHora());
        }
        if (tarifa.getDuracaoFixa() != dto.duracaoFixa()) {
            tarifa.setDuracaoFixa(dto.duracaoFixa());
        }
    }
}
