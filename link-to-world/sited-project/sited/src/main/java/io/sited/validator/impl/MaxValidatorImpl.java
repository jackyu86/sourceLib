package io.sited.validator.impl;

import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class MaxValidatorImpl implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        Number number = (Number) instance;
        long max = context.option("max");
        if (number.doubleValue() > max) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", context.option("max.message", null), max));
            return false;
        }
        return true;
    }
}
