package io.sited.template.impl.attribute.writer;

import com.google.common.base.Strings;
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
public class ClassAttributeWriterImpl implements AttributeWriter {
    private final ExpressionEngine expressionEngine;

    public ClassAttributeWriterImpl(ExpressionEngine expressionEngine) {
        this.expressionEngine = expressionEngine;
    }

    @Override
    public void output(Attribute attribute, TemplateContext context, Writer writer) throws IOException {
        StringBuilder b = new StringBuilder();

        Object value = eval(attribute, context.bindings);
        if (value != null) {
            b.append(value);
        }

        if (attribute.rawValue() != null) {
            b.append(' ');
            b.append(attribute.rawValue());
        }

        String className = b.toString();
        if (!Strings.isNullOrEmpty(className)) {
            writer.write(" ");
            writer.write(attribute.name());
            writer.write("=\"");
            writer.write(className);
            writer.write("\"");
        }
    }

    @Override
    public Object eval(Attribute attribute, Map<String, Object> bindings) {
        Expression expression = expressionEngine.expression(attribute.value(), bindings);
        return expression.eval(bindings);
    }
}
