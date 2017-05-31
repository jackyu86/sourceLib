package io.sited.http.impl;

import io.sited.Provider;
import io.sited.cache.Cache;
import io.sited.http.Request;
import io.sited.http.Session;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class SessionProvider implements Provider<Session, Request> {
    public static final String COOKIE_SESSION_ID = "_session_id";
    @Inject
    Cache<SessionImpl.SessionData> cache;

    @Override
    public Session get(Request request) {
        String sessionId = sessionId(request);
        Optional<SessionImpl.SessionData> data = cache.get(sessionId);
        return new SessionImpl(sessionId, data.isPresent() ? data.get() : new SessionImpl.SessionData());
    }

    private String sessionId(Request request) {
        Optional<String> cookie = request.cookie(COOKIE_SESSION_ID);
        if (!cookie.isPresent()) {
            String sessionId = UUID.randomUUID().toString();
            CookieImpl cookieImpl = new CookieImpl(COOKIE_SESSION_ID, sessionId);
            cookieImpl.setMaxAge(-1);
            cookieImpl.setPath("/");

            HttpServerExchange exchange = request.require(HttpServerExchange.class, null);
            if (exchange != null) {
                exchange.getResponseCookies().put(COOKIE_SESSION_ID, cookieImpl);
            }
            return sessionId;
        }
        return cookie.get();
    }
}
