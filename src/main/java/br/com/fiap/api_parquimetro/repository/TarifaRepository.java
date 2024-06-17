package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByIdAndAtivoTrue(Long id);

    Optional<Page<Tarifa>> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            SELECT t FROM Tarifa t WHERE t.status = true
""")
    Optional<Tarifa> retornaTarifaAtiva();
}
