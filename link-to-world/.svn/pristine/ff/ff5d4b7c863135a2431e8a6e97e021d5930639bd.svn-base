package io.sited.http.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import io.sited.http.Response;
import io.sited.util.JSON;
import io.undertow.server.handlers.CookieImpl;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author chi
 */
public class ResponseImpl implements Response {
    public final Object body;
    public final Map<String, String> headers = Maps.newHashMap();
    public final Map<String, CookieImpl> cookies = Maps.newHashMap();

    public String contentType;
    public Charset charset;
    public int statusCode = 200;
    public long contentLength;

    public ResponseImpl(Object body) {
        this.body = body;
    }

    @Override
    public Response setHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public Response setCookie(String name, String value) {
        return setCookie(name, value, null, null, null, null);
    }

    @Override
    public Response setCookie(String name, String value, Integer maxAge) {
        return setCookie(name, value, maxAge, null, null, null);
    }

    @Override
    public Response setCookie(String name, String value, Integer maxAge, String domain) {
        return setCookie(name, value, maxAge, domain, null, null);
    }

    @Override
    public Response setCookie(String name, String value, Integer maxAge, String domain, Boolean httpOnly, Boolean secure) {
        CookieImpl cookie = new CookieImpl(name, JSON.toJSON(value));
        cookie.setPath("/");
        cookie.setDomain(domain);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        if (value == null) {
            cookie.setMaxAge(0);
        }
        if (httpOnly != null) {
            cookie.setHttpOnly(httpOnly);
        }
        if (secure != null) {
            cookie.setSecure(secure);
        }
        cookies.put(name, cookie);
        return this;
    }

    @Override
    public final Response setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public Response setContentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    @Override
    public Response setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public Response setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T body() {
        return (T) body;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Map<String, String> cookies() {
        Map<String, String> cookies = Maps.newHashMap();
        this.cookies.forEach((name, cookie) -> cookies.put(name, cookie.getValue()));
        return cookies;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("body", body)
            .add("headers", headers)
            .add("cookies", cookies)
            .add("contentType", contentType)
            .add("size", contentLength)
            .add("charset", charset)
            .add("statusCode", statusCode)
            .toString();
    }
}