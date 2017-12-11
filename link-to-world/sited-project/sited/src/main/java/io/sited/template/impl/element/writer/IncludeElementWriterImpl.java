package io.sited.template.impl.element.writer;

import com.google.common.collect.Maps;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import io.sited.template.TemplateEngine;
import io.sited.template.impl.TemplateImpl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author chi
 */
public class IncludeElementWriterImpl implements ElementWriter {
    private final TemplateEngine templateEngine;

    public IncludeElementWriterImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        String path = "/include/" + element.node().name.substring(2) + ".html";

        TemplateImpl template = (TemplateImpl) templateEngine.template(path);

        Map<String, Object> fragmentBindings = Maps.newHashMap();
        fragmentBindings.putAll(context.bindings);
        for (Attribute attribute : element.attributes().values()) {
            fragmentBindings.put(attribute.name(), attribute.eval(context.bindings));
        }

        Map<String, Object> original = context.bindings;
        boolean include = context.include;
        context.include = true;
        context.bindings = fragmentBindings;
        template.output(context, writer);
        context.bindings = original;
        context.include = include;
    }
}
