package io.sited.http.impl.writer;

import io.sited.http.HttpModule;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.ResponseWriter;
import io.sited.http.impl.ResponseImpl;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;

/**
 * @author chi
 */
public class ResponseResponseWriter implements ResponseWriter<Response> {
    private final HttpModule httpModule;

    public ResponseResponseWriter(HttpModule httpModule) {
        this.httpModule = httpModule;
    }

    @Override
    public void write(Request request, Response response) throws IOException {
        ResponseImpl responseImpl = (ResponseImpl) response;
        HttpServerExchange exchange = request.require(HttpServerExchange.class);
        exchange.setStatusCode(response.statusCode());
        if (responseImpl.contentType != null) {
            exchange.getResponseHeaders().add(new HttpString("Content-Type"), responseImpl.contentType);
        }
        responseImpl.cookies.entrySet().forEach(cookie -> exchange.getResponseCookies().put(cookie.getKey(), cookie.getValue()));
        responseImpl.headers.forEach((name, value) -> exchange.getResponseHeaders().put(new HttpString(name), value));
        if (responseImpl.body != null) {
            writeBody(request, responseImpl.body);
        }
    }

    @SuppressWarnings("unchecked")
    private void writeBody(Request request, Object response) throws IOException {
        ResponseWriter responseWriter = httpModule.writer(response.getClass());
        responseWriter.write(request, response);
    }
}
