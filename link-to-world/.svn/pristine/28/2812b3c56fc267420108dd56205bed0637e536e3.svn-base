package io.sited.template.impl.token;

import io.sited.template.impl.Token;

import java.util.List;

/**
 * @author chi
 */
public class FilterToken extends Token {
    public final List<Token> params;

    public FilterToken(String content, Token parent, List<Token> params) {
        super("Filter", content, parent);
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("|");
        b.append(content);
        if (!params.isEmpty()) {
            b.append('(');
            for (int i = 0; i < this.params.size(); i++) {
                if (i != 0) {
                    b.append(',');
                }
                b.append(this.params.get(i).toString());
            }
            b.append(')');
        }
        return b.toString();
    }
}
