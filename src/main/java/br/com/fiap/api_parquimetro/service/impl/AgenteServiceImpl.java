package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.AgenteFactory;
import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.repository.AgenteRepository;
import br.com.fiap.api_parquimetro.service.AgenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl implements AgenteService {

    private final AgenteRepository repository;
    private final AgenteFactory factory;

    @Override
    public ResponseEntity<AgenteResponseDto> cadastrar(AgenteRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var agente = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/agente/{id}").buildAndExpand(agente.getId()).toUri();
        this.repository.save(agente);
        return ResponseEntity.created(uri).body(new AgenteResponseDto(agente));
    }

    @Override
    public ResponseEntity<AgenteResponseDto> buscarPorId(Long id) {
        var agente = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwAgenteNotFoundException);
        return ResponseEntity.ok(new AgenteResponseDto(agente));
    }

    @Override
    public ResponseEntity<Page<AgenteResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwAgenteNotFoundException).map(AgenteResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<AgenteResponseDto> atualizar(Long id, AgenteRequestDto dto) {
        var agente = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwAgenteNotFoundException);
        this.factory.atualizar(agente, dto);
        this.repository.save(agente);
        return ResponseEntity.ok(new AgenteResponseDto(agente));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var agente = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwAgenteNotFoundException);
        agente.setAtivo(false);
        this.repository.save(agente);
        return ResponseEntity.noContent().build();
    }

    private ControllerNotFoundException throwAgenteNotFoundException() {
        return new ControllerNotFoundException("Agente não encontrado");
    }
}
