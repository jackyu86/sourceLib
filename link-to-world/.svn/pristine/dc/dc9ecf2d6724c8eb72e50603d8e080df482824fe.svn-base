package io.sited.template.impl.element.writer;

import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.TemplateContext;
import io.sited.template.impl.element.ElementImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author chi
 */
public class StaticElementBodyWriterImplTest {
    @Test
    public void output() throws IOException {
        Node node = new Node();
        node.name = "div";

        ElementImpl element = new ElementImpl(node, Maps.newHashMap(), new StaticElementWriterImpl());
        StringWriter writer = new StringWriter();
        element.output(context(Maps.newHashMap()), writer);
        Assert.assertEquals("<div></div>", writer.toString());
    }

    private TemplateContext context(Map<String, Object> bindings) {
        TemplateContext context = new TemplateContext();
        context.bindings = bindings;
        return context;
    }
}