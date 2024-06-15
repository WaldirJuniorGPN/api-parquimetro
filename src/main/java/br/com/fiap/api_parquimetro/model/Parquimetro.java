package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
}
