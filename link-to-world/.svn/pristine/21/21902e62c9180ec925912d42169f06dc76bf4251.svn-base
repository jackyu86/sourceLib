package io.sited.validator.impl;

import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class NotNullValidatorImpl implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        if (instance == null) {
            BadRequestException.InvalidField field = BadRequestException.InvalidField.of(context.path, "validator.error.null", context.option("notnull.message", null));
            context.invalidFields.add(field);
            return false;
        }
        return true;
    }
}
