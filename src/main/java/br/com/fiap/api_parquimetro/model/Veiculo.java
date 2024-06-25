package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Veiculo extends EntidadeBase {

    private String placaDoVeiculo;
    private String modelo;
    private String cor;
    //TODO: remover o atributo horaDaEntrada do Veiculo e colocar na Transacao, poderia ser o inputDate
    private LocalDateTime horaDaEntrada;
    //TODO: remover o atributo horaDaEntrada do Veiculo e colocar na Transacao
    private LocalDateTime horaDaSaida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;


}
