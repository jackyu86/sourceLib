package io.sited.validator.impl;

import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class MinValidatorImpl implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        Number number = (Number) instance;
        long min = context.option("min");
        if (number.doubleValue() < min) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", context.option("min.message", null), min));
            return false;
        }
        return true;
    }
}
