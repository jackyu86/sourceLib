package io.sited.validator.impl;

import io.sited.validator.Validator;

import java.util.List;

/**
 * @author chi
 */
public class ListValidator implements Validator {
    private final String path;
    private final Validator validator;

    public ListValidator(String path, Validator validator) {
        this.path = path;
        this.validator = validator;
    }

    @Override
    public boolean validate(Object instance, Context context) {
        if (instance == null) {
            return true;
        }
        String parent = context.path;

        List list = (List) instance;
        boolean valid = true;
        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            context.path = path(parent, i);
            boolean result = validator.validate(item, context);
            if (!result) {
                valid = false;
            }
        }
        context.path = parent;
        return valid;
    }

    private String path(String parent, int index) {
        if (parent == null) {
            return path + '[' + index + ']';
        } else {
            return parent + '.' + path + '[' + index + ']';
        }
    }
}
