package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/parquimetros")
@RequiredArgsConstructor
public class ParquimetroController {

    private final ParquimetroService service;

    @PostMapping
    public ResponseEntity<ParquimetroResponseDto> cadastrar(@Valid @RequestBody ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var parquimetroResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/parquimetros/{id}").buildAndExpand(parquimetroResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(parquimetroResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        var parquimetroResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(parquimetroResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(@PathVariable Long id) {
        var parquimetroResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(parquimetroResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody ParquimetroRequestDto dto) {
        var parquimetroResponseDto = this.service.atualzar(id, dto);

        return ResponseEntity.ok(parquimetroResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ParquimetroResponseDto> alterarStatus(@PathVariable Long id, @Valid @RequestBody StatusRequestDto statusRequestDto) {
        var parquimetroResponseDto = this.service.alterarStatus(id, statusRequestDto);

        return ResponseEntity.ok(parquimetroResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
