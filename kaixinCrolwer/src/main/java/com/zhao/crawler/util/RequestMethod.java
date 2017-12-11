package com.zhao.crawler.util;

/**
 * Created by lj on 2017/6/16.
 */
import org.apache.http.client.methods.*;

public enum RequestMethod {
    OPTIONS(new HttpOptions()),
    GET(new HttpGet()),
    HEAD(new HttpHead()),
    POST(new HttpPost()),
    PUT(new HttpPut()),
    DELETE(new HttpDelete()),
    TRACE(new HttpTrace());

    private final HttpRequestBase requestMethod;

    RequestMethod(HttpRequestBase requestMethod) {
        this.requestMethod = requestMethod;
    }

    public HttpRequestBase getRequestMethod() {
        return this.requestMethod;
    }
}
