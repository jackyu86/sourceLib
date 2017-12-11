package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class CompareTokenCodeBuilder extends TokenCodeBuilder {
    private static final Map<String, String> METHODS = Maps.newHashMap();

    static {
        METHODS.put(">", "gt");
        METHODS.put(">=", "gte");
        METHODS.put("<", "lt");
        METHODS.put("<=", "lte");
        METHODS.put("==", "equals");
        METHODS.put("!=", "notEquals");
    }

    public CompareTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        CodeBuilder builder = new CodeBuilder();

        String value1 = variable("compare", "value");
        builder.append("Object %s = null;", value1);
        Token operand1 = token.children.get(0);
        builder.append(builder(operand1.type).code(operand1, context.returnName(value1)));

        String value2 = variable("compare", "value");
        builder.append("Object %s = null;", value2);
        Token operand2 = token.children.get(1);
        builder.append(builder(operand2.type).code(operand2, context.returnName(value2)));

        builder.append("%s=%s.%s(%s, %s);", context.returnName, Objects.class.getCanonicalName(), method(token), value1, value2);
        return builder.build();
    }

    private String method(Token token) {
        if (!METHODS.containsKey(token.content)) {
            throw new StandardException("unsupport operator, operator={}", token.content);
        }
        return METHODS.get(token.content);
    }
}
