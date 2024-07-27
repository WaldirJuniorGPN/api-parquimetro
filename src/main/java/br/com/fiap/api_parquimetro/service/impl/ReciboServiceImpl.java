package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.dto.response.ReciboResponseDto;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.service.ReciboService;
import org.springframework.stereotype.Service;

import static br.com.fiap.api_parquimetro.utils.Utils.formatarLocalDateTime;

@Service
public class ReciboServiceImpl implements ReciboService {

    @Override
    public ReciboResponseDto gerarRecibo(Transacao transacao) {

        ReciboResponseDto reciboResponseDto = new ReciboResponseDto();
        reciboResponseDto.setTransacaoId(transacao.getId());
        reciboResponseDto.setPlacaDoVeiculo(transacao.getVeiculo().getPlacaDoVeiculo());
        reciboResponseDto.setHoraDaEntrada(formatarLocalDateTime(transacao.getInputDate()));
        reciboResponseDto.setHoraDaSaida(transacao.getHoraDaSaida() != null ? formatarLocalDateTime(transacao.getHoraDaSaida()) : null);
        reciboResponseDto.setTempoEstacionado(transacao.getTempoEstacionado());
        reciboResponseDto.setValorTotalPago(transacao.getValorPago());

        return reciboResponseDto;
    }
}
