package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

public interface TransacaoService {

    ResponseEntity<TransacaoResponseDto> cadastrar(TransacaoRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<TransacaoResponseDto> buscarPorId(Long id);

    ResponseEntity<Page<TransacaoResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<TransacaoResponseDto> atualizar(Long id, TransacaoRequestDto dto);

    ResponseEntity<Void> deletar(Long id);

    ResponseEntity<String> FinalizarTransacao(Long id, LocalDateTime horaSaida);
}
