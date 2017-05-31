package io.sited.validator;

import com.google.common.collect.Lists;
import io.sited.http.exception.BadRequestException;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public interface Validator {
    boolean validate(Object instance, Context context);

    class Context {
        public Object root;
        public Object current;
        public String path;
        public Map<String, Object> options;
        public List<BadRequestException.InvalidField> invalidFields = Lists.newArrayList();

        @SuppressWarnings("unchecked")
        public <T> T option(String key, T defaultValue) {
            if (options == null) {
                return defaultValue;
            }
            T value = (T) options.get(key);
            return value == null ? defaultValue : value;
        }

        public <T> T option(String key) {
            return option(key, null);
        }
    }
}
