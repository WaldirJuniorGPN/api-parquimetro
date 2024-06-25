package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.Status;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.service.CalculadoraService;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ParquimetroServiceImpl implements ParquimetroService {

    private final ParquimetroRepository parquimetroRepository;
    private final CalculadoraService calculadoraService;
    private final EntityFactory<Parquimetro, ParquimetroRequestDto> factory;

    @Override
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> cadastrar(ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var parquimetro = this.factory.criar(dto);
        var calculadora = this.buscarCalculadora(dto.calculadoraId());
        parquimetro.setCalculadora(calculadora);
        var uri = uriComponentsBuilder.path("/parquimetro/{id}").buildAndExpand(parquimetro.getId()).toUri();
        this.salvarNoBanco(parquimetro);
        return ResponseEntity.created(uri).body(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        var page = buscarPorParquimetro(pageable);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> buscarPorId(Long id) {
        var parquimetro = this.buscarParquimetro(id);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> atualzar(Long id, ParquimetroRequestDto dto) {
        var parquimetro = this.buscarParquimetro(id);
        this.factory.atualizar(parquimetro, dto);
        this.salvarNoBanco(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    @Transactional
    public ResponseEntity<ParquimetroResponseDto> alterarStatus(Long id, StatusRequestDto status) {
        var parquimetro = this.buscarParquimetro(id);
        parquimetro.setStatus(status.status());
        this.salvarNoBanco(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var parquimetro = this.buscarParquimetro(id);
        parquimetro.setAtivo(false);
        ResponseEntity.noContent().build();
    }

    @Override
    public void ocuparParquimetro(Parquimetro parquimetro) {
        parquimetro.setStatus(Status.OCUPADO);
        this.salvarNoBanco(parquimetro);
    }

    @Override
    public void liberarParquimetro(Parquimetro parquimetro) {
        parquimetro.setStatus(Status.LIVRE);
        this.salvarNoBanco(parquimetro);
    }

    @Override
    @Cacheable(value = "parquimetros", key = "#id")
    public Parquimetro buscarParquimetro(Long id) {
        return this.buscarNoBanco(id);
    }

    private void salvarNoBanco(Parquimetro parquimetro) {
        this.parquimetroRepository.save(parquimetro);
    }

    private Parquimetro buscarNoBanco(Long id) {
        return this.parquimetroRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Parquimetro não encontrado"));
    }

    private Calculadora buscarCalculadora(Long id) {
        var calculadoraResponse = calculadoraService.buscarPorId(id);
        if(isNull(calculadoraResponse)) {
            throw new ControllerNotFoundException("Calculadora não encontrada");
        }

        return new Calculadora(calculadoraResponse.id());
    }

    private Page<ParquimetroResponseDto> buscarPorParquimetro(Pageable pageable) {
        return this.parquimetroRepository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new ControllerPropertyReferenceException("Parâmetros do JSON estão inadequados")).map(ParquimetroResponseDto::new);
    }

}
