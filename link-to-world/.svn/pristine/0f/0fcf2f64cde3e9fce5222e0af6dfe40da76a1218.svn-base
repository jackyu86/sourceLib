package io.sited.template.impl.token;

import io.sited.template.impl.Token;

import java.util.List;

/**
 * @author chi
 */
public class MethodToken extends Token {
    public final List<Token> params;

    public MethodToken(String content, Token parent, List<Token> params) {
        super("Method", content, parent);
        this.params = params;
    }

    public static boolean isMethodToken(Token token) {
        return "Method".equals(token.type);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(".");
        b.append(content);
        b.append('(');
        for (int i = 0; i < params.size(); i++) {
            if (i != 0) {
                b.append(',');
            }
            b.append(params.get(i).toString());
        }
        b.append(')');
        return b.toString();
    }
}
