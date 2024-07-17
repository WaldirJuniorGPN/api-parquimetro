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

@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
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
    public ResponseEntity<VeiculoResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequestDto dto) {
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
