package br.com.fiap.api_parquimetro.controller;

import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Iniciar transação de tempo flexível", description = "Esta rota é responsável por iniciar uma transação de tempo flexível.")
    @PostMapping("/tempo-flexivel")
    public ResponseEntity<TransacaoIniciadaResponseDto> iniciarTransacaoTempoFlexivel(@Valid @RequestBody TransacaoRequestFlexivelDto dto) {
        var transacaoIniciadaResponseDto = this.service.iniciarTransacaoTempoFlexivel(dto);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @Operation(summary = "Iniciar transação de tempo fixo", description = "Esta rota é responsável por iniciar uma transação de tempo fixo.")
    @PostMapping("/tempo-fixo")
    public ResponseEntity<TransacaoIniciadaResponseDto> iniciarTransacaoTempoFixo(@Valid @RequestBody TransacaoRequestFixoDto dto) {
        var transacaoIniciadaResponseDto = this.service.iniciarTransacaoTempoFixo(dto);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @Operation(summary = "Finalizar transação", description = "Esta rota é responsável por finalizar uma transação.")
    @PatchMapping("/{id}/saida")
    public ResponseEntity<TransacaoFinalizadaResponseDto> finalizarTransacao(@PathVariable Long id) {
        var transacaoIniciadaResponseDto = this.service.finalizarTransacao(id);

        return ResponseEntity.ok(transacaoIniciadaResponseDto);
    }

    @Operation(summary = "Listar transações pendentes de pagamento", description = "Esta rota é responsavel por listar, de forma paginada, as transações pendentes de pagamento.")
    @GetMapping("/pendentes-de-pagamento")
    public ResponseEntity<Page<TransacaoPagamentoPendenteResponseDto>> listarTransacoesPendentes(Pageable pageable) {
        var transacaoPagamentoPendenteResponseDtos = this.service.listarTransacoesPendentes(pageable);

        return ResponseEntity.ok(transacaoPagamentoPendenteResponseDtos);
    }

    @Operation(summary = "Listar transações concluídas", description = "Esta rota é responsável por listar, de forma paginada, as transações concluídas.")
    @GetMapping("/pagas")
    public ResponseEntity<Page<TransacaoFinalizadaResponseDto>> listarTransacoesConcluidas(Pageable pageable) {
        var transacaoFinalizadaResponseDtos = this.service.listarTransacoesConcluidas(pageable);

        return ResponseEntity.ok(transacaoFinalizadaResponseDtos);
    }

    @Operation(summary = "Buscar transação por id", description = "Esta rota é responsável por buscar uma transação específica a partir do id informado.")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var responseDto = this.service.buscarPorId(id);

        return ResponseEntity.ok(responseDto);
    }
}
