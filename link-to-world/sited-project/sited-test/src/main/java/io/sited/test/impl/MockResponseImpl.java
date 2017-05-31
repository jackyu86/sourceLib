package io.sited.test.impl;

import com.google.common.collect.Maps;
import io.sited.http.Response;
import io.sited.http.ServerResponse;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.util.JSON;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class MockResponseImpl implements ServerResponse {
    private final Map<String, String> headers = Maps.newHashMap();
    private final Map<String, String> cookies = Maps.newHashMap();
    private int statusCode;
    private Object body;

    public MockResponseImpl(Object response) {
        if (response instanceof Response) {
            Response httpResponse = (Response) response;
            headers.putAll(httpResponse.headers());
            cookies.putAll(httpResponse.cookies());
            statusCode = httpResponse.statusCode();
            body = httpResponse.body();
        } else if (response instanceof Throwable) {
            statusCode = statusCode((Throwable) response);
            body = response;
        } else {
            statusCode = response == null ? 204 : 200;
            body = response;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T body(Type bodyClass) {
        return (T) body;
    }

    @Override
    public String contentType() {
        return header("Content-Type").orElse("");
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    private int statusCode(Throwable e) {
        if (e instanceof NotFoundException) {
            return 404;
        } else if (e instanceof BadRequestException) {
            return 400;
        } else if (e instanceof UnauthorizedException || e instanceof UnauthenticatedException) {
            return 403;
        } else {
            return 500;
        }
    }

    @Override
    public Optional<String> header(String name) {
        return Optional.ofNullable(headers.get(name));
    }

    @Override
    public <T> Optional<T> header(String name, Class<T> type) {
        if (headers.containsKey(name)) {
            return Optional.of(JSON.fromJSON(headers.get(name), type));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> cookie(String name) {
        return Optional.ofNullable(cookies.get(name));
    }

    @Override
    public <T> Optional<T> cookie(String name, Class<T> type) {
        if (cookies.containsKey(name)) {
            return Optional.of(JSON.fromJSON(cookies.get(name), type));
        }
        return Optional.empty();
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Map<String, String> cookies() {
        return cookies;
    }
}
