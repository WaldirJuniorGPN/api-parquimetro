package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import br.com.fiap.api_parquimetro.service.TarifaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/tarifa")
@RestController
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<TarifaResponseDto> cadastrar(@RequestBody  TarifaRequestDto dto, UriComponentsBuilder uriComponentsBuilder)
    {
        return  this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDto> buscarPorid(@PathVariable Long id){
        return this.service.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<Page<TarifaResponseDto>> buscarTodos(Pageable pageable){
        return this.service.buscarTodos(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponseDto> atualizar(@PathVariable Long id, @RequestBody TarifaRequestDto dto){
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
       return this.service.deletar(id);
    }

}
