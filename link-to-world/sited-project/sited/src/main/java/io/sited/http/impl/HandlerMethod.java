package io.sited.http.impl;

import io.sited.http.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author chi
 */
public class HandlerMethod {
    public Class<?> controllerClass;
    public String path;
    public HttpMethod httpMethod;
    public String permission;
    public Method method;
}
