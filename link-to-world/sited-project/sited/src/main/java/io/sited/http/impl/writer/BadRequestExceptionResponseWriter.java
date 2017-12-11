package io.sited.http.impl.writer;

import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.Message;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class BadRequestExceptionResponseWriter implements ResponseWriter<BadRequestException> {
    @Inject
    I18nConfig i18nConfig;

    @Override
    public void write(Request request, BadRequestException e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange)) {
            exchange.setStatusCode(400);

            BadRequestExceptionResponse response = new BadRequestExceptionResponse();
            response.message = "bad request";

            List<BadRequestException.InvalidField> invalidFields = e.invalidFields();
            invalidFields.forEach(invalidField -> {
                Optional<Message> message = i18nConfig.message(invalidField.messageKey, request.locale());
                if (message.isPresent()) {
                    invalidField.message = message.get().get(invalidField.args);
                }
            });
            response.invalidFields = invalidFields;
            exchange.getResponseHeaders().put(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(response)));
        } else {
            exchange.setStatusCode(400);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            exchange.getResponseSender().send(writer.toString());
        }
    }

    public static class BadRequestExceptionResponse extends StandardExceptionResponseWriter.StandardExceptionResponse {
        public List<BadRequestException.InvalidField> invalidFields;
    }
}
