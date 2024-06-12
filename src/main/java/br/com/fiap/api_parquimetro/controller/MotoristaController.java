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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/motorista")
@RestController
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<MotoristaResponseDto> cadastrar(@Valid @RequestBody MotoristaRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<MotoristaResponseDto>> buscarTodos(Pageable pageable) {
        return this.service.buscarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaResponseDto> buscarPorId(@PathVariable Long id) {
        return this.service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MotoristaResponseDto> atualizar(@PathVariable Long idMotorista, @Valid @RequestBody MotoristaRequestDto dto) {
        return this.service.atualizar(idMotorista, dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return this.service.deletar(id);
    }
}
