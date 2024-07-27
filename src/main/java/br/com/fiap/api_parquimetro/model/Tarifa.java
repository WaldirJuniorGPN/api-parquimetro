package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tarifa extends EntidadeBase {

    @Column(name = "tarifa_fixa")
    private BigDecimal tarifaFixa;

    @Column(name = "tarifa_variavel_por_hora")
    private BigDecimal tarifaVariavelPorHora;

    @Column(name = "duracao_fixa")
    private int duracaoFixa;

    public Tarifa(Long id) {
        super.setId(id);
    }
}
