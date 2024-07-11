package br.com.fiap.api_parquimetro.model;

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
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id")
    private Tarifa tarifa;
}
