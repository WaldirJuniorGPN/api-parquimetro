package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.dto.response.ReciboResponseDto;
import br.com.fiap.api_parquimetro.model.Transacao;

public interface ReciboService {

    ReciboResponseDto gerarRecibo(Transacao transacao);


}
