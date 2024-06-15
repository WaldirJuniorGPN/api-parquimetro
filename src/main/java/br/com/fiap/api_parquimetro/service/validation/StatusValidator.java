package br.com.fiap.api_parquimetro.service.validation;

import br.com.fiap.api_parquimetro.model.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<ValidStatus, Status> {

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(Status status, ConstraintValidatorContext context) {
        if (status == null) {
            return false;
        }
        return status == Status.ATIVO || status == Status.INATIVO || status == Status.MANUTENCAO;
    }
}
