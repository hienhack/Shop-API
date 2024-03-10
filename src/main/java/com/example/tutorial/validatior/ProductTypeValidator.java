package com.example.tutorial.validatior;

import com.example.tutorial.entity.Type;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductTypeValidator implements ConstraintValidator<ProductType, List<Type>> {
    @Override
    public boolean isValid(List<Type> types, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> typeNames = new HashSet<>();
        for (Type type : types) {
            if (typeNames.contains(type.getName())) {
                return false;
            } else {
                typeNames.add(type.getName());
            }
        }

        return true;
    }
}
