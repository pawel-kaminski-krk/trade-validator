package com.ffb.tradevalidator.api.validator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public abstract class BeanValidator<T>
{
    public abstract boolean canValidate(Object input);

    @SuppressWarnings("unchecked")
    ErrorReason<T> isValid(Object given) {
        // Note I assume that at this point validator accepted the object and can be cast to T
        T input = (T) given;

        List<String> errors = new ArrayList<>();
        validate(input, (validated, msg, params) -> errors.add(format(msg + " see " + validated, params)));

        return new ErrorReason<>(input, List.copyOf(errors));
    }

    protected abstract void validate(T given, ErrorMessageBuilder messageBuilder);

    static class ErrorReason<T> {
        private final T given;
        private final List<String> errors;

        ErrorReason(T given, List<String> errors)
        {
            this.given = given;
            this.errors = errors;
        }

        public T getGiven()
        {
            return given;
        }

        public List<String> getErrors()
        {
            return errors;
        }
    }

    @FunctionalInterface
    public interface ErrorMessageBuilder {
        void append(Object validated, String msg, Object... params);
    }
}
