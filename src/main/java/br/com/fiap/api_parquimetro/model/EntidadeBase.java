package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_date")
    private LocalDateTime inputDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Setter
    private boolean ativo = true;

    @PrePersist
    public void prePersist() {
        this.inputDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preupdate() {
        this.updateDate = LocalDateTime.now();
    }
}
