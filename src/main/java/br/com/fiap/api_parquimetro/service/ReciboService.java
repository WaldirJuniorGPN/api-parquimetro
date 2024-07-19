package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Recibo;
import br.com.fiap.api_parquimetro.model.Transacao;

public interface ReciboService {
    Recibo gerarRecibo(Transacao transacao);
}
