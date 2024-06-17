package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.impl.TarifaFactoryImpl;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import br.com.fiap.api_parquimetro.repository.TarifaRepository;
import br.com.fiap.api_parquimetro.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class TarifaServiceimpl implements TarifaService {

    private final TarifaRepository repository;
    private final TarifaFactoryImpl factory;

    @Override
    public ResponseEntity<TarifaResponseDto> cadastrar(TarifaRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var tarifa = factory.criar(dto);
        var uri = uriComponentsBuilder.path("/tarifa/{id}").buildAndExpand(tarifa.getId()).toUri();
        this.repository.save(tarifa);
        return ResponseEntity.created(uri).body(new TarifaResponseDto(tarifa));
    }

    @Override
    public ResponseEntity<TarifaResponseDto> buscarPorId(Long id) {
        var tarifa = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::thowParquimetroNotFoundException);

        return ResponseEntity.ok(new TarifaResponseDto(tarifa));
    }

    @Override
    public ResponseEntity<Page<TarifaResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository
                .findAllByAtivoTrue(pageable)
                .orElseThrow(this::thowParquimetroNotFoundException)
                .map(TarifaResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<TarifaResponseDto> atualizar(Long id, TarifaRequestDto dto) {
        var tarifa = this.repository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(this::thowParquimetroNotFoundException);
        this.factory.atualizar(tarifa, dto);
        this.repository.save(tarifa);
        return ResponseEntity.ok(new TarifaResponseDto(tarifa));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var tarifa = this.repository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(this::thowParquimetroNotFoundException);
        this.repository.delete(tarifa);
        return ResponseEntity.noContent().build();
    }

    private RuntimeException thowParquimetroNotFoundException() {
        return new ControllerNotFoundException("tarifa não encontrado");
    }
}
