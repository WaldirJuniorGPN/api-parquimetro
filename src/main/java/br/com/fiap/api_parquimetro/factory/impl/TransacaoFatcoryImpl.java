package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.TransacaoFactory;
import br.com.fiap.api_parquimetro.model.Agente;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestDto;
import br.com.fiap.api_parquimetro.repository.AgenteRepository;
import br.com.fiap.api_parquimetro.repository.TarifaRepository;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransacaoFatcoryImpl implements TransacaoFactory {

    private final TarifaRepository tarifaRepository;
    private final AgenteRepository agenteRepository;
    private final VeiculoRepository veiculoRepository;

    @Override
    public Transacao criar(TransacaoRequestDto dto) {
        Transacao transacao = new Transacao();
        transacao.setCodigo(dto.codigo());
        transacao.setTarifa(this.buscaTarifaAtiva());
        transacao.setAgente(this.buscarAgente(dto.idAgente()));
        transacao.setVeiculo(this.buscaVeiculoPorPlaca(dto.placaDoveiculo()));
        transacao.setValortotal(dto.valortotal());
        return transacao;
    }

    @Override
    public void atualizar(Transacao transacao, TransacaoRequestDto dto) {
        if(!transacao.getCodigo().equals(dto.codigo())){
            transacao.setCodigo(dto.codigo());
        }

        if (!transacao.getValortotal().equals(dto.valortotal())){
            transacao.setValortotal(dto.valortotal());
        }
    }

    private Tarifa buscaTarifaAtiva(){
        return this.tarifaRepository.retornaTarifaAtiva()
                .orElseThrow(()->new ControllerNotFoundException("Não encontrada nenhuma tarifa ativa"));
    }
    private Agente buscarAgente(Long id){
       return this.agenteRepository.findByIdAndAtivoTrue(id).orElseThrow(()->new ControllerNotFoundException("Agente não encontrado"));
    }

    public Veiculo buscaVeiculoPorPlaca(String placaDoVeiculo){
        return this.veiculoRepository.findByPlacaDoVeiculoIgnoreCaseAndHoraDaSaidaIsNull(placaDoVeiculo)
                .orElseThrow(()->new ControllerNotFoundException("Não foi encontrado nenhum veículo com essa placa"));

    }

}
