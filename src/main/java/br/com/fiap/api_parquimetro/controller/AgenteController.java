package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.service.AgenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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



    @Operation(summary = "Cadastrar agente", description = "Esta rota cadastra novos agentes.")
    @PostMapping
    public ResponseEntity<AgenteResponseDto> cadastrar(@Valid @RequestBody AgenteRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var agenteResponseDto = this.service.cadastrar(dto);
        var uri = uriComponentsBuilder.path("/agentes/{id}").buildAndExpand(agenteResponseDto.id()).toUri();

        return ResponseEntity.created(uri).body(agenteResponseDto);
    }

    @Operation(summary="Buscar agentes cadastrados", description = "Esta rota retorna, de forma paginada, todos os agentes cadastrados")
    @GetMapping
    public ResponseEntity<Page<AgenteResponseDto>> buscarTodos(Pageable pageable) {
        var agenteResponseDtos = this.service.buscarTodos(pageable);

        return ResponseEntity.ok(agenteResponseDtos);
    }

    @Operation(summary = "Buscar agente", description = "Esta rota busca um agente específico a partir do id informado")
    @GetMapping("/{id}")
    public ResponseEntity<AgenteResponseDto> buscarPorId(@PathVariable Long id) {
        var agenteResponseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(agenteResponseDto);
    }

    @Operation(summary = "Atualizar informações de agente", description = "Esta rota atualiza as informações de um agente específico a partir do id informado")
    @PutMapping("/{id}")
    public ResponseEntity<AgenteResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody AgenteRequestDto dto) {
        var agenteResponseDto = this.service.atualizar(id, dto);

        return ResponseEntity.ok(agenteResponseDto);
    }

    @Operation(summary="Deleta um agente", description = "Esta rota deleta um agente específico a partir do id informado")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
