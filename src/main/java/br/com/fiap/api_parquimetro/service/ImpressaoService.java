package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Recibo;

public interface ImpressaoService {
    void imprimirRecibo(Recibo recibo);
}
