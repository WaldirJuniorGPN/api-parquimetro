package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByIdAndAtivoTrue(Long id);

    Optional<Page<Veiculo>> findAllByAtivoTrue(Pageable pageable);
}
