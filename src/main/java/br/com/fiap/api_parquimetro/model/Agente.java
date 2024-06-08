package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "agentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Agente extends EntidadeBase {

    private String matricula;
    private String nome;
    private String areaDeAtuacao;
}
