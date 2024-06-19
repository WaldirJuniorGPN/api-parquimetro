package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Transacao;

import java.time.LocalDateTime;

public record TransacaoIniciadaResponseDto(Long id, Long veiculoId, Long parquimetroId, LocalDateTime horaDaEntrada) {
    public TransacaoIniciadaResponseDto(Transacao transacao) {
        this(transacao.getId(), transacao.getVeiculo().getId(), transacao.getParquimetro().getId(), transacao.getInputDate());
    }
}
