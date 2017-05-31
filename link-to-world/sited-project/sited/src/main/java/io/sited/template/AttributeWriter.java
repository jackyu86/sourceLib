package io.sited.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public interface AttributeWriter {
    void output(Attribute attribute, TemplateContext context, Writer writer) throws IOException;

    Object eval(Attribute attribute, Map<String, Object> context);
}