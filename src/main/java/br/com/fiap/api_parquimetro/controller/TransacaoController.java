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
@RequestMapping("transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping("/tempo-flexivel")
    public ResponseEntity<TransacaoIniciadaResponseDto> iniciarTransacaoTempoFlexivel(@Valid @RequestBody TransacaoRequestFlexivelDto dto) {
        var transacaoIniciadaResponseDto = this.service.iniciarTransacaoTempoFlexivel(dto);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @PostMapping("/tempo-fixo")
    public ResponseEntity<TransacaoIniciadaResponseDto> iniciarTransacaoTempoFixo(@Valid @RequestBody TransacaoRequestFixoDto dto) {
        var transacaoIniciadaResponseDto = this.service.iniciarTransacaoTempoFixo(dto);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @PatchMapping("/{id}/saida")
    public ResponseEntity<TransacaoFinalizadaResponseDto> finalizarTransacao(@PathVariable Long id) {
        var transacaoIniciadaResponseDto = this.service.finalizarTransacao(id);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @GetMapping("/pendentes-de-pagamento")
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> listarTransacoesPendentes(Pageable pageable) {
        var transacaoPagamentoPendenteResponseDtos = this.service.listarTransacoesPendentes(pageable);

        return ResponseEntity.ok(transacaoPagamentoPendenteResponseDtos);
    }

    @GetMapping("/pagas")
    public ResponseEntity<Page<TransacaoFinalizadaResponseDto>> listarTransacoesConcluidas(Pageable pageable) {
        var transacaoFinalizadaResponseDtos = this.service.listarTransacoesConcluidas(pageable);

        return ResponseEntity.ok(transacaoFinalizadaResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var responseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(responseDto);
    }
}
