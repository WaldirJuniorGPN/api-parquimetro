package br.com.fiap.api_parquimetro.factory.impl;

import br.com.fiap.api_parquimetro.factory.EntityFactory;
import br.com.fiap.api_parquimetro.model.Motorista;
import br.com.fiap.api_parquimetro.model.Veiculo;
import br.com.fiap.api_parquimetro.model.dto.request.VeiculoRequestDto;
import br.com.fiap.api_parquimetro.service.MotoristaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
@Qualifier("veiculoFactory")
@RequiredArgsConstructor
public class VeiculoFactoryImpl implements EntityFactory<Veiculo, VeiculoRequestDto> {

    private final MotoristaService motoristaService;

    @Override
    public Veiculo criar(VeiculoRequestDto dto) {
        var veiculo = new Veiculo();
        veiculo.setPlacaDoVeiculo(dto.placaDoVeiculo());
        veiculo.setModelo(dto.modelo());
        veiculo.setCor(dto.cor());
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
        var motoristaResponseDto = motoristaService.buscarPorId(id);
        var motorista = new Motorista();
        motorista.setId(motoristaResponseDto.id());
        return motorista;
    }
}
