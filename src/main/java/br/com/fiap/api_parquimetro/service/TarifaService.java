package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TarifaService {

    TarifaResponseDto cadastrar(TarifaRequestDto dto);

    Page<TarifaResponseDto> buscarTodos(Pageable pageable);

    TarifaResponseDto buscarPorId(Long id);

    TarifaResponseDto atualizar(Long id, TarifaRequestDto dto);

    void deletar(Long id);
}
