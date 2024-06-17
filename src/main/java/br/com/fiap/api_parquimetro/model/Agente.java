package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToMany(mappedBy = "agente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacao;
}
