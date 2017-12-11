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

public class BodyImpl implements Element {
    public final Element element;

    public BodyImpl(Element element) {
        this.element = element;
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
            for (Element child : element.children()) {
                child.output(context, writer);
            }
        } else {
            writer.write("<");
            writer.write(element.node().name);

            for (Attribute attribute : element.attributes().values()) {
                attribute.output(context, writer);
            }

            writer.write(">");

            for (Element child : element.children()) {
                child.output(context, writer);
            }

            for (String jsFile : context.jsFiles) {
                writer.write(jsScript(jsFile));
            }

            writer.write("</");
            writer.write(element.node().name);
            writer.write(">");
        }
    }

    private String jsScript(String jsFile) {
        return "<script src=\"" + jsFile + "\"></script>";
    }
}