package br.com.fiap.api_parquimetro.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class NotPastValidator implements ConstraintValidator<NotPastValid, LocalDateTime> {

    @Override
    public void initialize(NotPastValid anotation) {
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return true;
        }
        return !localDateTime.isBefore(LocalDateTime.now());
    }
}
