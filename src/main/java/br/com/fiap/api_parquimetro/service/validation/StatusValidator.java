package br.com.fiap.api_parquimetro.service.validation;

import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<ValidStatus, StatusParquimetro> {

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(StatusParquimetro statusParquimetro, ConstraintValidatorContext context) {
        if (statusParquimetro == null) {
            return false;
        }
        return statusParquimetro == StatusParquimetro.LIVRE || statusParquimetro == StatusParquimetro.MANUTENCAO;
    }
}
