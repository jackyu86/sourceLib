package io.sited.validator.impl;

import io.sited.validator.Validator;

import java.util.Map;

/**
 * @author chi
 */
public class MapValidator implements Validator {
    private final String path;
    private final Validator validator;

    public MapValidator(String path, Validator validator) {
        this.path = path;
        this.validator = validator;
    }

    @Override
    public boolean validate(Object instance, Context context) {
        if (instance == null) {
            return true;
        }
        String parent = context.path;
        Map map = (Map) instance;
        boolean valid = true;
        for (Object object : map.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            context.path = path(parent, entry.getKey().toString());
            boolean result = validator.validate(entry.getValue(), context);
            if (!result) {
                valid = false;
            }
        }
        context.path = parent;
        return valid;
    }

    private String path(String parent, String key) {
        if (parent == null) {
            return path + '.' + key;
        } else {
            return parent + '.' + path + '.' + key;
        }
    }
}
