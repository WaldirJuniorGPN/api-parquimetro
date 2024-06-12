package br.com.fiap.api_parquimetro.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotPastValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPastValid {

    String message() default "Hora da entrada não pode estar no passado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}