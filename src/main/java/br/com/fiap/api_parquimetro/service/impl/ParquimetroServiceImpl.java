package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Parquimetro;
import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;
import br.com.fiap.api_parquimetro.model.dto.request.ParquimetroRequestDto;
import br.com.fiap.api_parquimetro.model.dto.request.StatusRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.ParquimetroResponseDto;
import br.com.fiap.api_parquimetro.repository.ParquimetroRepository;
import br.com.fiap.api_parquimetro.service.ParquimetroService;
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
public class ParquimetroServiceImpl implements ParquimetroService {

    private final ParquimetroRepository parquimetroRepository;
    private final EntityFactory<Parquimetro, ParquimetroRequestDto> factory;

    @Override
    @Transactional
    public ParquimetroResponseDto cadastrar(ParquimetroRequestDto dto) {
        var parquimetro = this.factory.criar(dto);
        this.salvarNoBanco(parquimetro);
        return new ParquimetroResponseDto(parquimetro);
    }

    @Override
    public Page<ParquimetroResponseDto> buscarTodos(Pageable pageable) {
        return buscarPorParquimetro(pageable);
    }

    @Override
    public ParquimetroResponseDto buscarPorId(Long id) {
        var parquimetro = this.buscarParquimetro(id);
        return new ParquimetroResponseDto(parquimetro);
    }

    @Override
    public ParquimetroResponseDto atualzar(Long id, ParquimetroRequestDto dto) {
        var parquimetro = this.buscarParquimetro(id);
        this.factory.atualizar(parquimetro, dto);
        this.salvarNoBanco(parquimetro);
        return new ParquimetroResponseDto(parquimetro);
    }

    @Override
    @Transactional
    public ParquimetroResponseDto alterarStatus(Long id, StatusRequestDto status) {
        var parquimetro = this.buscarParquimetro(id);
        parquimetro.setStatusParquimetro(status.statusParquimetro());
        this.salvarNoBanco(parquimetro);
        return new ParquimetroResponseDto(parquimetro);
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
        parquimetro.setStatusParquimetro(StatusParquimetro.OCUPADO);
        this.salvarNoBanco(parquimetro);
    }

    @Override
    public void liberarParquimetro(Parquimetro parquimetro) {
        parquimetro.setStatusParquimetro(StatusParquimetro.LIVRE);
        this.salvarNoBanco(parquimetro);
    }

    @Override
    public Parquimetro buscarParquimetro(Long id) {
        return this.buscarNoBanco(id);
    }

    private void salvarNoBanco(Parquimetro parquimetro) {
        this.parquimetroRepository.save(parquimetro);
    }

    private Parquimetro buscarNoBanco(Long id) {
        return this.parquimetroRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException(ConstantesUtils.PARQUIMETRO_NAO_ENCONTRADO));
    }

    private Page<ParquimetroResponseDto> buscarPorParquimetro(Pageable pageable) {
        return this.parquimetroRepository.findAllByAtivoTrue(pageable).orElseThrow(
                () -> new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS)).map(ParquimetroResponseDto::new);
    }

}
