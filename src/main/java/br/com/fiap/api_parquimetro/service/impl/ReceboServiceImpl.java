package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Recibo;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.service.ReciboService;
import org.springframework.stereotype.Service;

@Service
public class ReceboServiceImpl implements ReciboService {
    @Override
    public Recibo gerarRecibo(Transacao transacao) {
        Recibo recibo = new Recibo();
        recibo.setTransacaoId(transacao.getId());
        recibo.setPlacaDoVeiculo(transacao.getVeiculo().getPlacaDoVeiculo());
        recibo.setHoraDaEntrada(transacao.getInputDate());
        recibo.setHoraDaSaida(transacao.getHoraDaSaida());
        recibo.setTempoEstacionado(transacao.getTempoEstacionado());
        return recibo;
    }
}
