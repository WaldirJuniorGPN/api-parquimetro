package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.TarifaFactory;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import org.springframework.stereotype.Component;

@Component
public class TarifaFactoryImpl implements TarifaFactory {
    @Override
    public Tarifa criar(TarifaRequestDto dto) {
       var tarifa = new Tarifa();
       tarifa.setTarifaHora(dto.tarifaHora());
       tarifa.setTarifaAdicional(dto.tarifaAdicional());
       tarifa.setStatus(dto.status());
       return tarifa;

    }

    @Override
    public void atualizar(Tarifa tarifa, TarifaRequestDto dto) {
     if (!tarifa.getTarifaHora().equals(dto.tarifaHora())) {
         tarifa.setTarifaHora(dto.tarifaHora());
     }
     if (!tarifa.getTarifaAdicional().equals(dto.tarifaAdicional())){
         tarifa.setTarifaAdicional(dto.tarifaAdicional());
     }
     if (!tarifa.getStatus().equals(dto.status())) {
         tarifa.setStatus(dto.status());
     }
    }
}
