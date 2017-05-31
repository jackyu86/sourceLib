package io.sited.template.impl.element.writer;

import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class RootElementWriterImpl implements ElementWriter {
    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        for (Element child : element.children()) {
            child.output(context, writer);
        }
    }
}
