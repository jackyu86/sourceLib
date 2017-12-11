package io.sited.template.impl.element;

import com.google.common.collect.Lists;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.Node;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeadImpl implements Element {
    public final Element element;
    public final List<String> jsFiles = Lists.newArrayList();
    public final List<String> cssFiles = Lists.newArrayList();

    public HeadImpl(Element element) {
        this.element = element;

        for (Element child : element.children()) {
            if ("script".equals(child.node().name) && child.attributes().containsKey("src")) {
                jsFiles.add(child.node().attributes.get("src"));
            } else if (child.node().name.equals("link") && "text/css".equals(child.node().attributes.get("type")) && child.attributes().containsKey("href")) {
                cssFiles.add(child.node().attributes.get("href"));
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
        writer.write("<");
        writer.write(element.node().name);

        for (Attribute attribute : element.attributes().values()) {
            attribute.output(context, writer);
        }

        writer.write(">");

        for (Element child : element.children()) {
            child.output(context, writer);
        }

        for (String cssFile : context.cssFiles) {
            writer.write(cssLink(cssFile));
        }

        writer.write("</");
        writer.write(element.node().name);
        writer.write(">");
    }

    private String cssLink(String cssFile) {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssFile + "\">";
    }
}