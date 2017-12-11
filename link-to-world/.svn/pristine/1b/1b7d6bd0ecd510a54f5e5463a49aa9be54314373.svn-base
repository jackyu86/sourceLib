package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.nio.ByteBuffer;


public class ObjectResponseWriter implements ResponseWriter<Object> {
    @Override
    public void write(Request request, Object body) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(body)));
    }
}
