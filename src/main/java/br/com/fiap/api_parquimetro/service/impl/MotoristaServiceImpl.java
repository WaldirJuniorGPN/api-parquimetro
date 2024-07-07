package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.exception.ControllerPropertyReferenceException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.dto.request.MotoristaRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.MotoristaResponseDto;
import br.com.fiap.api_parquimetro.repository.MotoristaRepository;
import br.com.fiap.api_parquimetro.service.MotoristaService;
import br.com.fiap.api_parquimetro.utils.ConstantesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MotoristaServiceImpl implements MotoristaService {

    private final MotoristaRepository repository;
    private final EntityFactory<Motorista, MotoristaRequestDto> factory;

    @Override
    @Transactional
    public ResponseEntity<MotoristaResponseDto> cadastrar(MotoristaRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var motorista = this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/motorista/{id}").buildAndExpand(motorista.getId()).toUri();
        this.repository.save(motorista);
        return ResponseEntity.created(uri).body(new MotoristaResponseDto(motorista));
    }

    @Override
    public ResponseEntity<Page<MotoristaResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable).orElseThrow(this::throwPropertyReferenceException).map(MotoristaResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<MotoristaResponseDto> buscarPorId(Long id) {
        var motorista = this.buscarNoBanco(id);
        return ResponseEntity.ok(new MotoristaResponseDto(motorista));
    }

    @Override
    public ResponseEntity<MotoristaResponseDto> atualizar(Long idMotorista, MotoristaRequestDto dto) {
        var motorista = this.buscarNoBanco(idMotorista);
        this.factory.atualizar(motorista, dto);
        this.repository.save(motorista);
        return ResponseEntity.ok(new MotoristaResponseDto(motorista));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        var motorista = this.buscarNoBanco(id);
        motorista.setAtivo(false);
        ResponseEntity.noContent().build();
    }

    private Motorista buscarNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::throwMotoristaNotFoundException);
    }

    private ControllerNotFoundException throwMotoristaNotFoundException() {
        return new ControllerNotFoundException(ConstantesUtils.MOTORISTA_NAO_ENCONTRADO);
    }

    private ControllerPropertyReferenceException throwPropertyReferenceException() {
        return new ControllerPropertyReferenceException(ConstantesUtils.PARAMETROS_JSON_INCORRETOS);
    }
}
