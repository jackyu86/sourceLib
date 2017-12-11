package io.sited.template.impl.code;


/**
 * @author chi
 */
public interface Objects {
    static Object add(Object object, Object another) {
        if (object instanceof Number && another instanceof Number) {
            boolean isDouble = object instanceof Double || another instanceof Double;
            if (isDouble) {
                return ((Number) object).doubleValue() + ((Number) another).doubleValue();
            } else {
                return ((Number) object).intValue() + ((Number) another).intValue();
            }
        }
        return "" + object + another;
    }

    static Boolean equals(Object object, Object another) {
        return java.util.Objects.equals(object, another);
    }

    static Boolean notEquals(Object object, Object another) {
        return !java.util.Objects.equals(object, another);
    }

    static Boolean gt(Object object, Object another) {
        if (object instanceof Number && another instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) another).doubleValue() > 0;
        }
        return false;
    }

    static Boolean gte(Object object, Object another) {
        if (object instanceof Number && another instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) another).doubleValue() >= 0;
        }
        return false;
    }

    static Boolean lt(Object object, Object another) {
        if (object instanceof Number && another instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) another).doubleValue() < 0;
        }
        return false;
    }

    static Boolean lte(Object object, Object another) {
        if (object instanceof Number && another instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) another).doubleValue() <= 0;
        }
        return false;
    }

    static Boolean not(Object value) {
        if (value instanceof Boolean) {
            return !(Boolean) value;
        }
        return false;
    }

    static Object object(Object value) {
        return value;
    }

    static Object object(boolean value) {
        return value;
    }

    static Object object(int value) {
        return value;
    }

    static Object object(long value) {
        return value;
    }

    static Object object(double value) {
        return value;
    }

    static Object object(char value) {
        return value;
    }

    static Object object(float value) {
        return value;
    }
}
