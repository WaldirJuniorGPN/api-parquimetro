package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.MotoristaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MotoristaService {

    MotoristaResponseDto cadastrar(MotoristaRequestDto dto);

    Page<MotoristaResponseDto> buscarTodos(Pageable pageable);

    MotoristaResponseDto buscarPorId(Long id);

    MotoristaResponseDto atualizar(Long idMotorista, MotoristaRequestDto dto);

    void deletar(Long id);
}
