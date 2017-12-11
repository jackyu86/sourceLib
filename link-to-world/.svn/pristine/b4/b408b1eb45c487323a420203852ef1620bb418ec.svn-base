package io.sited.http.impl;

import com.google.common.base.Strings;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * @author chi
 */
public class RequestURLBuilder {
    private final HttpServerExchange exchange;

    public RequestURLBuilder(HttpServerExchange exchange) {
        this.exchange = exchange;
    }

    public String url() {
        String scheme = exchange.getRequestScheme();
        int port;

        String xForwardedPort = exchange.getRequestHeaders().getFirst(Headers.X_FORWARDED_PORT);
        if (xForwardedPort != null) {
            int index = xForwardedPort.indexOf(',');
            if (index > 0)
                port = Integer.parseInt(xForwardedPort.substring(0, index));
            else
                port = Integer.parseInt(xForwardedPort);
        } else {
            port = exchange.getHostPort();
        }

        StringBuilder b = new StringBuilder(scheme)
            .append("://")
            .append(exchange.getHostName());
        if (!(("http".equals(scheme) && port == 80)
            || ("https".equals(scheme) && port == 443))) {
            b.append(':').append(port);
        }
        b.append(path());
        return b.toString();
    }

    public String path() {
        StringBuilder b = new StringBuilder();
        b.append(exchange.getRequestPath());
        String query = exchange.getQueryString();
        if (!Strings.isNullOrEmpty(query)) {
            b.append('?').append(query);
        }
        return b.toString();
    }
}
