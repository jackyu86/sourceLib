package com.caej.site;

import com.google.common.collect.Maps;
import io.sited.Site;
import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.sited.template.Template;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateContext;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author chi
 */
public class ThrowableBodyWriter implements ResponseWriter<Throwable> {
    @Inject
    TemplateConfig templateConfig;

    @Inject
    Site site;

    @Override
    public void write(Request request, Throwable e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange)) {
            exchange.setStatusCode(500);
            Map<String, String> response = Maps.newHashMapWithExpectedSize(1);
            response.put("message", e.getMessage());
            exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(response)));
        } else {
            Template template = templateConfig.get("/500.html");
            TemplateContext templateContext = new TemplateContext();
            templateContext.bindings = Maps.newHashMap();
            templateContext.bindings.put("_request", request);
            templateContext.bindings.put("site", site);
            StringWriter writer = new StringWriter();
            template.output(templateContext, writer);
            exchange.getResponseSender().send(writer.toString());
        }
    }
}
