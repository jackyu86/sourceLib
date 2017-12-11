package io.sited.http.impl;

import io.sited.http.Handler;
import io.sited.http.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author chi
 */
public class HandlerRef {
    public Class<?> controllerClass;
    public HttpMethod httpMethod;
    public String path;
    public String permission;
    public Method method;
    public Handler handler;
}
