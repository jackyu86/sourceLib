package io.sited.http.exception;

import io.sited.StandardException;

/**
 * @author chi
 */
public class UnauthorizedException extends StandardException {
    public UnauthorizedException(String message, Object... params) {
        super(message, params);
    }
}
