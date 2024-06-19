package br.com.fiap.api_parquimetro.model.dto.response;

import br.com.fiap.api_parquimetro.model.Transacao;

import java.time.LocalDateTime;

public record TransacaoPagamentoPendenteResponseDto(Long id, Long veiculoId, Long parquimetroId,
                                                    LocalDateTime horaDaEntrada, Long motoristaId,
                                                    String telefoneMotorista) {
    public TransacaoPagamentoPendenteResponseDto(Transacao transacao) {
        this(transacao.getId(), transacao.getVeiculo().getId(), transacao.getParquimetro().getId(), transacao.getInputDate(), transacao.getVeiculo().getMotorista().getId(), transacao.getVeiculo().getMotorista().getTelefone());
    }
}
