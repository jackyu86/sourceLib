package io.sited.template;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public interface Element {
    Node node();

    List<Element> children();

    Map<String, Attribute> attributes();

    Optional<Attribute> attribute(String name);

    void output(TemplateContext context, Writer writer) throws IOException;
}
