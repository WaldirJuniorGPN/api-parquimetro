package br.com.fiap.api_parquimetro.factory;

import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;

public interface TransacaoFactory {

    Transacao criar(TransacaoRequestDto dto);
    void atualizar(Transacao transacao ,TransacaoRequestDto dto);
}
