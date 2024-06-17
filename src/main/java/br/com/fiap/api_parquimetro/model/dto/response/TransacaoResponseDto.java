package br.com.fiap.api_parquimetro.model.dto.response;


import br.com.fiap.api_parquimetro.model.Transacao;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponseDto(Long id,
                                   Long codigo,
                                   String placaVeiculo,
                                   LocalDateTime horaDaEntrada,
                                   Long idTarifa,
                                   Long idAgente,
                                   BigDecimal valortotal) {

    public TransacaoResponseDto(Transacao transacao) {
        this(transacao.getId(),
             transacao.getCodigo(),
             transacao.getVeiculo().getPlacaDoVeiculo(),
             transacao.getVeiculo().getHoraDaEntrada(),
             transacao.getTarifa().getId(),
             transacao.getAgente().getId(),
             transacao.getValortotal());
    }
}
