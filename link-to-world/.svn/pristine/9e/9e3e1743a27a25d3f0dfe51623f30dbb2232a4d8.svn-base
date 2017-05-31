package io.sited.template.impl;

import io.sited.template.Template;
import io.sited.template.TemplateContext;
import io.sited.template.impl.element.DocumentImpl;
import io.sited.template.impl.element.ElementImpl;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class TemplateImpl implements Template {
    public final DocumentImpl doc;

    public TemplateImpl(ElementImpl doc) {
        this.doc = new DocumentImpl(doc);
    }

    @Override
    public void output(TemplateContext context, Writer writer) throws IOException {
        doc.output(context, writer);
    }
}
