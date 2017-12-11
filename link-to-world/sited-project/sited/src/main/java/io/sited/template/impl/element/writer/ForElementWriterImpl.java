package io.sited.template.impl.element.writer;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import io.sited.template.impl.Expression;
import io.sited.template.impl.ExpressionEngine;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class ForElementWriterImpl implements ElementWriter {
    private static final Pattern STATEMENT_PATTERN = Pattern.compile("([a-zA-Z0-9]+)\\s*:\\s*([a-zA-Z0-9\\.\\(\\)]+)");
    private final ExpressionEngine expressionEngine;

    public ForElementWriterImpl(ExpressionEngine expressionEngine) {
        this.expressionEngine = expressionEngine;
    }

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Attribute attribute = element.attributes().get("for");
        Matcher matcher = STATEMENT_PATTERN.matcher(attribute.value());
        if (matcher.matches()) {
            String item = matcher.group(1);
            String target = matcher.group(2);

            Expression expression = expressionEngine.expression(target, context.bindings);
            Object list = expression.eval(context.bindings);

            if (list == null) {
                return;
            }

            if (!(list instanceof Iterable)) {
                throw new StandardException("invalid for expression, expression={}, items=%s", attribute.value(), list);
            }

            Map<String, Object> original = Maps.newHashMap();
            original.putAll(context.bindings);

            for (Object value : (Iterable) list) {
                Map<String, Object> itemBindings = Maps.newHashMap();
                itemBindings.putAll(context.bindings);
                context.bindings = itemBindings;
                itemBindings.put(item, value);
                for (Element child : element.children()) {
                    child.output(context, writer);
                }
                context.bindings = original;
            }
        } else {
            throw new StandardException("invalid for expression, expression={}", attribute.value());
        }
    }
}
