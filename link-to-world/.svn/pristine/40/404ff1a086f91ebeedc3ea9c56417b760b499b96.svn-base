package io.sited;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author chi
 */
public class StandardException extends RuntimeException {
    public StandardException(String message, Object... params) {
        super(MessageFormatter.arrayFormat(message, params).getMessage());

        if (params.length > 0 && params[params.length - 1] instanceof Throwable) {
            initCause((Throwable) params[params.length - 1]);
        }
    }

    public StandardException(Throwable cause) {
        this(cause.getMessage() == null ? "null pointer exception" : cause.getMessage(), cause);
    }
}
