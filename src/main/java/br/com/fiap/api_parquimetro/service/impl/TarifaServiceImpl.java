package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.dto.request.TarifaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TarifaResponseDto;
import br.com.fiap.api_parquimetro.repository.TarifaRepository;
import br.com.fiap.api_parquimetro.service.TarifaService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarifaServiceImpl implements TarifaService {

    private final TarifaRepository repository;
    private final EntityFactory<Tarifa, TarifaRequestDto> factory;

    @Override
    @Transactional
    public TarifaResponseDto cadastrar(TarifaRequestDto dto) {
        var tarifa = this.factory.criar(dto);
        this.repository.save(tarifa);

        return new TarifaResponseDto(tarifa);
    }

    @Override
    public Page<TarifaResponseDto> buscarTodos(Pageable pageable) {
        return this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(TarifaResponseDto::new);
    }

    @Override
    public TarifaResponseDto buscarPorId(Long id) {
        var tarifa = this.buscarNoBanco(id);

        return new TarifaResponseDto(tarifa);
    }

    @Override
    @Transactional
    public TarifaResponseDto atualizar(Long id, TarifaRequestDto dto) {
        var tarifa = this.buscarNoBanco(id);
        this.factory.atualizar(tarifa, dto);
        this.repository.save(tarifa);

        return new TarifaResponseDto(tarifa);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var tarifa = this.buscarNoBanco(id);
        tarifa.setAtivo(false);
        this.repository.save(tarifa);
    }

    private Tarifa buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException(ConstantesUtils.TARIFA_NAO_ENCONTRADA));
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }
}
