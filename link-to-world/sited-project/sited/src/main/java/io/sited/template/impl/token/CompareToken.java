package io.sited.template.impl.token;

import io.sited.StandardException;
import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class CompareToken extends Token {
    public CompareToken(String content, Token parent) {
        super("Compare", content, parent);
    }

    public static boolean isCompareToken(Token token) {
        return "Compare".equals(token.type);
    }

    public String operator() {
        return content;
    }

    public Token leftOperand() {
        if (children.size() != 2) {
            throw new StandardException("invalid compare token, token={}", this);
        }
        return children.get(0);
    }

    public Token rightOperand() {
        return children.get(1);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (children.size() != 2) {
            throw new StandardException("invalid compare token");
        }
        b.append(children.get(0).toString());
        b.append(operator());
        b.append(children.get(1).toString());
        return b.toString();
    }
}
