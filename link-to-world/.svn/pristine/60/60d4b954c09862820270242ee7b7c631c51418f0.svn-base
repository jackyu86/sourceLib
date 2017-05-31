package io.sited.validator.impl;

import com.google.common.base.Strings;
import io.sited.StandardException;
import io.sited.validator.Validator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class ObjectValidator implements Validator {
    private final String path;
    private final Map<Field, List<Validator>> validators;

    public ObjectValidator(String path, Map<Field, List<Validator>> validators) {
        this.path = path;
        this.validators = validators;
    }

    @Override
    public boolean validate(Object instance, Context context) {
        if (instance == null) {
            return true;
        }
        boolean valid = true;
        try {
            String parent = context.path;
            context.path = path(parent);
            if (Strings.isNullOrEmpty(path)) {
                context.root = instance;
            }
            context.current = instance;
            for (Map.Entry<Field, List<Validator>> entry : validators.entrySet()) {
                Field field = entry.getKey();
                Object value = field.get(instance);
                for (Validator validator : entry.getValue()) {
                    boolean result = validator.validate(value, context);
                    if (!result) {
                        valid = false;
                    }
                }
            }
            context.path = parent;
        } catch (IllegalAccessException e) {
            throw new StandardException(e);
        }
        return valid;
    }

    private String path(String parent) {
        if (parent == null) {
            return path;
        } else if (path == null) {
            return parent;
        } else {
            return parent + '.' + path;
        }
    }
}
