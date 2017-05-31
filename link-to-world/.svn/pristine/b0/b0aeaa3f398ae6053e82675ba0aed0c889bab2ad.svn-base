package io.sited.template.impl.code;

import com.google.common.collect.Lists;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.FilterToken;
import io.sited.util.CodeBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class FilterTokenCodeBuilder extends TokenCodeBuilder {
    public FilterTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        FilterToken filterToken = (FilterToken) token;
        CodeBuilder builder = new CodeBuilder();

        String valueName = variable("filter", "variable");
        builder.append("Object %s=null;", valueName);
        List<String> paramNames = Lists.newArrayList();

        for (Token param : filterToken.params) {
            String paramName = variable("filter", "param");
            builder.append("Object %s=null;", paramName);
            paramNames.add(paramName);
            builder.append(builder(param.type).code(param, context.returnName(paramName)));
        }

        String filterParamsName = variable("filter", "params");
        builder.append("Object[] %s = new Object[%s];", filterParamsName, paramNames.size());

        for (int i = 0; i < paramNames.size(); i++) {
            builder.append("%s[%s]=%s;", filterParamsName, i, paramNames.get(i));
        }
        builder.append("%s=engine.filter(\"%s\").filter(%s, %s);", valueName, token.content, context.baseName, filterParamsName);

        if (token.hasNext()) {
            builder.append(builder(token.next().type).code(token.next(), context.base(valueName, Object.class)));
        } else {
            builder.append("%s=%s;", context.returnName, valueName);
        }
        return builder.build();
    }
}
