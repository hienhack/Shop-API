package com.example.tutorial.validatior;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductType {
    String message() default "Duplicated types found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
