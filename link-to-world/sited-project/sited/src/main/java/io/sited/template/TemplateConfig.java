package io.sited.template;

import io.sited.template.impl.node.NodeProcessor;
import io.sited.util.source.SourceRepository;

/**
 * @author chi
 */
public interface TemplateConfig {
    Template get(String path);

    TemplateConfig setElementWriter(String elementName, ElementWriter elementWriter);

    TemplateConfig setAttributeWriter(String attributeName, AttributeWriter attributeWriter);

    TemplateConfig setFilter(String filterName, Filter filter);

    TemplateConfig addRepository(SourceRepository repository);

    TemplateConfig addNodeProcessor(NodeProcessor nodeProcessor);

    SourceRepository repository();
}
