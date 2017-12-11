package io.sited.template.impl.element;

import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.Node;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HtmlImpl implements Element {
    public final Element element;
    public HeadImpl head;
    public BodyImpl body;

    public HtmlImpl(Element element) {
        this.element = element;

        for (Element child : element.children()) {
            if ("head".equals(child.node().name)) {
                head = new HeadImpl(child);
            } else if ("body".equals(child.node().name)) {
                body = new BodyImpl(child);
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
            if (body != null) {
                body.output(context, writer);
            }
        } else {
            writer.write("<");
            writer.write(element.node().name);

            for (Attribute attribute : element.attributes().values()) {
                attribute.output(context, writer);
            }

            writer.write(">");

            StringWriter bodyWriter = null;
            if (body != null) {
                bodyWriter = new StringWriter();
                body.output(context, bodyWriter);
            }
            StringWriter headerWriter = null;
            if (head != null) {
                headerWriter = new StringWriter();
                head.output(context, headerWriter);
            }
            for (Element child : element.children()) {
                if ("head".equals(child.node().name) && headerWriter != null) {
                    writer.write(headerWriter.toString());
                } else if ("body".equals(child.node().name) && bodyWriter != null) {
                    writer.write(bodyWriter.toString());
                } else {
                    child.output(context, writer);
                }
            }

            writer.write("</");
            writer.write(element.node().name);
            writer.write(">");
        }
    }
}