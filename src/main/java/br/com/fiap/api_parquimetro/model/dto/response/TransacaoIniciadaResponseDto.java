package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Transacao;

import java.time.LocalDateTime;

public record TransacaoIniciadaResponseDto(Long id, Long veiculoId, String placaDoVeiculo, Long parquimetroId, String localizacao, LocalDateTime horaDaEntrada) {
    public TransacaoIniciadaResponseDto(Transacao transacao) {
        this(transacao.getId(), transacao.getVeiculo().getId(), transacao.getVeiculo().getPlacaDoVeiculo(), transacao.getParquimetro().getId(), transacao.getParquimetro().getLocalizacao(), transacao.getInputDate());
    }
}
