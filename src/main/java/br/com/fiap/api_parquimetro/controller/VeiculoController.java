package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import br.com.fiap.api_parquimetro.service.VeiculoService;
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

@RequestMapping("/veiculos")
@RestController
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
    public ResponseEntity<VeiculoResponseDto> cadastrar(@Valid @RequestBody VeiculoRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var veiculoResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/veiculos/{id}").buildAndExpand(veiculoResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(veiculoResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<VeiculoResponseDto>> buscarTodos(Pageable pageable) {
        var veiculoRsponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(veiculoRsponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDto> buscarPorId(@PathVariable Long id) {
        var veiculoResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(veiculoResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequestDto dto) {
        var veiculoResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(veiculoResponseDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
