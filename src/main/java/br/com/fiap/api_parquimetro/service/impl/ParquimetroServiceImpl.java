package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.repository.CalculadoraRepository;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class ParquimetroServiceImpl implements ParquimetroService {

    private final ParquimetroRepository parquimetroRepository;
    private final CalculadoraRepository calculadoraRepository;
    private final EntityFactory<Parquimetro, ParquimetroRequestDto> factory;

    @Override
    public ResponseEntity<ParquimetroResponseDto> cadastrar(ParquimetroRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var parquimetro = this.factory.criar(dto);
        var calculadora = this.buscarCalculadora(dto.calculadoraId());
        parquimetro.setCalculadora(calculadora);
        var uri = uriComponentsBuilder.path("/parquimetro/{id}").buildAndExpand(parquimetro.getId()).toUri();
        this.parquimetroRepository.save(parquimetro);
        return ResponseEntity.created(uri).body(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Page<ParquimetroResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.parquimetroRepository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new ControllerPropertyReferenceException("Parâmetros do JSON estão inadequados")).map(ParquimetroResponseDto::new);
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
        this.parquimetroRepository.save(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<ParquimetroResponseDto> alterarStatus(Long id, StatusRequestDto status) {
        var parquimetro = this.buscarParquimetro(id);
        parquimetro.setStatus(status.status());
        this.parquimetroRepository.save(parquimetro);
        return ResponseEntity.ok(new ParquimetroResponseDto(parquimetro));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var parquimetro = this.buscarParquimetro(id);
        parquimetro.setAtivo(false);
        return ResponseEntity.noContent().build();
    }

    private Parquimetro buscarParquimetro(Long id) {
        return this.parquimetroRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Parquimetro não encontrado"));
    }

    private Calculadora buscarCalculadora(Long id) {
        return this.calculadoraRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Calculadora não encontrada"));
    }
}
