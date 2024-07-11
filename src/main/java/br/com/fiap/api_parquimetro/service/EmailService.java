package br.com.fiap.api_parquimetro.service;

public interface EmailService {
    void enviarEmail(String para, String assunto, String mensagem);
}
