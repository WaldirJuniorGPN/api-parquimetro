package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Calculadora;

import java.math.BigDecimal;

public record CalculadoraResponseDto(Long id, BigDecimal tarifaPorHora, BigDecimal tarifaAdicional) {
    public CalculadoraResponseDto(Calculadora calculadora) {
        this(calculadora.getId(), calculadora.getTarifaPorHora(), calculadora.getTarifaAdicional());
    }
}
