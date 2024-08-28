package com.employee_api.springboot_bootcamp.constraints;

import com.employee_api.springboot_bootcamp.validators.NotSameIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NotSameIdValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface NotSameId {
    String message() default "Manager id cannot be the same as employee id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
