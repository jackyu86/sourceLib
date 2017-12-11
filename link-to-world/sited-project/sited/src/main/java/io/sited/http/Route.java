package io.sited.http;

import io.sited.http.impl.HandlerRef;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class Route {
    public final Handler handler;
    public final Method method;
    public final String permission;
    public final Map<String, String> pathParams;
    public final List<Interceptor> interceptors;

    public Route(HandlerRef handlerRef, Map<String, String> pathParams, List<Interceptor> interceptors) {
        this.handler = handlerRef.handler;
        this.method = handlerRef.method;
        this.permission = handlerRef.permission;
        this.pathParams = pathParams;
        this.interceptors = interceptors;
    }
}
