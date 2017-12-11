package io.sited.template.impl.attribute.writer;

import io.sited.template.Attribute;
import io.sited.template.AttributeWriter;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author chi
 */
public class StaticAttributeWriterImpl implements AttributeWriter {
    @Override
    public void output(Attribute attribute, TemplateContext context, Writer writer) throws IOException {
        writer.write(" ");
        writer.write(attribute.name());

        if (!attribute.isBoolAttribute()) {
            writer.write("=\"");
            writer.write(attribute.value());
            writer.write("\"");
        }
    }

    @Override
    public Object eval(Attribute attribute, Map<String, Object> context) {
        return attribute.value();
    }
}
