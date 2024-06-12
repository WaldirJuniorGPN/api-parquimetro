package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.ParquimetroFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;

import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ParquimetroServiceImpl implements ParquimetroService {

    private final ParquimetroRepository repository;
    private final ParquimetroFactory factory;
    @Override
    public ResponseEntity<ParquimetroResponseDto> cadastrar(ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var parquimetro = factory.criar(dto);
        var uri = uriComponentsBuilder.path("/parquimetro/{id}").buildAndExpand(parquimetro.getId()).toUri();
        this.repository.save(parquimetro);
        return ResponseEntity.created(uri).body(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(Long id) {
        var parquimetro = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::thowParquimetroNotFoundException);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository
                .findAllByAtivoTrue(pageable)
                .orElseThrow(this::thowParquimetroNotFoundException)
                .map(ParquimetroResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> atualizar(Long id, ParquimetroRequestDto dto) {
        var parquimetro = this.repository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(this::thowParquimetroNotFoundException);
        this.factory.atualizar(parquimetro, dto);
        this.repository.save(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var parquimetro = this.repository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(this::thowParquimetroNotFoundException);
        this.repository.delete(parquimetro);
        return ResponseEntity.noContent().build();
    }

    private RuntimeException thowParquimetroNotFoundException() {
        return new ControllerNotFoundException("Parquimetro não encontrado");
    }
}
