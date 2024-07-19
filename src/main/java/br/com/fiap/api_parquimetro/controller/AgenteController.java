package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.service.AgenteService;
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
@RequestMapping("/agente")
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService service;

    @PostMapping
    public ResponseEntity<AgenteResponseDto> cadastrar(@Valid @RequestBody AgenteRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var agenteResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/agentes/{id}").buildAndExpand(agenteResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(agenteResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<AgenteResponseDto>> buscarTodos(Pageable pageable) {
        var agenteResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(agenteResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenteResponseDto> buscarPorId(@PathVariable Long id) {
        var agenteResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(agenteResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenteResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody AgenteRequestDto dto) {
        var agenteResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(agenteResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
