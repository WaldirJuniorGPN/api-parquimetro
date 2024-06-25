package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.CalculadoraResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalculadoraService {

    CalculadoraResponseDto cadastrar(CalculadoraRequestDto dto);

    Page<CalculadoraResponseDto> buscarTodos(Pageable pageable);

    CalculadoraResponseDto buscarPorId(Long id);

    CalculadoraResponseDto atualizar(Long id, CalculadoraRequestDto dto);

    void deletar(Long id);
}
