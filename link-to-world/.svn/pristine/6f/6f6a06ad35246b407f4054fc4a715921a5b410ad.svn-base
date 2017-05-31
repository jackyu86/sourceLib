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
public class IfElementWriterImpl implements ElementWriter {
    private final ExpressionEngine expressionEngine;

    public IfElementWriterImpl(ExpressionEngine expressionEngine) {
        this.expressionEngine = expressionEngine;
    }

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Attribute attribute = element.attributes().get("if");
        Expression expression = expressionEngine.expression(attribute.value(), context.bindings);
        Object condition = expression.eval(context.bindings);
        if (condition != null && (Boolean) condition) {
            for (Element child : element.children()) {
                child.output(context, writer);
            }
        }
    }
}
