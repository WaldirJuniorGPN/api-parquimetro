package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import br.com.fiap.api_parquimetro.service.TarifaService;
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
@RequestMapping("/tarifa")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService service;

    @PostMapping
    public ResponseEntity<TarifaResponseDto> cadastrar(@Valid @RequestBody TarifaRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var tarifaResponseDtoResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/tarifa{id}").buildAndExpand(tarifaResponseDtoResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(tarifaResponseDtoResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<TarifaResponseDto>> buscarTodos(Pageable pageable) {
        var tarifaResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(tarifaResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDto> buscarPorId(@PathVariable Long id) {
        var tarifaResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(tarifaResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody TarifaRequestDto dto) {
        var tarifaResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(tarifaResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
