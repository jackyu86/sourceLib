package io.sited.template.impl.element;

import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.Node;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DocumentImpl implements Element {
    public final ElementImpl element;
    public HtmlImpl html;

    public DocumentImpl(ElementImpl element) {
        this.element = element;

        for (Element child : element.children()) {
            if ("html".equals(child.node().name)) {
                html = new HtmlImpl(child);
            }
        }
    }

    @Override
    public Node node() {
        return element.node();
    }

    @Override
    public List<Element> children() {
        return element.children();
    }

    @Override
    public Map<String, Attribute> attributes() {
        return element.attributes();
    }

    @Override
    public Optional<Attribute> attribute(String name) {
        return Optional.ofNullable(attributes().get(name));
    }

    @Override
    public void output(TemplateContext context, Writer writer) throws IOException {
        if (context.include) {
            if (html != null && html.head != null) {
                context.jsFiles.addAll(html.head.jsFiles);
                context.cssFiles.addAll(html.head.cssFiles);
            }

            if (html != null && html.body != null) {
                html.body.output(context, writer);
            }
        } else {
            for (Element child : element.children()) {
                if ("html".equals(child.node().name)) {
                    html.output(context, writer);
                } else {
                    child.output(context, writer);
                }
            }
        }
    }
}