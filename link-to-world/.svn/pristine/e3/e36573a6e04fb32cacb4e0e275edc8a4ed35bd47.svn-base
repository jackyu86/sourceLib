package io.sited.template.impl.token;

import io.sited.StandardException;
import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class AddToken extends Token {
    public AddToken(Token parent) {
        super("Add", "+", parent);
    }

    public static boolean isAddToken(Token token) {
        return "Add".equals(token.type);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (children.size() != 2) {
            throw new StandardException("invalid add token");
        }
        b.append(children.get(0).toString());
        b.append('+');
        b.append(children.get(1).toString());
        return b.toString();
    }
}
