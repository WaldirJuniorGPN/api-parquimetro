package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Transacao extends EntidadeBase {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parquimetro_id")
    private Parquimetro parquimetro;

    @Column(name = "tempo_estacionado")
    private Duration tempoEstacionado;

    @Column(name = "hora_da_saida")
    private LocalDateTime horaDaSaida;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    @Column(name = "pagamento_pendente")
    private boolean pagamentoPendente = true;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
}
