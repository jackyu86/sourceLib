package io.sited.user.web.validator;

import com.google.common.base.Strings;
import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class PasswordValidator implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        String password = (String) instance;
        boolean valid = !Strings.isNullOrEmpty(password) && password.length() >= 4;
        if (!valid) {
            context.invalidFields.add(BadRequestException.InvalidField.of("password", "user.error.invalidPassword", null));
        }
        return valid;
    }
}
