package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ParquimetroService {

    ResponseEntity<ParquimetroResponseDto> cadastrar(ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<ParquimetroResponseDto> buscarPorId(Long id);

    ResponseEntity<ParquimetroResponseDto> atualzar(Long id, ParquimetroRequestDto dto);

    ResponseEntity<ParquimetroResponseDto> alterarStatus(Long id, StatusRequestDto status);

    ResponseEntity<Void> deletar(Long id);

    void ocuparParquimetro(Parquimetro parquimetro);

    void liberarParquimetro(Parquimetro parquimetro);

    Parquimetro buscarParquimetro(Long id);
}
