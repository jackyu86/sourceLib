package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class NullTokenCodeBuilder extends TokenCodeBuilder {
    public NullTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext codeContext) {
        return codeContext.returnName + "=null;";
    }
}
