package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Agente;
import br.com.fiap.api_parquimetro.model.dto.request.AgenteRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.AgenteResponseDto;
import br.com.fiap.api_parquimetro.repository.AgenteRepository;
import br.com.fiap.api_parquimetro.service.AgenteService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgenteServiceImpl implements AgenteService {

    private final AgenteRepository repository;
    private final EntityFactory<Agente, AgenteRequestDto> factory;

    @Override
    @Transactional
    public AgenteResponseDto cadastrar(AgenteRequestDto dto) {
        var agente = this.factory.criar(dto);
        this.repository.save(agente);

        return new AgenteResponseDto(agente);
    }

    @Override
    public AgenteResponseDto buscarPorId(Long id) {
        var agente = this.buscarNoBanco(id);

        return new AgenteResponseDto(agente);
    }

    @Override
    public Page<AgenteResponseDto> buscarTodos(Pageable pageable) {
        return this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(AgenteResponseDto::new);
    }

    @Override
    @Transactional
    public AgenteResponseDto atualizar(Long id, AgenteRequestDto dto) {
        var agente = this.buscarNoBanco(id);
        this.factory.atualizar(agente, dto);
        this.repository.save(agente);

        return new AgenteResponseDto(agente);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var agente = this.buscarNoBanco(id);
        agente.setAtivo(false);
        this.repository.save(agente);
    }

    private Agente buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwAgenteNotFoundException);
    }

    private ControllerNotFoundException throwAgenteNotFoundException() {
        return new ControllerNotFoundException(ConstantesUtils.AGENTE_NAO_ENCONTRADO);
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }
}
