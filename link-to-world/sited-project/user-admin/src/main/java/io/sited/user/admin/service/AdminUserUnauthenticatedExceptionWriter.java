package io.sited.user.admin.service;

import com.google.common.collect.Maps;
import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.sited.user.admin.exception.AdminUserUnauthenticatedException;
import io.sited.util.JSON;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * @author chi
 */
public class AdminUserUnauthenticatedExceptionWriter implements ResponseWriter<AdminUserUnauthenticatedException> {
    @Override
    public void write(Request request, AdminUserUnauthenticatedException e) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        if (isJSONBody(exchange) || isTextHtmlBody(exchange)) {
            exchange.setStatusCode(403);
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("message", e.getMessage());
            map.put("fromURL", exchange.getRequestURL());
            exchange.getResponseSender().send(ByteBuffer.wrap(JSON.toJSONBytes(map)));
        } else {
            exchange.setStatusCode(302);
            exchange.getResponseHeaders().put(new HttpString("location"), "/admin/login?fromURL=" + exchange.getRequestURL());
        }
    }
}
