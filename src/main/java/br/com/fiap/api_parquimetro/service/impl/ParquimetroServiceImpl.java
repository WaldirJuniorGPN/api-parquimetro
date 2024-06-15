package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class ParquimetroServiceImpl implements ParquimetroService {

    private final ParquimetroRepository repository;
    private final EntityFactory<Parquimetro, ParquimetroRequestDto> factory;

    @Override
    public ResponseEntity<ParquimetroResponseDto> cadastrar(ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var parquimetro = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/parquimetro/{id}").buildAndExpand(parquimetro.getId()).toUri();
        this.repository.save(parquimetro);
        return ResponseEntity.created(uri).body(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new ControllerPropertyReferenceException("Parâmetros do JSON estão inadequados")).map(ParquimetroResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(Long id) {
        var parquimetro = this.buscarNoBanco(id);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> atualzar(Long id, ParquimetroRequestDto dto) {
        var parquimetro = this.buscarNoBanco(id);
        this.factory.atualizar(parquimetro, dto);
        this.repository.save(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> alterarStatus(Long id, StatusRequestDto status) {
        var parquimetro = this.buscarNoBanco(id);
        parquimetro.setStatus(status.status());
        this.repository.save(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var parquimetro = this.buscarNoBanco(id);
        parquimetro.setAtivo(false);
        return ResponseEntity.noContent().build();
    }

    private Parquimetro buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Parquimetro não encontrado"));
    }
}
