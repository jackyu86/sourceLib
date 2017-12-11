package io.sited.template.impl.element;

import com.google.common.collect.Lists;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.Node;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class ElementImpl implements Element {
    public final Node node;
    public final Map<String, Attribute> attributes;
    public final ElementWriter elementWriter;
    public final List<Element> children = Lists.newArrayList();

    public ElementImpl(Node node, Map<String, Attribute> attributes, ElementWriter elementWriter) {
        this.node = node;
        this.attributes = attributes;
        this.elementWriter = elementWriter;
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public List<Element> children() {
        return children;
    }

    @Override
    public Map<String, Attribute> attributes() {
        return attributes;
    }

    @Override
    public Optional<Attribute> attribute(String name) {
        return Optional.ofNullable(attributes().get(name));
    }

    @Override
    public void output(TemplateContext context, Writer writer) throws IOException {
        elementWriter.output(this, context, writer);
    }
}
