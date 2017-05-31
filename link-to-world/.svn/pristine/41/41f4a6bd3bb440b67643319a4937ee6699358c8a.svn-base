package io.sited.user.web.validator;

import com.google.common.base.Strings;
import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

/**
 * @author chi
 */
public class UsernameValidator implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        String username = (String) instance;
        boolean valid = !Strings.isNullOrEmpty(username) && username.length() >= 4;
        if (!valid) {
            context.invalidFields.add(BadRequestException.InvalidField.of("username", "user.error.invalidUsername", null));
        }
        return valid;
    }
}
