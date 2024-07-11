package br.com.fiap.api_parquimetro.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recibo {
    private Long transacaoId;
    private String placaDoVeiculo;
    private LocalDateTime horaDaEntrada;
    private LocalDateTime horaDaSaida;
    private Duration tempoEstacionado;
    private BigDecimal valorTotalPago;
}
