package com.caej.site.page.service;

import io.sited.template.Attribute;
import io.sited.template.TemplateContext;
import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.attribute.writer.DynamicAttributeWriterImpl;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class ScriptSrcAttributeWriter extends DynamicAttributeWriterImpl {
    private final String build;

    public ScriptSrcAttributeWriter(ExpressionEngine expressionEngine, String build) {
        super(expressionEngine);
        this.build = build;
    }

    @Override
    public void output(Attribute attribute, TemplateContext context, Writer writer) throws IOException {
        String value = attribute.value();
        if (value.contains("?")) {
            value += "&t=" + build;
        } else {
            value += "?t=" + build;
        }
        writer.write(" src=\"");
        writer.write(value);
        writer.write("\"");
    }
}
