package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tarifa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tarifa extends EntidadeBase{

    private BigDecimal tarifaHora;
    private BigDecimal tarifaAdicional;
    private Boolean status;
    @OneToMany(mappedBy = "tarifa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacao;

    public BigDecimal calcularValor(Veiculo veiculo){
       return new BigDecimal("0");
    }

    @Override
    public String toString() {
        return "Tarifa{" +
                "tarifaHora=" + tarifaHora +
                ", tarifaAdicional=" + tarifaAdicional +
                ", status=" + status +
                ", transacao=" + transacao +
                '}';
    }
}
