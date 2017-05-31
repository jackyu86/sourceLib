package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class DoubleTokenCodeBuilder extends TokenCodeBuilder {
    public DoubleTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        if (token.hasNext()) {
            String variable = variable("double", "value");
            CodeBuilder builder = new CodeBuilder();
            builder.append("Object %s=Double.valueOf(%s);", variable, token.content);
            builder.append(builder(token.next().type).code(token.next(), context.base(variable, String.class)));
            return builder.build();
        } else {
            return context.returnName + "=Double.valueOf(" + token.content + ");";
        }
    }
}
