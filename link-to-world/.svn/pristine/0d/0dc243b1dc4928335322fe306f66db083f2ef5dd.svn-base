package io.sited.template.impl.node;

import io.sited.template.Node;
import io.sited.template.impl.node.processor.ForNodeProcessorImpl;
import io.sited.template.impl.node.processor.IfNodeProcessorImpl;
import io.sited.util.source.StringSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class TemplateParserImplTest {
    @Test
    public void parse() {
        StringSource source = new StringSource("/test.html", "<div j:if=\"test\" j:for=\"item:list\"></div>");
        TemplateParserImpl templateParser = new TemplateParserImpl();
        templateParser.addNodeProcessor(new IfNodeProcessorImpl());
        templateParser.addNodeProcessor(new ForNodeProcessorImpl());
        Node node = templateParser.parse(source);
        Assert.assertEquals(1, node.children.size());
        Assert.assertEquals("j:for", node.children.get(0).name);
        Assert.assertEquals("item:list", node.children.get(0).attributes.get("j:for"));
    }

    @Test
    public void text() {
        StringSource source = new StringSource("/test.html", "<span>span</span>");
        TemplateParserImpl templateParser = new TemplateParserImpl();

        Node node = templateParser.parse(source);
        Assert.assertEquals(1, node.children.get(0).children.size());
    }
}