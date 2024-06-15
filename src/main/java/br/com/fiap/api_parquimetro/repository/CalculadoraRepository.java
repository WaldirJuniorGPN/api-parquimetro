package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Calculadora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalculadoraRepository extends JpaRepository<Calculadora, Long> {

    Optional<Page<Calculadora>> findAllByAtivoTrue(Pageable pageable);

    Optional<Calculadora> findByIdAndAtivoTrue(Long id);
}
