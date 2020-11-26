package com.ffb.tradevalidator.api.validator;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Validator
{
    private final List<BeanValidator<?>> beanValidators;

    public Validator(BeanValidator<?>... beanValidators)
    {
        this.beanValidators = asList(beanValidators);
    }

    public void validate(Object input)
    {
        String allErrors = beanValidators.stream()
                .filter(v -> v.canValidate(input))
                .map(v -> v.isValid(input))
                .filter(errorReason -> !errorReason.getErrors().isEmpty())
                .map(errorReason -> errorReason.getGiven() + "\n" + errorReason.getErrors().stream()
                        .map(e -> "- " + e)
                        .collect(Collectors.joining("\n")))
                .collect(Collectors.joining("\n"));

        if (!allErrors.isEmpty()) {
            throw new IllegalArgumentException("there are validation errors. See \n" + allErrors);
        }
    }
}
