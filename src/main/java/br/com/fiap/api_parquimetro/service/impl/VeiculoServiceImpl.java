package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository repository;
    private final EntityFactory<Veiculo, VeiculoRequestDto> factory;

    @Override
    @Transactional
    public ResponseEntity<VeiculoResponseDto> cadastrar(VeiculoRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var veiculo = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/veiculo/{id}").buildAndExpand(veiculo.getId()).toUri();
        this.salvarNoBanco(veiculo);
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
    @Transactional
    public ResponseEntity<VeiculoResponseDto> atualizar(Long idVeiculo, VeiculoRequestDto dto) {
        var veiculo = this.buscarNoBanco(idVeiculo);
        this.factory.atualizar(veiculo, dto);
        this.salvarNoBanco(veiculo);
        return ResponseEntity.ok(new VeiculoResponseDto(veiculo));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var veiculo = this.buscarNoBanco(id);
        veiculo.setAtivo(false);
        ResponseEntity.noContent().build();
    }

//    @Override
//    public void registrarEntrada(Veiculo veiculo) {
//        veiculo.setHoraDaEntrada(LocalDateTime.now());
//        this.salvarNoBanco(veiculo);
//    }
//
//    @Override
//    public void registrarSaida(Veiculo veiculo, LocalDateTime dataHoraSaida) {
//        veiculo.setHoraDaSaida(dataHoraSaida);
//        this.salvarNoBanco(veiculo);
//    }

    @Override
    @Cacheable(value = "veiculos", key = "#id")
    public Veiculo buscarVeiculo(Long id) {
        return this.buscarNoBanco(id);
    }

    private void salvarNoBanco(Veiculo veiculo){
        this.repository.save(veiculo);
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
