package com.example.tutorial.validatior;

import com.example.tutorial.Annotation.EnumType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumTypeValidator implements ConstraintValidator<EnumType, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(EnumType annotation) {
        acceptedValues = Stream.of(annotation.value().getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return acceptedValues.contains(value);
    }
}
