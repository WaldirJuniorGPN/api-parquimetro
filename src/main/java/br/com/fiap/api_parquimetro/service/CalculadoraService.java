package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.CalculadoraResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface CalculadoraService {

    ResponseEntity<CalculadoraResponseDto> cadastrar(CalculadoraRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<Page<CalculadoraResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<CalculadoraResponseDto> buscarPorId(Long id);

    ResponseEntity<CalculadoraResponseDto> atualizar(Long id, CalculadoraRequestDto dto);

    ResponseEntity<Void> deletar(Long id);
}
