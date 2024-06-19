package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TransacaoService {

    ResponseEntity<TransacaoIniciadaResponseDto> registrarEntrada(TransacaoRequestDto dto);

    ResponseEntity<TransacaoFinalizadaResponseDto> registrarSaida(Long id);

    ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> buscarTransacoesPendentesDePagamento(Pageable pageable);

    ResponseEntity<Page<TransacaoFinalizadaResponseDto>> buscarTransacoesPagas(Pageable pageable);

    ResponseEntity<?> buscarPorId(Long id);
}
