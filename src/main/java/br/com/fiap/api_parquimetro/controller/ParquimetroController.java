package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/parquimetro")
@RequiredArgsConstructor
public class ParquimetroController {

    private final ParquimetroService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> cadastrar(@Valid @RequestBody ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        return this.service.buscarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(@PathVariable Long id) {
        return this.service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody ParquimetroRequestDto dto) {
        return this.service.atualzar(id, dto);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> alterarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequestDto statusRequestDto) {
        return this.service.alterarStatus(id, statusRequestDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return this.service.deletar(id);
    }
}
