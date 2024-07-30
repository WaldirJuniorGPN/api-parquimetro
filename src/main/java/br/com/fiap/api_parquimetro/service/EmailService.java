package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.response.ReciboResponseDto;

import java.math.BigDecimal;

public interface EmailService {
    void enviarEmail(String para, String assunto, String mensagem);

    String getTemplate(Transacao transacao, BigDecimal valorPago, ReciboResponseDto reciboResponseDto);
}
