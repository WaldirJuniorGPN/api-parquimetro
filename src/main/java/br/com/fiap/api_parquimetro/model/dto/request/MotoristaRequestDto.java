package br.com.fiap.api_parquimetro.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MotoristaRequestDto(
        @NotBlank
        @Pattern(regexp = "^\\d{11}$", message = "O campo CNH deve conter exatamente 11 dígitos numéricos.")
        String cnh,
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "^\\(?([1-9]{2})\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "O formato do telefone está incorreto. Deve seguir o padrão (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.")
        String telefone
) {
}
