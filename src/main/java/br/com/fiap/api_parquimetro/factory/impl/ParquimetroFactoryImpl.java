package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.service.TarifaService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Qualifier("parquimetroFactory")
@RequiredArgsConstructor
public class ParquimetroFactoryImpl implements EntityFactory<Parquimetro, ParquimetroRequestDto> {

    private final TarifaService tarifaService;

    @Override
    public Parquimetro criar(ParquimetroRequestDto dto) {
        var parquimetro = new Parquimetro();
        parquimetro.setLocalizacao(dto.localizacao());
        parquimetro.setStatusParquimetro(dto.status().statusParquimetro());
        parquimetro.setTarifa(this.buscarTarifaNoBanco(dto.tarifaId()));
        return parquimetro;
    }

    @Override
    public void atualizar(Parquimetro parquimetro, ParquimetroRequestDto dto) {
        if (!parquimetro.getLocalizacao().equals(dto.localizacao())) {
            parquimetro.setLocalizacao(dto.localizacao());
        }
        if (!parquimetro.getStatusParquimetro().equals(dto.status().statusParquimetro())) {
            parquimetro.setStatusParquimetro(dto.status().statusParquimetro());
        }
        if (!parquimetro.getTarifa().getId().equals(dto.tarifaId())) {
            parquimetro.setTarifa(this.buscarTarifaNoBanco(dto.tarifaId()));
        }
    }

    private Tarifa buscarTarifaNoBanco(Long id) {
        var tarifaResponse = tarifaService.buscarPorId(id);
        if (isNull(tarifaResponse)) {
            throw new ControllerNotFoundException(ConstantesUtils.TARIFA_NAO_ENCONTRADA);
        }
        return new Tarifa(tarifaResponse.id());
    }
}
