package com.test.agro.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FieldTypeValidator implements ConstraintValidator<FieldType, String> {

    private final List<String> fieldType = Arrays.asList("wheat", "broccoli", "strawberries");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return fieldType.contains(s);
    }
}
