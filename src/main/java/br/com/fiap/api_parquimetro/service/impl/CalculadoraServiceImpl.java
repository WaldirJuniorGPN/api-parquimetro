package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.CalculadoraResponseDto;
import br.com.fiap.api_parquimetro.repository.CalculadoraRepository;
import br.com.fiap.api_parquimetro.service.CalculadoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService {

    private final CalculadoraRepository repository;
    private final EntityFactory<Calculadora, CalculadoraRequestDto> factory;

    @Override
    public ResponseEntity<CalculadoraResponseDto> cadastrar(CalculadoraRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var calculadora = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/calculadora{id}").buildAndExpand(calculadora.getId()).toUri();
        this.repository.save(calculadora);
        return ResponseEntity.created(uri).body(new CalculadoraResponseDto(calculadora));
    }

    @Override
    public ResponseEntity<Page<CalculadoraResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new ControllerPropertyReferenceException("Parâmetros do Json inadequados")).map(CalculadoraResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<CalculadoraResponseDto> buscarPorId(Long id) {
        var calculadora = this.buscarNoBanco(id);
        return ResponseEntity.ok(new CalculadoraResponseDto(calculadora));
    }

    @Override
    public ResponseEntity<CalculadoraResponseDto> atualizar(Long id, CalculadoraRequestDto dto) {   
        var calculadora = this.buscarNoBanco(id);
        this.factory.atualizar(calculadora, dto);
        this.repository.save(calculadora);
        return ResponseEntity.ok(new CalculadoraResponseDto(calculadora));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var calculadora = this.buscarNoBanco(id);
        calculadora.setAtivo(false);
        return ResponseEntity.noContent().build();
    }

    private Calculadora buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Calculadora não encontrada"));
    }
}
