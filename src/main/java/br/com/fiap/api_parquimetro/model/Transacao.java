package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

@Entity
@Table(name = "transacoes")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Transacao extends EntidadeBase {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parquimetro_id")
    private Parquimetro parquimetro;

    private Duration tempoEstacionado;

    private BigDecimal valorPago;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    private boolean pagamentoPendente = true;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
}
