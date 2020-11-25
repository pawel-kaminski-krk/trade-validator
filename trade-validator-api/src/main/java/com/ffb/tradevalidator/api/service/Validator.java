package com.ffb.tradevalidator.api.service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import java.util.stream.Collectors;

public class Validator
{
    private final javax.validation.Validator validator;

    public Validator()
    {
        validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    public <T> T validate(T input)
    {
        Set<ConstraintViolation<T>> result = validator.validate(input);
        if (result.isEmpty()) {
            return input;
        } else {
            String constraintViolationMessage = result.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
            throw new IllegalArgumentException(
                    input.getClass().getSimpleName() + " is invalid. See \n" + constraintViolationMessage);
        }
    }
}
