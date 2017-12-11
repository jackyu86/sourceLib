package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteArrayResponseWriter implements ResponseWriter<byte[]> {
    @Override
    public void write(Request request, byte[] response) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        exchange.getResponseSender().send(ByteBuffer.wrap(response));
    }
}