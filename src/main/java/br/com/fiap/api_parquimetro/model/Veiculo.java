package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class Veiculo extends EntidadeBase {

    private String placaDoVeiculo;
    private String modelo;
    private String cor;
    private LocalDateTime horaDaEntrada;
    private LocalDateTime horaDaSaida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;
}
