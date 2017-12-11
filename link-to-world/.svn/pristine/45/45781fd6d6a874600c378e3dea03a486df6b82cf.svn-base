package io.sited.form;

import io.sited.util.JSON;

import java.util.Map;

/**
 * @author chi
 */
public class FieldOption<T> {
    public String name;
    public String displayName;
    public String type;
    public Class<T> javaType;
    public T defaultValue;

    @SuppressWarnings("unchecked")
    public T value(Map<String, Object> options) {
        if (options.containsKey(name)) {
            return JSON.convert(options.get(name), javaType);
        }
        return defaultValue;
    }
}
