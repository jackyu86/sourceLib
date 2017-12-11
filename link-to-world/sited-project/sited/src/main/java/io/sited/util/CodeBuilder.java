package io.sited.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class CodeBuilder {
    private final StringBuilder builder = new StringBuilder(256);

    public static String enumVariableLiteral(Enum value) {
        return value.getDeclaringClass().getCanonicalName() + "." + value.name();
    }

    public static String typeVariableLiteral(Type type) {
        StringBuilder b = new StringBuilder();
        if (Types.isGeneric(type)) {
            b.append(Types.class.getCanonicalName());
            b.append('.');
            b.append("generic(");
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class ownerType = (Class) parameterizedType.getRawType();
            if (Types.isMap(ownerType)) {
                b.append(Map.class.getCanonicalName());
            } else if (Types.isList(ownerType)) {
                b.append(List.class.getCanonicalName());
            } else {
                b.append(ownerType.getCanonicalName());
            }
            b.append(".class, new java.lang.reflect.Type[]{");

            Type[] arguments = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < arguments.length; i++) {
                if (i != 0) {
                    b.append(',');
                }
                b.append(typeVariableLiteral(arguments[i]));
            }
            b.append("})");
        } else {
            b.append(((Class) type).getCanonicalName());
            b.append(".class");
        }
        return b.toString();
    }

    public CodeBuilder prepend(String text) {
        builder.insert(0, text);
        return this;
    }

    public CodeBuilder prepend(String pattern, Object... argument) {
        builder.insert(0, String.format(pattern, argument));
        return this;
    }

    public CodeBuilder append(String text) {
        builder.append(text);
        return this;
    }

    public CodeBuilder append(String pattern, Object... argument) {
        builder.append(String.format(pattern, argument));
        return this;
    }

    public CodeBuilder indent(int indent) {
        for (int i = 0; i < indent; i++)
            builder.append("    ");
        return this;
    }

    public String build() {
        return builder.toString();
    }
}
