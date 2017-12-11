package io.sited.template;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.cache.CacheModule;
import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.TemplateEngineImpl;
import io.sited.template.impl.filter.EllipsisFilter;
import io.sited.template.impl.filter.FormatFilter;
import io.sited.template.impl.filter.JSONFilter;
import io.sited.template.impl.node.NodeProcessor;
import io.sited.util.source.SourceRepository;

/**
 * @author chi
 */
@ModuleInfo(name = "template", require = {CacheModule.class})
public class TemplateModule extends Module implements TemplateConfig {
    private TemplateEngine engine;

    @Override
    protected void configure() throws Exception {
        TemplateOptions options = options(TemplateOptions.class);
        engine = new TemplateEngineImpl();
        if (!options.cacheEnabled) {
            engine.setCacheEnabled(false);
        }

        setFilter("json", new JSONFilter());
        setFilter("ellipsis", new EllipsisFilter());
        setFilter("format", new FormatFilter());

        bind(TemplateConfig.class, this);
        export(TemplateConfig.class);
        bind(ExpressionEngine.class, engine.expressionEngine());
        export(ExpressionEngine.class);
    }

    @Override
    public Template get(String path) {
        return engine.template(path);
    }

    @Override
    public TemplateConfig setElementWriter(String elementName, ElementWriter elementWriter) {
        engine.setWriter(elementName, elementWriter);
        return this;
    }

    @Override
    public TemplateConfig setAttributeWriter(String attributeName, AttributeWriter attributeWriter) {
        engine.setWriter(attributeName, attributeWriter);
        return this;
    }

    @Override
    public TemplateConfig setFilter(String filterName, Filter filter) {
        engine.setFilter(filterName, filter);
        return this;
    }

    @Override
    public TemplateConfig addRepository(SourceRepository repository) {
        engine.addRepository(repository);
        return this;
    }

    @Override
    public TemplateConfig addNodeProcessor(NodeProcessor nodeProcessor) {
        engine.addNodeProcessor(nodeProcessor);
        return this;
    }

    @Override
    public SourceRepository repository() {
        return engine.repository();
    }
}
