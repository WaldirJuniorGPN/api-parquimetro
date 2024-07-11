package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParquimetroService {

    ParquimetroResponseDto cadastrar(ParquimetroRequestDto dto);

    Page<ParquimetroResponseDto> buscarTodos(Pageable pageable);

    ParquimetroResponseDto buscarPorId(Long id);

    ParquimetroResponseDto atualzar(Long id, ParquimetroRequestDto dto);

    ParquimetroResponseDto alterarStatus(Long id, StatusRequestDto status);

    void deletar(Long id);

    void ocuparParquimetro(Parquimetro parquimetro);

    void liberarParquimetro(Parquimetro parquimetro);

    Parquimetro buscarParquimetro(Long id);
}
