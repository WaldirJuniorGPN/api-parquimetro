package br.com.fiap.api_parquimetro.model;

import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parquimetros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Parquimetro extends EntidadeBase{

    private String localizacao;

    @Enumerated(EnumType.STRING)
    private StatusParquimetro statusParquimetro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
}
