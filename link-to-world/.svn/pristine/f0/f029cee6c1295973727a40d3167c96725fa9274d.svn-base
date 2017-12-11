package io.sited.test.impl;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import io.sited.http.HttpMethod;
import io.sited.http.HttpModule;
import io.sited.http.ServerResponse;
import io.sited.test.MockSite;
import io.sited.validator.ValidatorModule;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

/**
 * @author chi
 */
public class MockRequestBuilder {
    private final MockSite site;

    private HttpMethod method;
    private final String path;
    private final String baseURL;
    private final Map<String, String> headers = Maps.newHashMap();
    private final Map<String, String> queries = Maps.newHashMap();
    private final Map<String, String> cookies = Maps.newHashMap();

    private String contentType;
    private Object body;
    private Locale locale = Locale.getDefault();
    private Charset charset = Charsets.UTF_8;

    public MockRequestBuilder(HttpMethod method, String baseURL, String path, MockSite site) {
        this.method = method;
        this.site = site;
        this.path = path;
        this.baseURL = baseURL;
    }

    public <T> MockRequestBuilder body(T body) {
        this.body = body;
        return this;
    }

    public MockRequestBuilder setHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public MockRequestBuilder setCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public MockRequestBuilder setQueryParam(String name, String value) {
        queries.put(name, value);
        return this;
    }

    public MockRequestBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MockRequestBuilder setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public MockRequestBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public ServerResponse execute() throws Exception {
        MockRequestImpl request = new MockRequestImpl(site.module(HttpModule.class).requestConfig, site.module(ValidatorModule.class), charset, locale);
        request.method = method;
        request.baseURL = baseURL;
        request.path = path;
        request.queries = queries;
        request.headers = headers;
        request.cookies = cookies;
        request.contentType = contentType;
        request.body = body;
        return new MockResponseImpl(site.handle(request));
    }
}
