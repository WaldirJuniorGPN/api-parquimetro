package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.TransacaoFactory;
import br.com.fiap.api_parquimetro.factory.VeiculoFactory;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoResponseDto;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import br.com.fiap.api_parquimetro.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository repository;
    private final TransacaoFactory factory;

    private final VeiculoRepository veiculoRepository;
    private final VeiculoFactory veiculoFactory;


    @Override
    public ResponseEntity<TransacaoResponseDto> cadastrar(TransacaoRequestDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var transacao =this.factory.criar(dto);
        var uri = uriComponentsBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
        this.repository.save(transacao);
        return ResponseEntity.created(uri).body(new TransacaoResponseDto(transacao));
    }

    @Override
    public ResponseEntity<TransacaoResponseDto> buscarPorId(Long id) {
        var transacao = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::thowTransacaoNotFoundException);
        return ResponseEntity.ok(new TransacaoResponseDto(transacao));
    }

    @Override
    public ResponseEntity<Page<TransacaoResponseDto>> buscarTodos(Pageable pageable) {
        var page = this.repository.findAllByAtivoTrue(pageable)
                .orElseThrow(this::thowTransacaoNotFoundException)
                .map(TransacaoResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<TransacaoResponseDto> atualizar(Long id, TransacaoRequestDto dto) {
        var transacao = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::thowTransacaoNotFoundException);
        this.factory.atualizar(transacao, dto);
        this.repository.save(transacao);
        return ResponseEntity.ok(new TransacaoResponseDto(transacao));
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        var transacao = this.repository.findByIdAndAtivoTrue(id).orElseThrow(this::thowTransacaoNotFoundException);
        this.repository.delete(transacao);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> FinalizarTransacao(Long id, LocalDateTime horaSaida) {
        var transacao = this.repository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(this::thowTransacaoNotFoundException);
        var horaDaEntrada = transacao.getVeiculo().getHoraDaEntrada();
        var idVeiculo = transacao.getVeiculo().getId();
        var veiculo = this.veiculoRepository
                .findByIdAndAtivoTrue(idVeiculo)
                .orElseThrow(()->new ControllerNotFoundException("Veiculo não encotrado"));
        this.veiculoFactory.atualizarHoraSaida(id, horaSaida);
        this.veiculoRepository.save(veiculo);
        var valortotal = transacao.calculoDoValorTotal(horaDaEntrada, horaSaida, transacao.getTarifa());
        transacao.setValortotal(valortotal);
        this.repository.save(transacao);
        Duration duracao = Duration.between(horaDaEntrada, horaSaida);
        var duracaoEmMinutos = duracao.toMinutes();
        var mensagem = "id da Transação        : " + id +
                       "Placa do Veiculo       : " + transacao.getVeiculo().getPlacaDoVeiculo()+
                       "Hora da Entrada        : " + transacao.getVeiculo().getHoraDaEntrada()+
                       "Hora da Saida          : " + transacao.getVeiculo().getHoraDaSaida()+
                       "Tempo total            : " + duracaoEmMinutos +
                       "Valor da primeira hora : " + transacao.getTarifa().getTarifaHora()+
                       "Valor do minuto apos primeira hora: " + transacao.getTarifa().getTarifaAdicional() +
                       "Valor da transação : "+ transacao.getValortotal();
        return ResponseEntity.ok(mensagem);
    }

    private RuntimeException thowTransacaoNotFoundException() {
        return new ControllerNotFoundException("transacao não encontrado");
    }
}
