package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transacoes")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Transacao extends EntidadeBase {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parquimetro_id")
    private Parquimetro parquimetro;

    private BigDecimal valorPago;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    private boolean pagamentoPendente = true;
}
