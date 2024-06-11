package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.exception.ControllerNotFoundException;
import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("veiculoFactory")
@RequiredArgsConstructor
public class VeiculoFactoryImpl implements EntityFactory<Veiculo, VeiculoRequestDto> {

    private final MotoristaRepository repository;

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

    private Motorista buscarMotoristaNoBanco(Long id) {
        return this.repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrado"));
    }
}
