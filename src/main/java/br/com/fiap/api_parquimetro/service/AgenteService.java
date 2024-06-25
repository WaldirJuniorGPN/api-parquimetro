package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgenteService {

    AgenteResponseDto cadastrar(AgenteRequestDto dto);

    AgenteResponseDto buscarPorId(Long id);

    Page<AgenteResponseDto> buscarTodos(Pageable pageable);

    AgenteResponseDto atualizar(Long id, AgenteRequestDto dto);

    void deletar(Long id);
}
