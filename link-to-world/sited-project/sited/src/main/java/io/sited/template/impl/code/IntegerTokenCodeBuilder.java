package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class IntegerTokenCodeBuilder extends TokenCodeBuilder {
    public IntegerTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        if (token.hasNext()) {
            String variable = variable("integer", "value");
            CodeBuilder builder = new CodeBuilder();
            builder.append("Object %s=Integer.valueOf(%s);", variable, token.content);
            builder.append(builder(token.next().type).code(token.next(), context.base(variable, String.class)));
            return builder.build();
        } else {
            return context.returnName + "=Integer.valueOf(" + token.content + ");";
        }
    }
}
