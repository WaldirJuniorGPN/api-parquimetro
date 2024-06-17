package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;

public interface TarifaFactory {

    Tarifa criar(TarifaRequestDto dto);
    void atualizar(Tarifa tarifa, TarifaRequestDto dto);

}
