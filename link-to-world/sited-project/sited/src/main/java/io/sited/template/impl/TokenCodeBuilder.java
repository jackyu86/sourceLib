package io.sited.template.impl;

import io.sited.StandardException;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chi
 */
public abstract class TokenCodeBuilder {
    private final AtomicInteger counter = new AtomicInteger();
    private final Map<String, TokenCodeBuilder> builders;

    protected TokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        this.builders = builders;
    }

    public abstract String code(Token token, CodeContext context);

    protected TokenCodeBuilder builder(String type) {
        if (!builders.containsKey(type)) {
            throw new StandardException("missing code build, type={}", type);
        }
        return builders.get(type);
    }

    protected String variable(String baseName, String fieldName) {
        return baseName + "_" + fieldName + counter.incrementAndGet();
    }
}
