package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transacao extends EntidadeBase{

    private Long codigo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_id")
    private Agente agente;

    private BigDecimal valortotal;

    public BigDecimal calculoDoValorTotal(LocalDateTime dataHoraInicio, LocalDateTime dataHotaFim, Tarifa tarifa) {
        Duration duracao = Duration.between(dataHoraInicio, dataHotaFim);
        var duracaoEmMinutos = duracao.toMinutes();
        var tarifaHora = getTarifa().getTarifaHora();
        var tarifaMinuto = getTarifa().getTarifaAdicional();

        var valorTotal = tarifaHora.add(tarifaMinuto.multiply(BigDecimal.valueOf(duracaoEmMinutos-60)));

        return valorTotal;

    }
}
