package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
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

import java.util.Optional;

@RequestMapping("/parquimetro")
@RestController
@RequiredArgsConstructor
public class ParquimetroController {

    private final ParquimetroService service;

    @PostMapping
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> cadastrar(@Valid @RequestBody ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder){
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(@PathVariable Long id){
        return this.service.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable){
        return this.service.buscarTodos(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> atualizar(@PathVariable Long id, @RequestBody ParquimetroRequestDto dto){
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        return this.service.deletar(id);
    }
}
