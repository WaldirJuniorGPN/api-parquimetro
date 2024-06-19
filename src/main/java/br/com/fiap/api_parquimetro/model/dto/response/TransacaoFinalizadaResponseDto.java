package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoFinalizadaResponseDto(TransacaoIniciadaResponseDto inicio, LocalDateTime horaDaSaida,
                                             BigDecimal valorPago) {
    public TransacaoFinalizadaResponseDto(Transacao transacao) {
        this(new TransacaoIniciadaResponseDto(transacao), transacao.getVeiculo().getHoraDaSaida(), transacao.getValorPago());
    }
}
