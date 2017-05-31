package io.sited.template.impl;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.AttributeWriter;
import io.sited.template.ElementWriter;
import io.sited.template.Filter;
import io.sited.template.Node;
import io.sited.template.Template;
import io.sited.template.TemplateEngine;
import io.sited.template.impl.element.ElementCompilerImpl;
import io.sited.template.impl.element.ElementImpl;
import io.sited.template.impl.node.NodeProcessor;
import io.sited.template.impl.node.TemplateParserImpl;
import io.sited.util.source.CompositeSourceRepository;
import io.sited.util.source.Source;
import io.sited.util.source.SourceRepository;

import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class TemplateEngineImpl implements TemplateEngine {
    private final CompositeSourceRepository repository = new CompositeSourceRepository();
    private final TemplateParserImpl templateParser = new TemplateParserImpl();
    private final ExpressionEngine expressionEngine = new ExpressionEngine();
    private final ElementCompilerImpl elementCompiler = new ElementCompilerImpl(this, expressionEngine);
    private final Map<String, Template> cache = Maps.newHashMap();
    private volatile Boolean cacheEnabled = true;

    @Override
    public Template template(String path) {
        if (cacheEnabled && cache.containsKey(path)) {
            return cache.get(path);
        }
        Optional<Source> source = repository.get(path);
        if (!source.isPresent()) {
            throw new StandardException("template not found, path={}", path);
        }
        Node node = templateParser.parse(source.get());
        ElementImpl element = elementCompiler.compile(node);
        TemplateImpl template = new TemplateImpl(element);
        cache.put(path, template);
        return template;
    }

    @Override
    public TemplateEngine setWriter(String name, ElementWriter elementWriter) {
        elementCompiler.setElementWriter(name, elementWriter);
        return this;
    }

    @Override
    public TemplateEngine setWriter(String name, AttributeWriter attributeWriter) {
        elementCompiler.setAttributeWriter(name, attributeWriter);
        return this;
    }

    @Override
    public TemplateEngine setFilter(String name, Filter filter) {
        expressionEngine.setFilter(name, filter);
        return this;
    }

    @Override
    public TemplateEngine addRepository(SourceRepository repository) {
        this.repository.add(repository);
        return this;
    }

    @Override
    public TemplateEngine addNodeProcessor(NodeProcessor nodeProcessor) {
        templateParser.addNodeProcessor(nodeProcessor);
        return this;
    }

    @Override
    public TemplateEngine setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        return this;
    }

    @Override
    public SourceRepository repository() {
        return repository;
    }

    public ExpressionEngine expressionEngine() {
        return expressionEngine;
    }
}
