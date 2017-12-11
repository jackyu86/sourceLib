package io.sited.template.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.Filter;

import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author chi
 */
public class ExpressionEngine {
    private final Cache<String, TemplateToken> tokens = CacheBuilder.newBuilder().build();
    private final Cache<String, Expression> expressions = CacheBuilder.newBuilder().build();
    private final ExpressionTokenizer expressionTokenizer = new ExpressionTokenizerImpl();
    private final ExpressionCompiler expressionCompiler = new ExpressionCompilerImpl(this);
    private final Map<String, Filter> filters = Maps.newHashMap();

    public Expression expression(String content, Map<String, Object> context) {
        TemplateToken templateToken = tokens.getIfPresent(content);
        if (templateToken == null) {
            Token token = expressionTokenizer.tokenize(content);
            templateToken = new TemplateToken(token);
            tokens.put(content, templateToken);
        }

        String key = templateToken.key(context);
        Expression expression = expressions.getIfPresent(key);
        if (expression == null) {
            Map<String, Class<?>> bindings = bindings(context);
            expression = expressionCompiler.compile(templateToken.token, bindings);
            expressions.put(key, expression);
        }
        return expression;
    }

    public ExpressionEngine setFilter(String name, Filter filter) {
        filters.put(name, filter);
        return this;
    }

    public Filter filter(String name) {
        if (!filters.containsKey(name)) {
            throw new StandardException("missing filter, name={}", name);
        }
        return filters.get(name);
    }

    private Map<String, Class<?>> bindings(Map<String, Object> context) {
        Map<String, Class<?>> bindings = Maps.newHashMapWithExpectedSize(context.size());
        context.forEach((key, value) -> {
            if (value == null) {
                bindings.put(key, Object.class);
            } else {
                bindings.put(key, publicClass(value.getClass()));
            }
        });
        return bindings;
    }

    private Class<?> publicClass(Class<?> type) {
        Class<?> current = type;
        while (current != null) {
            if (Modifier.isPublic(current.getModifiers())) {
                return current;
            }
            current = current.getSuperclass();
        }
        return Object.class;
    }
}
