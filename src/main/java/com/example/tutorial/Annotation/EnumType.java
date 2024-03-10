package com.example.tutorial.Annotation;

import com.example.tutorial.validatior.EnumTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumTypeValidator.class)
public @interface EnumType  {
    public Class<? extends Enum<?>> value();
    String message() default "doest not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
