package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface AgenteService {

    ResponseEntity<AgenteResponseDto> cadastrar(AgenteRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<AgenteResponseDto> buscarPorId(Long id);

    ResponseEntity<Page<AgenteResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<AgenteResponseDto> atualizar(Long id, AgenteRequestDto dto);

    ResponseEntity<Void> deletar(Long id);
}
