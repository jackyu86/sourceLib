package io.sited.validator.impl;

import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class EmailValidatorImpl implements Validator {
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

    @Override
    public boolean validate(Object instance, Context context) {
        String email = (String) instance;
        if (!EMAIL.matcher(email).matches()) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.email.error", context.option("email.message", null), email));
            return false;
        }
        return true;
    }
}
