package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Tarifa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    Optional<Tarifa> findByIdAndAtivoTrue(Long id);

    Optional<Page<Tarifa>> findAllByAtivoTrue(Pageable pageable);
}
