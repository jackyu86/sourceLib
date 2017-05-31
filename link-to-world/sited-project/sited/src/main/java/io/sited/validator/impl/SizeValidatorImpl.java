package io.sited.validator.impl;

import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class SizeValidatorImpl implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        Number number = (Number) instance;
        long min = context.option("Min");
        if (number.doubleValue() < min) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", context.option("min.message", null), min));
            return false;
        }

        long max = context.option("Max");
        if (number.doubleValue() > max) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", context.option("max.message", null), max));
            return false;
        }
        return true;
    }
}
