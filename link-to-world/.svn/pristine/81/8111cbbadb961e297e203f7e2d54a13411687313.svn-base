package io.sited.template.impl.attribute.writer;

import com.google.common.collect.Maps;
import io.sited.template.TemplateContext;
import io.sited.template.impl.attribute.AttributeImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author chi
 */
public class StaticAttributeBodyWriterImplTest {
    @Test
    public void output() throws IOException {
        AttributeImpl attribute = new AttributeImpl("name", "value", null, new StaticAttributeWriterImpl());
        StringWriter writer = new StringWriter();
        attribute.output(context(Maps.newHashMap()), writer);
        Assert.assertEquals(" name=\"value\"", writer.toString());
    }

    private TemplateContext context(Map<String, Object> bindings) {
        TemplateContext context = new TemplateContext();
        context.bindings = bindings;
        return context;
    }
}