package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoResponseDto;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import br.com.fiap.api_parquimetro.service.impl.TransacaoServiceImpl;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RequestMapping("/transacao")
@RestController
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<TransacaoResponseDto> cadastrar(@RequestBody TransacaoRequestDto dto, UriComponentsBuilder uriComponentsBuilder){
        return this.service.cadastrar(dto, uriComponentsBuilder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDto> buscarPorId(@PathVariable Long id){
        return this.service.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoResponseDto>> buscarTodos(Pageable pageable){
       return this.service.buscarTodos(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoResponseDto> atualizar(Long id, TransacaoRequestDto dto){
        return this.service.atualizar(id, dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(Long id){
        return this.service.deletar(id);
    }

    @GetMapping("/finalizar-Transacao/{id}")
    public ResponseEntity<String> FinalizarTransacao(@PathVariable Long id, @RequestBody LocalDateTime horaSaida){
        return this.service.FinalizarTransacao(id, horaSaida);
    }

}
