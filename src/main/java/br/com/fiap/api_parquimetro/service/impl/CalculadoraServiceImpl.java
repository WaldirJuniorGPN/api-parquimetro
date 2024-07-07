package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Calculadora;
import br.com.fiap.api_parquimetro.model.dto.request.CalculadoraRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.CalculadoraResponseDto;
import br.com.fiap.api_parquimetro.repository.CalculadoraRepository;
import br.com.fiap.api_parquimetro.service.CalculadoraService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService {

    private final CalculadoraRepository repository;
    private final EntityFactory<Calculadora, CalculadoraRequestDto> factory;

    @Override
    @Transactional
    public CalculadoraResponseDto cadastrar(CalculadoraRequestDto dto) {
        var calculadora = this.factory.criar(dto);
        this.repository.save(calculadora);

        return new CalculadoraResponseDto(calculadora);
    }

    @Override
    public Page<CalculadoraResponseDto> buscarTodos(Pageable pageable) {
        return this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(CalculadoraResponseDto::new);
    }

    @Override
    public CalculadoraResponseDto buscarPorId(Long id) {
        var calculadora = this.buscarNoBanco(id);

        return new CalculadoraResponseDto(calculadora);
    }

    @Override
    @Transactional
    public CalculadoraResponseDto atualizar(Long id, CalculadoraRequestDto dto) {
        var calculadora = this.buscarNoBanco(id);
        this.factory.atualizar(calculadora, dto);
        this.repository.save(calculadora);

        return new CalculadoraResponseDto(calculadora);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var calculadora = this.buscarNoBanco(id);
        calculadora.setAtivo(false);
        this.repository.save(calculadora);
    }

    private Calculadora buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException(ConstantesUtils.CALCULADORA_NAO_ENCONTRADA));
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }
}
