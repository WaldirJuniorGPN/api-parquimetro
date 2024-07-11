package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.VeiculoResponseDto;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import br.com.fiap.api_parquimetro.service.VeiculoService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository repository;
    private final EntityFactory<Veiculo, VeiculoRequestDto> factory;

    @Override
    @Transactional
    public VeiculoResponseDto cadastrar(VeiculoRequestDto dto) {
        var veiculo = this.factory.criar(dto);
        this.salvarNoBanco(veiculo);
        return new VeiculoResponseDto(veiculo);
    }

    @Override
    public Page<VeiculoResponseDto> buscarTodos(Pageable pageable) {
        return this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(VeiculoResponseDto::new);
    }

    @Override
    public VeiculoResponseDto buscarPorId(Long id) {
        var veiculo = this.buscarNoBanco(id);
        return new VeiculoResponseDto(veiculo);
    }

    @Override
    @Transactional
    public VeiculoResponseDto atualizar(Long idVeiculo, VeiculoRequestDto dto) {
        var veiculo = this.buscarNoBanco(idVeiculo);
        this.factory.atualizar(veiculo, dto);
        this.salvarNoBanco(veiculo);
        return new VeiculoResponseDto(veiculo);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var veiculo = this.buscarNoBanco(id);
        veiculo.setAtivo(false);
        ResponseEntity.noContent().build();
    }

    @Override
    @Cacheable(value = "veiculos", key = "#id")
    public Veiculo buscarVeiculo(Long id) {
        return this.buscarNoBanco(id);
    }

    private void salvarNoBanco(Veiculo veiculo) {
        this.repository.save(veiculo);
    }

    private Veiculo buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwVeiculoNotFoundException);
    }

    private ControllerNotFoundException throwVeiculoNotFoundException() {
        return new ControllerNotFoundException(ConstantesUtils.VEICULO_NAO_ENCONTRADO);
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }
}
