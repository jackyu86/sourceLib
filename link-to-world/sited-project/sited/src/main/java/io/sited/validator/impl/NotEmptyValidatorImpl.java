package io.sited.validator.impl;

import com.google.common.base.Strings;
import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

import java.util.Collection;
import java.util.Map;

/**
 * @author chi
 */
public class NotEmptyValidatorImpl implements Validator {
    @Override
    public boolean validate(Object instance, Context context) {
        if (isEmpty(instance)) {
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.empty", context.option("notempty.message", null)));
            return false;
        }
        return true;
    }

    private boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            return Strings.isNullOrEmpty((String) value);
        } else if (value instanceof Collection) {
            return ((Collection) value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map) value).isEmpty();
        } else {
            return false;
        }
    }
}
