package br.com.fiap.api_parquimetro.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReciboResponseDto {
    private Long transacaoId;
    private String placaDoVeiculo;
    private String horaDaEntrada;
    private String horaDaSaida;
    private Duration tempoEstacionado;
    private BigDecimal valorTotalPago;
}
