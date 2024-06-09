package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.MotoristaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface MotoristaService {

    ResponseEntity<MotoristaResponseDto> cadastrar(MotoristaRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<Page<MotoristaResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<MotoristaResponseDto> buscarPorId(Long id);

    ResponseEntity<MotoristaResponseDto> atualizar(Long idMotorista, MotoristaRequestDto dto);

    ResponseEntity<Void> deletar(Long id);
}
