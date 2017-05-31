package io.sited.http.impl;

import io.sited.Provider;
import io.sited.http.Request;
import io.undertow.server.HttpServerExchange;

/**
 * @author chi
 */
public class HttpServerExchangeProviderImpl implements Provider<HttpServerExchange, Request> {
    @Override
    public HttpServerExchange get(Request scope) {
        return (HttpServerExchange) scope.context().get(HttpServerExchange.class);
    }
}
