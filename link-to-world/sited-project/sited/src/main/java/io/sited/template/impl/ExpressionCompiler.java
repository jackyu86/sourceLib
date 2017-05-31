package io.sited.template.impl;

import java.util.Map;

/**
 * @author chi
 */
public interface ExpressionCompiler {
    Expression compile(Token token, Map<String, Class<?>> bindingTypes);
}