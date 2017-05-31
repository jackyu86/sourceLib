package io.sited.template;

import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.node.NodeProcessor;
import io.sited.util.source.SourceRepository;

/**
 * @author chi
 */
public interface TemplateEngine {
    Template template(String path);

    TemplateEngine setWriter(String name, ElementWriter elementWriter);

    TemplateEngine setWriter(String name, AttributeWriter attributeWriter);

    TemplateEngine setFilter(String name, Filter filter);

    TemplateEngine addRepository(SourceRepository repository);

    TemplateEngine addNodeProcessor(NodeProcessor nodeProcessor);

    TemplateEngine setCacheEnabled(boolean cacheEnabled);

    SourceRepository repository();

    ExpressionEngine expressionEngine();
}
