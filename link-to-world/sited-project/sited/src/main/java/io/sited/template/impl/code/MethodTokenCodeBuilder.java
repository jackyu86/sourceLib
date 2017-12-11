package io.sited.template.impl.code;

import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.MethodToken;
import io.sited.util.CodeBuilder;
import io.sited.util.Types;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class MethodTokenCodeBuilder extends TokenCodeBuilder {
    public MethodTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext codeContext) {
        CodeBuilder builder = new CodeBuilder();

        List<String> paramNames = Lists.newArrayList();
        MethodToken methodToken = (MethodToken) token;
        for (Token param : methodToken.params) {
            String paramName = variable("method", "param");
            paramNames.add(paramName);
            builder.append("Object %s = null;", paramName);
            builder.append(builder(param.type).code(param, new CodeContext(codeContext.bindings, "context", Map.class, paramName)));
        }

        builder.append("%s=%s.object(%s.%s(", codeContext.returnName, Objects.class.getCanonicalName(), codeContext.baseName, token.content);
        Method method = method(token.content, Types.raw(codeContext.baseClass), paramNames.size());
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < paramNames.size(); i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append("(%s)%s", parameterTypes[i].getCanonicalName(), paramNames.get(i));
        }
        builder.append("));");
        return builder.build();
    }

    private Method method(String name, Class type, int parameterCount) {
        Method method = null;
        for (Method m : type.getMethods()) {
            if (m.getName().equals(name) && m.getParameterCount() == parameterCount) {
                if (method == null) {
                    method = m;
                } else {
                    throw new StandardException("invalid method, type={}, method={}", type, name);
                }
            }
        }
        if (method == null) {
            throw new StandardException("missing method, type={}, method={}", type, name);
        }
        return method;
    }
}
