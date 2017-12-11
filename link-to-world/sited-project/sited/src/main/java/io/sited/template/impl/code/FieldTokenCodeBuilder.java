package io.sited.template.impl.code;

import io.sited.StandardException;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;
import io.sited.util.Types;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author chi
 */
public class FieldTokenCodeBuilder extends TokenCodeBuilder {
    public FieldTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        CodeBuilder b = new CodeBuilder();
        b.append("if(%s==null){", context.baseName);

        if (token.hasNext()) {
            b.append("%s=\"${%s}\";", context.returnName, token.toString());
        } else {
            b.append("%s=null;", context.returnName);
        }

        b.append("}else{");

        if (isMapType(context.baseClass)) {
            String fieldName = variable(context.baseName, token.content);

            Class<?> bindingType = context.bindings.get(token.content);
            if (bindingType == null) {
                b.append("%s %s=((%s)%s).get(\"%s\");", Object.class.getCanonicalName(), fieldName, Map.class.getCanonicalName(), context.baseName, token.content);

                if (token.hasNext()) {
                    throw new StandardException("invalid map expression, field={}", token.content);
                } else {
                    b.append("%s=%s;", context.returnName, fieldName);
                }
            } else {
                b.append("%s %s=(%s)((%s)%s).get(\"%s\");", bindingType.getCanonicalName(), fieldName, bindingType.getCanonicalName(), Map.class.getCanonicalName(), context.baseName, token.content);
                if (token.hasNext()) {
                    b.append(builder(token.next().type).code(token.next(), context.base(fieldName, bindingType)));
                } else {
                    b.append("%s=%s;", context.returnName, fieldName);
                }
            }
        } else {
            Class<?> baseClass = context.baseClass;
            Field field = field(baseClass, token.content);
            String fieldName = variable(context.baseName, field.getName());
            b.append("%s %s=((%s)%s).%s;", field.getType().getCanonicalName(), fieldName, baseClass.getCanonicalName(), context.baseName, token.content);

            if (token.hasNext()) {
                Token next = token.next();
                b.append(builder(next.type).code(next, context.base(fieldName, field.getType())));
            } else {
                b.append("%s=%s.object(%s);", context.returnName, Objects.class.getCanonicalName(), fieldName);
            }
        }
        b.append("}");
        return b.build();
    }

    private boolean isMapType(Type type) {
        return Map.class.isAssignableFrom(Types.raw(type));
    }

    private Field field(Class<?> baseClass, String field) {
        try {
            return baseClass.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            throw new StandardException("missing field, type={}, field={}", baseClass, field, e);
        }
    }
}
