package io.sited.template.impl;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chi
 */
public class Token {
    public final String type;
    public final String content;
    public final Token parent;
    public final List<Token> children = Lists.newArrayList();

    public Token(String type, String content, Token parent) {
        this.type = type;
        this.content = content;
        this.parent = parent;
    }

    public void addChild(Token token) {
        children.add(token);
    }

    public void replaceChild(Token target, Token token) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).equals(target)) {
                children.set(i, token);
                return;
            }
        }
    }

    public boolean hasNext() {
        return !children.isEmpty();
    }

    public Token next() {
        return children.get(0);
    }

    public Token last() {
        Token current = this;
        while (!current.children.isEmpty()) {
            current = current.next();
        }
        return current;
    }

    @Override
    public String toString() {
        return content;
    }
}
