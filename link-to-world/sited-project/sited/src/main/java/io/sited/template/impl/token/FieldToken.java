package io.sited.template.impl.token;

import io.sited.template.impl.Token;

/**
 * @author chi
 */
public class FieldToken extends Token {
    public FieldToken(String content, Token parent) {
        super("Field", content, parent);
    }

    public static boolean isFieldToken(Token token) {
        return "Field".equals(token.type);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (parent != null
            && !TokenWrapper.isTokenWrapper(parent)
            && !CompareToken.isCompareToken(parent)
            && !AddToken.isAddToken(parent)
            && !TernaryToken.isTernaryToken(parent)) {
            b.append('.');
        }
        b.append(content);

        if (!children.isEmpty()) {
            b.append(children.get(0).toString());
        }
        return b.toString();
    }
}
