package io.sited.user.web.service;

import io.sited.StandardException;
import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.sited.http.impl.RequestURLBuilder;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Collections;

import static io.sited.user.web.controller.UserAJAXController.COOKIE_FROM_URL;

/**
 * @author chi
 */
public class UnauthenticatedExceptionWriter<T extends StandardException> implements ResponseWriter<T> {
    @Override
    public void write(Request request, T e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange)) {
            exchange.setStatusCode(401);
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(Collections.singletonMap("message", e.getMessage()))));
        } else {
            String fromURL = URLEncoder.encode(new RequestURLBuilder(exchange).path(), "UTF-8");
            exchange.getResponseHeaders().add(new HttpString("location"), "/login?fromURL=" + fromURL);
            CookieImpl cookie = new CookieImpl(COOKIE_FROM_URL, fromURL).setPath("/");
            exchange.setResponseCookie(cookie);
            exchange.setStatusCode(302);
        }
    }
}
