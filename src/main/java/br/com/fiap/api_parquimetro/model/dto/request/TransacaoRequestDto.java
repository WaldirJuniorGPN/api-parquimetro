package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.model.Agente;
import br.com.fiap.api_parquimetro.model.Tarifa;
import br.com.fiap.api_parquimetro.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoRequestDto(Long id,
                                  @NotBlank
                                  Long codigo,
                                  @NotBlank
                                  @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa do veículo inválida")
                                  String placaDoveiculo,
                                  Long idAgente,
                                  BigDecimal valortotal) {
}
