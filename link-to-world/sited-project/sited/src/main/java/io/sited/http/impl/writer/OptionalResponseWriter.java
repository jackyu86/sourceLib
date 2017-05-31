package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;


public class OptionalResponseWriter implements ResponseWriter<Optional> {
    @Override
    public void write(Request request, Optional body) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (body.isPresent()) {
            exchange.setStatusCode(200);
            exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(body.get())));
        } else {
            exchange.setStatusCode(204);
        }
    }
}
