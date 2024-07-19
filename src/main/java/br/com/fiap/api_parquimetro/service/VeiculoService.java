package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VeiculoService {

    VeiculoResponseDto cadastrar(VeiculoRequestDto dto);

    Page<VeiculoResponseDto> buscarTodos(Pageable pageable);

    VeiculoResponseDto buscarPorId(Long id);

    VeiculoResponseDto atualizar(Long  idVeiculo, VeiculoRequestDto dto);

    void deletar(Long id);

    Veiculo buscarVeiculo(Long id);
}
