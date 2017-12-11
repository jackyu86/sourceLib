package io.sited.http.impl;

import com.google.common.base.Strings;
import io.sited.Provider;
import io.sited.Site;
import io.sited.http.Client;
import io.sited.http.Request;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.CookieImpl;
import io.undertow.util.Headers;

import java.util.UUID;

/**
 * @author chi
 */
public class ClientProviderImpl implements Provider<Client, Request> {
    public static final String COOKIE_CLIENT_ID = "_id";
    public static final String COOKIE_LOCALE = "_locale";

    public final Site site;

    public ClientProviderImpl(Site site) {
        this.site = site;
    }

    @Override
    public Client get(Request scope) {
        HttpServerExchange exchange = scope.require(HttpServerExchange.class);
        ClientImpl client = new ClientImpl();
        client.id = id(exchange);
        client.userAgent = userAgent(exchange);
        client.ip = ip(exchange);
        return client;
    }

    private String userAgent(HttpServerExchange exchange) {
        return exchange.getRequestHeaders().getFirst("User-Agent");
    }

    private String id(HttpServerExchange exchange) {
        Cookie cookie = exchange.getRequestCookies().get(COOKIE_CLIENT_ID);
        if (cookie == null) {
            String id = UUID.randomUUID().toString();
            CookieImpl cookieImpl = new CookieImpl(COOKIE_CLIENT_ID, id);
            cookieImpl.setMaxAge(Integer.MAX_VALUE);
            cookieImpl.setPath("/");
            exchange.getResponseCookies().put(COOKIE_CLIENT_ID, cookieImpl);
            return id;
        }
        return cookie.getValue();
    }

    private String ip(HttpServerExchange exchange) {
        String xForwardedFor = exchange.getRequestHeaders().getFirst(Headers.X_FORWARDED_FOR);
        if (Strings.isNullOrEmpty(xForwardedFor))
            return exchange.getSourceAddress().getAddress().getHostAddress();
        int index = xForwardedFor.indexOf(',');
        if (index > 0)
            return xForwardedFor.substring(0, index);
        return xForwardedFor;
    }

    static class ClientImpl implements Client {
        public String id;
        public String ip;
        public String country;
        public String city;
        public String os;
        public String osVersion;
        public String browser;
        public String browserVersion;
        public String userAgent;

        @Override
        public String id() {
            return id;
        }

        @Override
        public String ip() {
            return ip;
        }

        @Override
        public String country() {
            return country;
        }

        @Override
        public String city() {
            return city;
        }

        @Override
        public String os() {
            return os;
        }

        @Override
        public String osVersion() {
            return osVersion;
        }

        @Override
        public String browser() {
            return browser;
        }

        @Override
        public String browserVersion() {
            return browserVersion;
        }

        @Override
        public String userAgent() {
            return userAgent;
        }
    }
}
