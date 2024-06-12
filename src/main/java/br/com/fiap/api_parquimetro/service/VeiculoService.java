package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface VeiculoService {

    ResponseEntity<VeiculoResponseDto> cadastrar(VeiculoRequestDto dto, UriComponentsBuilder uriComponentsBuilder);

    ResponseEntity<Page<VeiculoResponseDto>> buscarTodos(Pageable pageable);

    ResponseEntity<VeiculoResponseDto> buscarPorId(Long id);

    ResponseEntity<VeiculoResponseDto> atualizar(Long  idVeiculo, VeiculoRequestDto dto);

    ResponseEntity<Void> deletar(Long id);
}
