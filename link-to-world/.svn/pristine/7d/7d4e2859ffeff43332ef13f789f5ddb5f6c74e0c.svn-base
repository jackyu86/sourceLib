package io.sited.validator.impl;

import io.sited.validator.Validator;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

public class FieldValidator implements Validator {
    public final Field field;
    public final Validator validator;
    public final Map<String, Object> options;

    public FieldValidator(Field field, Validator validator, Map<String, Object> options) {
        this.field = field;
        this.validator = validator;
        this.options = options;

        AccessController.doPrivileged((PrivilegedAction<Field>) () -> {
            field.setAccessible(true);
            return field;
        });
    }

    @Override
    public boolean validate(Object instance, Context context) {
        String parentPath = context.path;
        context.path = parentPath == null ? field.getName() : parentPath + '.' + field.getName();
        context.options = options;
        boolean valid = validator.validate(instance, context);
        context.path = parentPath;
        return valid;
    }
}