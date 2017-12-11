package io.sited.http;

import io.undertow.server.HttpServerExchange;

import java.io.IOException;

/**
 * @author chi
 */
public interface ResponseWriter<T> {
    void write(Request request, T response) throws IOException;

    default boolean isJSONBody(HttpServerExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        String accept = exchange.getRequestHeaders().getFirst("Accept");
        return contentType != null && contentType.contains("application/json") || accept != null && accept.contains("application/json");
    }

    default boolean isTextHtmlBody(HttpServerExchange exchange){
        String accept = exchange.getRequestHeaders().getFirst("Accept");
        return accept != null && accept.equals("text/html");
    }
}
