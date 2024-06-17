package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Tarifa;


import java.math.BigDecimal;

public record TarifaResponseDto(Long id,
                                BigDecimal tarifaHora,
                                BigDecimal tarifaAdiconal,
                                Boolean status) {

    public TarifaResponseDto( Tarifa tarifa){
        this(tarifa.getId(), tarifa.getTarifaHora(), tarifa.getTarifaAdicional(), tarifa.getStatus());
    }


}
