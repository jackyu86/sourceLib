package io.sited.template.impl.token;

import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class NotToken extends Token {
    public NotToken(Token parent) {
        super("Not", null, parent);
    }

    public static boolean isNotToken(Token token) {
        return "Not".equals(token.type);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('!');
        b.append(children.get(0).toString());
        return b.toString();
    }
}
