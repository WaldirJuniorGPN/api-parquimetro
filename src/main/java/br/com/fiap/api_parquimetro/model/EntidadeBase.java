package br.com.fiap.api_parquimetro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_date")
    private LocalDateTime inputDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.inputDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preupdate() {
        this.updateDate = LocalDateTime.now();
    }
}
