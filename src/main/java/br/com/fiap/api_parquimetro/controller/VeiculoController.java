package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/veiculo")
@RestController
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<VeiculoResponseDto> cadastrar(@Valid @RequestBody VeiculoRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<VeiculoResponseDto>> buscarTodos(Pageable pageable) {
        return this.service.buscarTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDto> buscarPorId(@PathVariable Long id) {
        return this.service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VeiculoResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequestDto dto) {
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return this.service.deletar(id);
    }
}
