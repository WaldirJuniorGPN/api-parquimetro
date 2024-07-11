package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Transacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public record TransacaoFinalizadaResponseDto(TransacaoIniciadaResponseDto inicio, LocalDateTime horaDaSaida,
                                             Duration tempoEstacionado, BigDecimal valorPago) {
    public TransacaoFinalizadaResponseDto(Transacao transacao) {
        this(new TransacaoIniciadaResponseDto(transacao), transacao.getHoraDaSaida(), transacao.getTempoEstacionado(), transacao.getValorPago());
    }
}
