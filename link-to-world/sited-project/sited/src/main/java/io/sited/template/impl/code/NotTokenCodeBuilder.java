package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class NotTokenCodeBuilder extends TokenCodeBuilder {
    public NotTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext codeContext) {
        CodeBuilder builder = new CodeBuilder();
        String notName = variable("not", "value");
        builder.append("Object %s=null;", notName);
        builder.append(builder(token.next().type).code(token.next(), codeContext.returnName(notName)));
        builder.append("%s=%s.not(%s);", codeContext.returnName, Objects.class.getCanonicalName(), notName);
        return builder.build();
    }
}
