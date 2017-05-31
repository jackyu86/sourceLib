package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;

/**
 * @author chi
 */
public class StandardExceptionResponseWriter implements ResponseWriter<Throwable> {
    @Override
    public void write(Request request, Throwable e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange)) {
            exchange.setStatusCode(500);
            StandardExceptionResponse response = new StandardExceptionResponse();
            response.message = e.getMessage();
            exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(response)));
        } else {
            exchange.setStatusCode(500);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            exchange.getResponseSender().send(writer.toString());
        }
    }

    public static class StandardExceptionResponse {
        public String message;
    }
}
