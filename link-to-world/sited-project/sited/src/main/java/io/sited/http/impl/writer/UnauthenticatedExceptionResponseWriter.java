package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.i18n.I18nConfig;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;

/**
 * @author chi
 */
public class UnauthenticatedExceptionResponseWriter implements ResponseWriter<UnauthenticatedException> {
    @Inject
    I18nConfig i18nConfig;

    @Override
    public void write(Request request, UnauthenticatedException e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange)) {
            exchange.setStatusCode(401);

            StandardExceptionResponseWriter.StandardExceptionResponse response = new StandardExceptionResponseWriter.StandardExceptionResponse();
            response.message = e.getMessage();
            exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(response)));
        } else {
            exchange.setStatusCode(401);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            exchange.getResponseSender().send(writer.toString());
        }
    }
}
