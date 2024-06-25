package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.CalculadoraResponseDto;
import br.com.fiap.api_parquimetro.service.CalculadoraService;
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
@RequestMapping("/calculadora")
@RequiredArgsConstructor
public class CalculadoraController {

    private final CalculadoraService service;

    @PostMapping
    public ResponseEntity<CalculadoraResponseDto> cadastrar(@Valid @RequestBody CalculadoraRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var calculadoraResponseDtoResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/calculadora{id}").buildAndExpand(calculadoraResponseDtoResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(calculadoraResponseDtoResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<CalculadoraResponseDto>> buscarTodos(Pageable pageable) {
        var calculadoraResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(calculadoraResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalculadoraResponseDto> buscarPorId(@PathVariable Long id) {
        var calculadoraResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(calculadoraResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalculadoraResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody CalculadoraRequestDto dto) {
        var calculadoraResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(calculadoraResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
