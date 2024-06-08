package br.com.fiap.api_parquimetro.exception;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String msg) {
        super(msg);
    }
}
