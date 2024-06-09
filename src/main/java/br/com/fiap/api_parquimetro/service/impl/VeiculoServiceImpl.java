package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.VeiculoFactory;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository repository;
    private final VeiculoFactory factory;

    @Override
    public ResponseEntity<VeiculoResponseDto> cadastrar(VeiculoRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var veiculo = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/veiculo/{id}").buildAndExpand(veiculo.getId()).toUri();
        this.repository.save(veiculo);
        return ResponseEntity.created(uri).body(new VeiculoResponseDto(veiculo));
    }

    @Override
    public ResponseEntity<Page<VeiculoResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(VeiculoResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<VeiculoResponseDto> buscarPorId(Long id) {
        var veiculo = this.buscarNoBanco(id);
        return ResponseEntity.ok(new VeiculoResponseDto(veiculo));
    }

    @Override
    public ResponseEntity<VeiculoResponseDto> atualizar(Long idVeiculo, VeiculoRequestDto dto) {
        var veiculo = this.buscarNoBanco(idVeiculo);
        this.factory.atualizar(veiculo, dto);
        this.repository.save(veiculo);
        return ResponseEntity.ok(new VeiculoResponseDto(veiculo));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var veiculo = this.buscarNoBanco(id);
        veiculo.setAtivo(false);
        return ResponseEntity.noContent().build();
    }

    private Veiculo buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwVeiculoNotFoundException);
    }

    private ControllerNotFoundException throwVeiculoNotFoundException() {
        return new ControllerNotFoundException("Veiculo não encontrado");
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException("Parâmetros do JSON estão inadequados");
    }
}
