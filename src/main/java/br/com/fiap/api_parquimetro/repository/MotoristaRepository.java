package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Motorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    Optional<Motorista> findByIdAndAtivoTrue(Long id);

    Optional<Page<Motorista>> findAllByAtivoTrue(Pageable pageable);
}
