package io.sited.web.impl.web;

import com.google.common.collect.Maps;
import io.sited.Site;
import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.sited.http.impl.TemplateBody;
import io.sited.template.Template;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateContext;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateBodyResponseWriter implements ResponseWriter<TemplateBody> {
    private final TemplateConfig templateConfig;
    private final Site site;

    public TemplateBodyResponseWriter(TemplateConfig templateConfig, Site site) {
        this.templateConfig = templateConfig;
        this.site = site;
    }

    @Override
    public void write(Request request, TemplateBody body) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        Template template = templateConfig.get(body.templatePath);
        StringWriter writer = new StringWriter();
        Map<String, Object> bindings = Maps.newHashMapWithExpectedSize(body.context.size() + 1);
        bindings.putAll(body.context);
        bindings.put("_request", request);
        bindings.put("site", site);
        TemplateContext templateContext = new TemplateContext();
        templateContext.bindings = bindings;
        template.output(templateContext, writer);
        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "text/html");
        exchange.getResponseSender().send(writer.toString());
    }
}