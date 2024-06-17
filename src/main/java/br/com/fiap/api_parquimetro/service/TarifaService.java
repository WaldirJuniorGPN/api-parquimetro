package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface TarifaService {

    ResponseEntity<TarifaResponseDto> cadastrar(TarifaRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<TarifaResponseDto> buscarPorId(Long id);

    ResponseEntity<Page<TarifaResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<TarifaResponseDto> atualizar(Long id, TarifaRequestDto dto);

    ResponseEntity<Void> deletar(Long id);

}
