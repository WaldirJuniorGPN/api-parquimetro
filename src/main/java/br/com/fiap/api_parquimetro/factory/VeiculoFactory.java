package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;

public interface VeiculoFactory {

    Veiculo criar(VeiculoRequestDto dto);

    void atualizar(Veiculo veiculo, VeiculoRequestDto dto);
}
