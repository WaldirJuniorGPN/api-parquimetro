package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.VeiculoFactory;
import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.repository.MotoristaRepository;
import br.com.fiap.api_parquimetro.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VeiculoFactoryImpl implements VeiculoFactory {

    private final VeiculoRepository repository;
    private final MotoristaRepository motoristaRepository;

    @Override
    public Veiculo criar(VeiculoRequestDto dto) {
        var veiculo = new Veiculo();
        veiculo.setPlacaDoVeiculo(dto.placaDoVeiculo());
        veiculo.setModelo(dto.modelo());
        veiculo.setCor(dto.cor());
        veiculo.setHoraDaEntrada(dto.horaDaEntrada());
        veiculo.setMotorista(this.buscarMotoristaNoBanco(dto.idMotorista()));
        return veiculo;
    }

    @Override
    public void atualizar(Veiculo veiculo, VeiculoRequestDto dto) {

        if (!veiculo.getPlacaDoVeiculo().equals(dto.placaDoVeiculo())) {
            veiculo.setPlacaDoVeiculo(dto.placaDoVeiculo());
        }
        if (!veiculo.getModelo().equals(dto.modelo())) {
            veiculo.setModelo(dto.modelo());
        }
        if (!veiculo.getCor().equals(dto.cor())) {
            veiculo.setCor(dto.cor());
        }
    }

    public void atualizarHoraSaida(Long id, LocalDateTime horaSaida){
        Veiculo veiculo = this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
        veiculo.setHoraDaSaida(horaSaida);

    }
    private Motorista buscarMotoristaNoBanco(Long id) {
        return this.motoristaRepository
                   .findByIdAndAtivoTrue(id)
                   .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrado"));
    }
}
