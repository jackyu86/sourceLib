package io.sited.template.impl;

import com.google.common.collect.Maps;
import io.sited.db.FindView;
import io.sited.template.Template;
import io.sited.template.TemplateContext;
import io.sited.template.TemplateEngine;
import io.sited.util.source.ClasspathSourceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class TemplateEngineImplTest {
    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        templateEngine = new TemplateEngineImpl();
        templateEngine.addRepository(new ClasspathSourceRepository("template"));
    }

    @Test
    public void simple() throws IOException {
        Template template = templateEngine.template("/simple.html");
        TemplateContext context = new TemplateContext();
        Map<String, Object> bindings = Maps.newHashMap();
        bindings.put("model", new Model());
        bindings.put("map", Collections.singletonMap("field", "field"));
        context.bindings = bindings;

        StringWriter writer = new StringWriter();

        template.output(context, writer);

        Assert.assertEquals("<!DOCTYPE html><html lang=\"en\" xmlns:j=\"http://www.w3.org/1999/xhtml\"><head><meta charset=\"UTF-8\"/><title>Template Test</title></head><body><span>string</span><span>1</span><span>field</span></body></html>", writer.toString());
    }

    @Test
    public void html() throws IOException {
        Template template = templateEngine.template("/html.html");
        TemplateContext context = new TemplateContext();
        Map<String, Object> bindings = Maps.newHashMap();
        bindings.put("model", new Model());
        context.bindings = bindings;

        StringWriter writer = new StringWriter();
        template.output(context, writer);

        Assert.assertEquals("<!DOCTYPE html><html lang=\"en-US\" xmlns:j=\"http://www.w3.org/1999/xhtml\"><head><meta charset=\"UTF-8\"/><title>null</title></head><body><div><div><span>span</span></div></div></body></html>", writer.toString());
    }

    @Test
    public void include() throws IOException {
        Template template = templateEngine.template("/include-test.html");
        TemplateContext context = new TemplateContext();
        context.bindings = Maps.newHashMap();
        StringWriter writer = new StringWriter();
        template.output(context, writer);

        Assert.assertEquals("<!DOCTYPE html><html lang=\"en-US\" xmlns:j=\"http://app4j.org\"><head><meta charset=\"UTF-8\"/><title>Include Test</title><link rel=\"stylesheet\" type=\"text/css\" href=\"../static/css/header.css\"></head><body><span>test</span><ul class=\"list\"><li class=\"active item\">Home</li><li class=\" item\">Setting</li></ul><script src=\"../static/js/header.js\"></script></body></html>", writer.toString());
    }

    @Test
    public void pagination() throws IOException {
        Template template = templateEngine.template("/pagination.html");
        TemplateContext context = new TemplateContext();
        Map<String, Object> bindings = Maps.newHashMap();
        FindView<String> findView = new FindView<>();
        findView.page = 1;
        findView.limit = 10;
        findView.total = 120L;
        bindings.put("list", findView);
        context.bindings = bindings;
        StringWriter writer = new StringWriter();
        template.output(context, writer);
        Assert.assertEquals("<!DOCTYPE html><html lang=\"en\" xmlns:j=\"http://www.w3.org/1999/xhtml\"><head><meta charset=\"UTF-8\"/><title>Template Test</title></head><body><li class=\"active\"><a href=\"/1/\">1</a></li><li><a href=\"/2/\">2</a></li><li><a href=\"/3/\">3</a></li><li><a href=\"/4/\">4</a></li><li><a href=\"/5/\">5</a></li><li><a href=\"/6/\">6</a></li><li class=\"next\"><a href=\"/2/\">»</a></li></body></html>", writer.toString());
    }
}