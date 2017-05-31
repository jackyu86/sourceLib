package io.sited.template.impl.element;

import com.google.common.collect.Maps;
import io.sited.template.Attribute;
import io.sited.template.AttributeWriter;
import io.sited.template.ElementWriter;
import io.sited.template.Node;
import io.sited.template.TemplateEngine;
import io.sited.template.impl.ElementCompiler;
import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.attribute.AttributeImpl;
import io.sited.template.impl.attribute.writer.ClassAttributeWriterImpl;
import io.sited.template.impl.attribute.writer.DynamicAttributeWriterImpl;
import io.sited.template.impl.attribute.writer.StaticAttributeWriterImpl;
import io.sited.template.impl.element.writer.DocTypeElementWriterImpl;
import io.sited.template.impl.element.writer.ForElementWriterImpl;
import io.sited.template.impl.element.writer.HtmlElementWriterImpl;
import io.sited.template.impl.element.writer.IfElementWriterImpl;
import io.sited.template.impl.element.writer.IncludeElementWriterImpl;
import io.sited.template.impl.element.writer.PaginationElementWriter;
import io.sited.template.impl.element.writer.RawTextElementWriterImpl;
import io.sited.template.impl.element.writer.RootElementWriterImpl;
import io.sited.template.impl.element.writer.StaticElementWriterImpl;
import io.sited.template.impl.element.writer.TextElementWriterImpl;
import io.sited.template.impl.element.writer.TitleElementWriterImpl;

import java.util.Map;

/**
 * @author chi
 */
public class ElementCompilerImpl implements ElementCompiler {
    private final Map<String, ElementWriter> customElementWriters = Maps.newHashMap();
    private final ElementWriter staticElementWriter = new StaticElementWriterImpl();
    private final ElementWriter dynamicElementWriter;
    private final Map<String, AttributeWriter> customAttributeWriters = Maps.newHashMap();
    private final AttributeWriter staticAttributeWriter = new StaticAttributeWriterImpl();
    private final AttributeWriter dynamicAttributeWriter;

    public ElementCompilerImpl(TemplateEngine templateEngine, ExpressionEngine expressionEngine) {
        dynamicElementWriter = new IncludeElementWriterImpl(templateEngine);
        dynamicAttributeWriter = new DynamicAttributeWriterImpl(expressionEngine);

        customElementWriters.put("!doctype", new DocTypeElementWriterImpl());
        customElementWriters.put("j:if", new IfElementWriterImpl(expressionEngine));
        customElementWriters.put("j:for", new ForElementWriterImpl(expressionEngine));
        customElementWriters.put("j:text", new TextElementWriterImpl(expressionEngine));
        customElementWriters.put("j:html", new HtmlElementWriterImpl(expressionEngine));
        customElementWriters.put("j:pagination", new PaginationElementWriter());
        customElementWriters.put("title", new TitleElementWriterImpl());
        customElementWriters.put("TEXT", new RawTextElementWriterImpl());
        customElementWriters.put("ROOT", new RootElementWriterImpl());

        customAttributeWriters.put("j:class", new ClassAttributeWriterImpl(expressionEngine));
    }

    @Override
    public ElementImpl compile(Node node) {
        Map<String, Attribute> attributes = Maps.newLinkedHashMap();
        if (node.attributes != null) {
            node.attributes.forEach((name, value) -> {
                if (name.startsWith("j:")) {
                    String rawName = name.substring(2);
                    String defaultValue = node.attributes.getOrDefault(rawName, null);
                    Attribute attribute = new AttributeImpl(rawName, value, defaultValue, attributeWriter(name));
                    attributes.put(attribute.name(), attribute);
                } else if (!attributes.containsKey(name)) {
                    Attribute attribute = new AttributeImpl(name, value, null, attributeWriter(name));
                    attributes.put(attribute.name(), attribute);
                }
            });
        }
        ElementImpl element = new ElementImpl(node, attributes, elementWriter(node.name));
        if (node.children != null) {
            for (Node child : node.children) {
                element.children.add(compile(child));
            }
        }
        return element;
    }

    @Override
    public void setElementWriter(String elementName, ElementWriter elementWriter) {
        customElementWriters.put(elementName, elementWriter);
    }

    @Override
    public void setAttributeWriter(String attributeName, AttributeWriter attributeWriter) {
        customAttributeWriters.put(attributeName, attributeWriter);
    }

    private ElementWriter elementWriter(String name) {
        if (customElementWriters.containsKey(name)) {
            return customElementWriters.get(name);
        } else if (name.startsWith("j:")) {
            return dynamicElementWriter;
        } else {
            return staticElementWriter;
        }
    }

    private AttributeWriter attributeWriter(String name) {
        if (customAttributeWriters.containsKey(name)) {
            return customAttributeWriters.get(name);
        } else if (name.startsWith("j:")) {
            return dynamicAttributeWriter;
        } else {
            return staticAttributeWriter;
        }
    }
}
