package io.sited;

import io.sited.http.HttpModule;
import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.sited.http.Route;
import io.sited.http.impl.RequestImpl;
import io.sited.validator.ValidatorConfig;
import io.sited.validator.ValidatorModule;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.CookieImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import static io.sited.http.impl.ClientProviderImpl.COOKIE_LOCALE;

class SiteHandler implements HttpHandler {
    final Site site;
    private final Logger logger = LoggerFactory.getLogger(SiteHandler.class);
    private final HttpModule httpConfig;
    private final ValidatorConfig validatorConfig;

    SiteHandler(Site site) {
        this.site = site;
        httpConfig = site.module(HttpModule.class);
        validatorConfig = site.module(ValidatorModule.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        HttpModule.RequestConfigImpl requestConfig = site.module(HttpModule.class).requestConfig;
        RequestImpl request = new RequestImpl(requestConfig, validatorConfig, site.charset(), locale(exchange));
        request.context().put(HttpServerExchange.class, exchange);
        Object response = null;
        try {
            response = site.handle(request);
            if (response == null) {
                if (exchange.getStatusCode() == 200) {
                    exchange.setStatusCode(204);
                }
            } else {
                Route route = (Route) request.context().get(Route.class);
                Class returnType = route == null ? response.getClass() : route.method.getReturnType();
                write(request, returnType, response);
            }
        } catch (Exception e) {
            response = e;
            write(request, e.getClass(), e);
        } finally {
            if (response instanceof Throwable || isTraceEnabled(request)) {
                logger.error("{} {} {}\n{}", request.method(), exchange.getStatusCode(), request.path(), info(request, response));
            } else {
                logger.debug("{} {} {}", request.method(), exchange.getStatusCode(), request.path());
            }
        }
    }

    private Locale locale(HttpServerExchange exchange) {
        Cookie cookie = exchange.getRequestCookies().get(COOKIE_LOCALE);
        if (cookie == null) {
            Locale locale = site.locale();
            CookieImpl cookieImpl = new CookieImpl(COOKIE_LOCALE, locale.toLanguageTag());
            cookieImpl.setPath("/");
            cookieImpl.setMaxAge(Integer.MAX_VALUE);
            exchange.getResponseCookies().put(COOKIE_LOCALE, cookieImpl);
            return locale;
        }
        return Locale.forLanguageTag(cookie.getValue());
    }

    private boolean isTraceEnabled(Request request) {
        return request.header("_trace").isPresent();
    }

    @SuppressWarnings("PMD")
    private String info(Request request, Object response) {
        StringBuilder b = new StringBuilder();

        b.append("Headers: \n");
        request.headers().forEach((key, value) -> b.append('\t').append(key).append('=').append(value).append('\n'));
        b.append("Cookies: \n");
        request.cookies().forEach((key, value) -> b.append('\t').append(key).append('=').append(value).append('\n'));

        if (response instanceof Throwable) {
            StringWriter writer = new StringWriter();
            ((Throwable) response).printStackTrace(new PrintWriter(writer));
            b.append("Trace: \n\t");
            b.append(writer.toString());
        } else {
            b.append("Body: \n\t");
            b.append(response);
        }
        return b.toString();
    }

    @SuppressWarnings("unchecked")
    public void write(Request request, Class<?> type, Object response) throws Exception {
        ResponseWriter responseWriter = httpConfig.writer(type);
        responseWriter.write(request, response);
    }
}