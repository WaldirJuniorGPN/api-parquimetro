package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "calculadoras")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Calculadora extends EntidadeBase {

    private BigDecimal tarifaPorHora;
    private BigDecimal tarifaAdicional;
}
