package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Agente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgenteRepository extends JpaRepository<Agente, Long> {

    Optional<Agente> findByIdAndAtivoTrue(Long id);

    Optional<Page<Agente>> findAllByAtivoTrue(Pageable pageable);
}
