package io.sited.template.impl.token;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class Tokens {
    private static final Pattern DOUBLE = Pattern.compile("\\d+\\.\\d+");
    private static final Pattern INTEGER = Pattern.compile("\\d+");

    public static boolean isIdentifier(String identifier) {
        if (!Character.isJavaIdentifierStart(identifier.charAt(0))) {
            return false;
        }
        for (int i = 1; i < identifier.length(); i++) {
            if (!Character.isJavaIdentifierPart(identifier.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String value) {
        return INTEGER.matcher(value).matches();
    }

    public static boolean isDouble(String value) {
        return DOUBLE.matcher(value).matches();
    }

    public static boolean isBool(String value) {
        return "true".equals(value) || "false".equals(value);
    }

    public static boolean isNull(String value) {
        return "null".equals(value);
    }

}
