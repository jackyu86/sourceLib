package io.sited.util;

import io.sited.StandardException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 */
public final class Types {
    public static Type generic(Class<?> rawType, Type[] arguments) {
        return new ParameterizedTypeImpl(rawType, arguments, null);
    }

    public static Type generic(Class<?> rawType, Type argument) {
        return new ParameterizedTypeImpl(rawType, new Type[]{argument}, null);
    }

    public static String className(Type type) {
        if (type instanceof Class) {
            return ((Class) type).getCanonicalName();
        } else {
            return ((Class) ((ParameterizedType) type).getRawType()).getCanonicalName();
        }
    }

    public static Type list(Type valueType) {
        return generic(List.class, valueType);
    }

    public static Type map(Type keyType, Type valueType) {
        return generic(Map.class, new Type[]{keyType, valueType});
    }

    public static Type supplier(Type valueType) {
        return generic(Supplier.class, valueType);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> raw(Type type) {
        if (type instanceof Class) {
            return (Class<T>) type;
        } else {
            return (Class) ((ParameterizedType) type).getRawType();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(String name) {
        try {
            return (Class<T>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new StandardException(e);
        }
    }

    public static Class<?> rawClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new StandardException("unsupported type, type={}", type);
        }
    }

    public static boolean isList(Type type) {
        return List.class.isAssignableFrom(rawClass(type));
    }

    public static boolean isGenericList(Type type) {
        if (type instanceof ParameterizedType) {
            Class<?> rawClass = (Class<?>) ((ParameterizedType) type).getRawType();
            return List.class.isAssignableFrom(rawClass)
                && ((ParameterizedType) type).getActualTypeArguments()[0] instanceof Class;
        }
        return false;
    }

    public static boolean isGeneric(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        return false;
    }


    public static Class<?> listValueClass(Type type) {
        return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    public static boolean isOptional(Type type) {
        return Optional.class.isAssignableFrom(rawClass(type));
    }

    public static boolean isGenericOptional(Type type) {
        if (type instanceof ParameterizedType) {
            Class<?> rawClass = (Class<?>) ((ParameterizedType) type).getRawType();
            return Optional.class.isAssignableFrom(rawClass)
                && ((ParameterizedType) type).getActualTypeArguments()[0] instanceof Class;
        }
        return false;
    }

    public static Class<?> optionalValueClass(Type type) {
        return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    public static boolean isMap(Type type) {
        return Map.class.isAssignableFrom(rawClass(type));
    }

    public static boolean isGenericStringMap(Type type) {
        if (type instanceof ParameterizedType) {
            Type keyType = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (!(keyType instanceof Class)) return false;
            Class keyClass = (Class) keyType;
            return String.class.equals(keyClass)
                && ((ParameterizedType) type).getActualTypeArguments()[1] instanceof Class;
        }
        return false;
    }

    public static Class<?> mapValueClass(Type type) {
        return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];
    }

    private static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type rawType;
        private final Type[] arguments;
        private final Type ownerType;

        ParameterizedTypeImpl(Class<?> rawType, Type[] arguments, Type ownerType) {
            this.rawType = rawType;
            this.arguments = arguments;
            this.ownerType = ownerType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return arguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public int hashCode() {
            return (ownerType == null ? 0 : ownerType.hashCode())
                ^ Arrays.hashCode(arguments)
                ^ rawType.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType that = (ParameterizedType) other;
            return rawType.equals(that.getRawType())
                && Objects.equals(ownerType, that.getOwnerType())
                && Arrays.equals(arguments, that.getActualTypeArguments());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (ownerType != null) {
                builder.append(ownerType.getTypeName()).append('.');
            }
            builder.append(rawType.getTypeName())
                .append('<');

            int i = 0;
            for (Type argument : arguments) {
                if (i > 0) builder.append(", ");
                builder.append(argument.getTypeName());
                i++;
            }

            builder.append('>');
            return builder.toString();
        }
    }

}
