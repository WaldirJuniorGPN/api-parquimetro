package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping("/tempo-flexivel")
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntradaTempoFlexivel(@Valid @RequestBody TransacaoRequestFlexivelDto dto) {
        return this.service.registrarEntradaTempoFlexivel(dto);
    }

    @PostMapping("/tempo-fixo")
    public ResponseEntity<TransacaoIniciadaResponseDto> registrarEntradaTempoFixo(@Valid @RequestBody TransacaoRequestFixoDto dto){
     return this.service.registrarEntradaTempoFixo(dto);
    }

    @PatchMapping("/{id}/saida")
    public ResponseEntity<TransacaoFinalizadaResponseDto> registrarSaida(@PathVariable Long id) {
        return this.service.registrarSaida(id);
    }

    @GetMapping("/pendentes-de-pagamento")
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> buscarTransacoesPendentesDePagamento(Pageable pageable) {
        return this.service.buscarTransacoesPendentesDePagamento(pageable);
    }

    @GetMapping("/pagas")
    public ResponseEntity<Page<TransacaoFinalizadaResponseDto>> buscarTransacoesPagas(Pageable pageable) {
        return this.service.buscarTransacoesPagas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return this.service.buscarPorId(id);
    }
}
