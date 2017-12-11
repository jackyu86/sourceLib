package io.sited.template.impl.attribute.writer;

import com.google.common.collect.Maps;
import io.sited.template.TemplateContext;
import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.Model;
import io.sited.template.impl.attribute.AttributeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chi
 */
public class DynamicAttributeBodyWriterImplTest {
    DynamicAttributeWriterImpl dynamicAttributeWriter;

    @Before
    public void setup() {
        dynamicAttributeWriter = new DynamicAttributeWriterImpl(new ExpressionEngine());
    }

    @Test
    public void output() throws IOException {
        AttributeImpl attribute = new AttributeImpl("name", "model.stringValue", null, dynamicAttributeWriter);
        HashMap<String, Object> context = Maps.newHashMap();
        context.put("model", new Model());

        StringWriter writer = new StringWriter();
        dynamicAttributeWriter.output(attribute, context(context), writer);
        Assert.assertEquals(" name=\"string\"", writer.toString());
    }

    private TemplateContext context(Map<String, Object> bindings) {
        TemplateContext context = new TemplateContext();
        context.bindings = bindings;
        return context;
    }
}