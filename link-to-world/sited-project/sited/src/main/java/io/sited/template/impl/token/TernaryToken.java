package io.sited.template.impl.token;

import io.sited.StandardException;
import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class TernaryToken extends Token {
    public TernaryToken(Token parent) {
        super("Ternary", null, parent);
    }

    public static boolean isTernaryToken(Token token) {
        return "Ternary".equals(token.type);
    }

    public Token condition() {
        if (children.size() != 3) {
            throw new StandardException("invalid ternary token, token={}", this);
        }
        return children.get(0);
    }

    public Token first() {
        if (children.size() != 3) {
            throw new StandardException("invalid ternary token, token={}", this);
        }
        return children.get(1);
    }

    public Token second() {
        if (children.size() != 3) {
            throw new StandardException("invalid ternary token, token={}", this);
        }
        return children.get(2);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (children.size() > 0) {
            b.append(children.get(0).toString());
        }
        b.append('?');
        if (children.size() > 1) {
            b.append(children.get(1).toString());
        }
        b.append(':');
        if (children.size() > 2) {
            b.append(children.get(2).toString());
        }
        return b.toString();
    }
}
