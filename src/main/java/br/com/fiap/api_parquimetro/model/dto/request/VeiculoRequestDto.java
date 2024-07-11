package br.com.fiap.api_parquimetro.model.dto.request;

import br.com.fiap.api_parquimetro.service.validation.NotPastValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record VeiculoRequestDto(
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$", message = "Placa do veículo inválida")
        String placaDoVeiculo,
        @NotBlank
        String modelo,
        @NotBlank
        String cor,
        @NotNull
        Long idMotorista
) {
}
