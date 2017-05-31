package io.sited.template.impl;

import java.util.Map;

/**
 * @author chi
 */
public class CodeContext {
    public final Map<String, Class<?>> bindings;
    public final String baseName;
    public final Class<?> baseClass;
    public final String returnName;

    public CodeContext(Map<String, Class<?>> bindings, String baseName, Class<?> baseClass, String returnName) {
        this.bindings = bindings;
        this.baseName = baseName;
        this.baseClass = baseClass;
        this.returnName = returnName;
    }

    public CodeContext base(String baseName, Class<?> baseClass) {
        return new CodeContext(bindings, baseName, baseClass, returnName);
    }

    public CodeContext returnName(String returnName) {
        return new CodeContext(bindings, baseName, baseClass, returnName);
    }
}
