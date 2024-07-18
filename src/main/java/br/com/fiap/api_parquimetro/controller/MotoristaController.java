package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.MotoristaResponseDto;
import br.com.fiap.api_parquimetro.service.MotoristaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/motorista")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService service;

    @PostMapping
    public ResponseEntity<MotoristaResponseDto> cadastrar(@Valid @RequestBody MotoristaRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var motoristaResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/motoristas/{id}").buildAndExpand(motoristaResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(motoristaResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<MotoristaResponseDto>> buscarTodos(Pageable pageable) {
        var motoristaResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(motoristaResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaResponseDto> buscarPorId(@PathVariable Long id) {
        var motoristaResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(motoristaResponseDto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MotoristaResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody MotoristaRequestDto dto) {
        var motoristaResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(motoristaResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
