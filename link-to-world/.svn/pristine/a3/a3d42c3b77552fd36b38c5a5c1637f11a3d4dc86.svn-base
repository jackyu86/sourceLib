package io.sited.template.impl;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class TemplateToken {
    public final Token token;
    public final String prefix;
    public final List<String> contextObjects;

    public TemplateToken(Token token) {
        this.token = token;
        prefix = token.toString();
        contextObjects = Lists.newArrayList();
        contextObject(token);
    }

    public String key(Map<String, Object> context) {
        StringBuilder b = new StringBuilder(prefix);
        b.append('/');
        for (int i = 0; i < contextObjects.size(); i++) {
            if (i != 0) {
                b.append(',');
            }
            Object value = context.get(contextObjects.get(i));
            if (value != null) {
                b.append(value.getClass().getCanonicalName());
            }
        }
        return b.toString();
    }

    private void contextObject(Token token) {
        if ("Field".equals(token.type)) {
            contextObjects.add(token.content);
        } else if ("Compare".equals(token.type) || "Add".equals(token.type)) {
            contextObject(token.children.get(0));
            contextObject(token.children.get(1));
        } else if ("Ternary".equals(token.type)) {
            contextObject(token.children.get(0));
            contextObject(token.children.get(1));
            contextObject(token.children.get(2));
        } else if ("Not".equals(token.type)) {
            contextObject(token.children.get(0));
        }
    }
}
