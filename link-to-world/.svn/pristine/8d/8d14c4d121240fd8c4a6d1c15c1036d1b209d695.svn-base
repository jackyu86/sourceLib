package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * @author chi
 */
public class AddTokenCodeBuilder extends TokenCodeBuilder {
    public AddTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        CodeBuilder builder = new CodeBuilder();

        String value1 = variable("add", "value");
        builder.append("Object %s = null;", value1, Function.class.getCanonicalName());
        Token operand1 = token.children.get(0);
        builder.append(builder(operand1.type).code(operand1, context.returnName(value1)));

        String value2 = variable("add", "value");
        builder.append("Object %s = null;", value2, Function.class.getCanonicalName());
        Token operand2 = token.children.get(1);
        builder.append(builder(operand2.type).code(operand2, context.returnName(value2)));

        builder.append("%s=%s.add(%s, %s);", context.returnName, Objects.class.getCanonicalName(), value1, value2);
        return builder.build();
    }
}
