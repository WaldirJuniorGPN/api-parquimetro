package br.com.fiap.api_parquimetro.repository;

import br.com.fiap.api_parquimetro.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Optional<Page<Transacao>> findAllByPagamentoPendenteTrueAndAtivoTrue(Pageable pageable);

    Optional<Page<Transacao>> findAllByPagamentoPendenteFalseAndAtivoTrue(Pageable pageable);

    Optional<Page<Transacao>> findAllByAtivoTrue(Pageable pageable);

    Optional<Transacao> findByIdAndAtivoTrue(Long id);
}
