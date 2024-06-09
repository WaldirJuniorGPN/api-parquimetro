package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.service.AgenteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/agente")
@RestController
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService service;

    @PostMapping
    @Transactional
    public ResponseEntity<AgenteResponseDto> cadastrar(@Valid @RequestBody AgenteRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<AgenteResponseDto>> buscarTodos(Pageable pageable) {
        return this.service.buscarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenteResponseDto> buscarPorId(@PathVariable Long id) {
        return this.service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AgenteResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody AgenteRequestDto dto) {
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return this.service.deletar(id);
    }
}
