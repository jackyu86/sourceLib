package io.sited.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public interface Attribute {
    String name();

    String value();

    String rawValue();

    boolean isBoolAttribute();

    void output(TemplateContext context, Writer writer) throws IOException;

    Object eval(Map<String, Object> bindings);
}