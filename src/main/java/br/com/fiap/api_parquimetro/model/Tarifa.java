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

    @Column(name = "tarifa_flexivel_por_hora")
    private BigDecimal tarifaFlexivelPorHora;
    @Column(name = "tarifa_adicional")
    private BigDecimal tarifaAdicional;
    @Column(name = "tarifa_fixa_por_hora")
    private BigDecimal tarifaFixaPorHora;

    public Tarifa(Long id) {
        super.setId(id);
    }
}
