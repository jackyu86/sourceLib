package io.sited.template.impl.token;

import io.sited.StandardException;
import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class TokenWrapper extends Token {
    public TokenWrapper() {
        super("WRAPPER", null, null);
    }

    public static boolean isTokenWrapper(Token token) {
        return "WRAPPER".equals(token.type);
    }

    public Token token() {
        if (children.isEmpty()) {
            throw new StandardException("missing token");
        }
        return children.get(0);
    }

    @Override
    public String toString() {
        return children.get(0).toString();
    }
}
