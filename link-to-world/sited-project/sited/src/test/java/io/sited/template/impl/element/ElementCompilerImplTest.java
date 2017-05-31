package io.sited.template.impl.element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.Element;
import io.sited.template.Node;
import io.sited.template.TemplateContext;
import io.sited.template.impl.ExpressionEngine;
import io.sited.template.impl.Model;
import io.sited.template.impl.TemplateEngineImpl;
import io.sited.template.impl.node.TemplateParserImpl;
import io.sited.util.source.StringSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chi
 */
public class ElementCompilerImplTest {
    private final ElementCompilerImpl elementCompiler = new ElementCompilerImpl(new TemplateEngineImpl(), new ExpressionEngine());

    @Test
    public void compile() throws IOException {
        StringSource source = new StringSource("/test.html", "<div id=\"div\"><span></span></div>");
        Node node = new TemplateParserImpl().parse(source);

        Element element = elementCompiler.compile(node);
        StringWriter writer = new StringWriter();
        element.output(context(Maps.newHashMap()), writer);
        Assert.assertEquals("<div id=\"div\"><span></span></div>", writer.toString());
    }

    @Test
    public void testIf() throws IOException {
        StringSource source = new StringSource("/test.html", "<div id=\"div\" j:if=\"embeddedModel.booleanValue\"><span></span></div>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        HashMap<String, Object> context = Maps.newHashMap();
        Model embeddedModel = new Model();
        embeddedModel.booleanValue = false;
        context.put("embeddedModel", embeddedModel);
        element.output(context(context), writer);
        Assert.assertEquals("", writer.toString());
    }

    @Test
    public void testFor() throws IOException {
        StringSource source = new StringSource("/test.html", "<div id=\"div\"><span j:for=\"item:list\" j:text=\"item\"></span></div>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        HashMap<String, Object> context = Maps.newHashMap();
        context.put("list", Lists.newArrayList("string1", "string2"));
        element.output(context(context), writer);
        Assert.assertEquals("<div id=\"div\"><span>string1</span><span>string2</span></div>", writer.toString());
    }

    @Test
    public void attribute() throws IOException {
        StringSource source = new StringSource("/test.html", "<div id=\"div\" j:id=\"id\"></div>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        HashMap<String, Object> context = Maps.newHashMap();
        context.put("id", "id");
        element.output(context(context), writer);
        Assert.assertEquals("<div id=\"id\"></div>", writer.toString());
    }

    @Test
    public void className() throws IOException {
        StringSource source = new StringSource("/test.html", "<div class=\"class1\" j:class=\"condition?class:class1\"></div>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        HashMap<String, Object> context = Maps.newHashMap();
        context.put("condition", true);
        context.put("class", "class2");
        context.put("class1", "class3");
        element.output(context(context), writer);
        Assert.assertEquals("<div class=\"class2 class1\"></div>", writer.toString());
    }

    @Test
    public void title() throws IOException {
        StringSource source = new StringSource("/test.html", "<title>title</title>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        TemplateContext context = context(Maps.newHashMap());
        context.title = "some";
        element.output(context, writer);
        Assert.assertEquals("<title>some</title>", writer.toString());
    }

    @Test
    public void titleWithText() throws IOException {
        StringSource source = new StringSource("/test.html", "<title j:text=\"title\">title</title>");
        Element element = elementCompiler.compile(new TemplateParserImpl().parse(source));
        StringWriter writer = new StringWriter();
        HashMap<String, Object> bindings = Maps.newHashMap();
        bindings.put("title", "text");
        TemplateContext context = context(bindings);
        context.title = "some";
        element.output(context, writer);
        Assert.assertEquals("<title>text</title>", writer.toString());
    }

    private TemplateContext context(Map<String, Object> bindings) {
        TemplateContext context = new TemplateContext();
        context.bindings = bindings;
        return context;
    }
}