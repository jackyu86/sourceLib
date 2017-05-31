package io.sited.template.impl.attribute.writer;

import io.sited.template.Attribute;
import io.sited.template.AttributeWriter;
import io.sited.template.TemplateContext;
import io.sited.template.impl.Expression;
import io.sited.template.impl.ExpressionEngine;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author chi
 */
public class DynamicAttributeWriterImpl implements AttributeWriter {
    private final ExpressionEngine expressionEngine;

    public DynamicAttributeWriterImpl(ExpressionEngine expressionEngine) {
        this.expressionEngine = expressionEngine;
    }

    @Override
    public void output(Attribute attribute, TemplateContext context, Writer writer) throws IOException {
        Object value = eval(attribute, context.bindings);
        if (value == null) {
            return;
        }

        if (attribute.isBoolAttribute() && value instanceof Boolean) {
            if ((Boolean) value) {
                writer.write(" ");
                writer.write(attribute.name());
            }
        } else {
            writer.write(" ");
            writer.write(attribute.name());
            writer.write("=\"");
            writer.write(HtmlEncoder.encode(value.toString()));
            writer.write("\"");
        }
    }

    @Override
    public Object eval(Attribute attribute, Map<String, Object> bindings) {
        Expression expression = expressionEngine.expression(attribute.value(), bindings);
        return expression.eval(bindings);
    }
}
