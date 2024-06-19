package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Parquimetro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParquimetroRepository extends JpaRepository<Parquimetro, Long> {

    Optional<Parquimetro> findByIdAndAtivoTrue(Long id);

    Optional<Page<Parquimetro>> findAllByAtivoTrue(Pageable pageable);
}
