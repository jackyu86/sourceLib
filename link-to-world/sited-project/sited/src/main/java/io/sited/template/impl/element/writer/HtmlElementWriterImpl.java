package io.sited.template.impl.element.writer;

import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import io.sited.template.impl.Expression;
import io.sited.template.impl.ExpressionEngine;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class HtmlElementWriterImpl implements ElementWriter {
    private final ExpressionEngine expressionEngine;

    public HtmlElementWriterImpl(ExpressionEngine expressionEngine) {
        this.expressionEngine = expressionEngine;
    }

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Attribute attribute = element.attributes().get("html");
        Expression expression = expressionEngine.expression(attribute.value(), context.bindings);
        Object value = expression.eval(context.bindings);
        if (value != null) {
            writer.write(value.toString());
        }
    }
}
