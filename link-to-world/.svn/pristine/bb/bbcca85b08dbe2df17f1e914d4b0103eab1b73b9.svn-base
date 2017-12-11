package io.sited.http.exception;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import io.sited.StandardException;

import java.util.List;

/**
 * @author chi
 */
public class BadRequestException extends StandardException {
    private final List<InvalidField> invalidFields;

    public BadRequestException(String path, String messageKey, Object... params) {
        this(ImmutableList.of(InvalidField.of(path, messageKey, null, params)));
    }

    public BadRequestException(String path, String messageKey, String message, Object... params) {
        this(ImmutableList.of(InvalidField.of(path, messageKey, message, params)));
    }

    public BadRequestException(List<InvalidField> invalidFields) {
        super(messages(invalidFields));
        this.invalidFields = invalidFields;
    }

    public static String messages(List<InvalidField> fields) {
        StringBuilder b = new StringBuilder();
        for (InvalidField field : fields) {
            b.append(field);
        }
        return b.toString();
    }

    @Override
    public String toString() {
        return messages(invalidFields);
    }

    public List<InvalidField> invalidFields() {
        return invalidFields;
    }

    public static class InvalidField {
        public String path;
        public String messageKey;
        public String message;
        public Object[] args;

        public static InvalidField of(String path, String messageKey, String message, Object... args) {
            InvalidField invalidField = new InvalidField();
            invalidField.path = path;
            invalidField.messageKey = messageKey;
            invalidField.message = message;
            invalidField.args = args;
            return invalidField;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("path", path)
                .add("messageKey", messageKey)
                .add("message", message)
                .add("args", args)
                .toString();
        }
    }
}
