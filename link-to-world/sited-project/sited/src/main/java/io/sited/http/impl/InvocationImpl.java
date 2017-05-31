package io.sited.http.impl;

import io.sited.http.Handler;
import io.sited.http.Interceptor;
import io.sited.http.Invocation;
import io.sited.http.Request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class InvocationImpl implements Invocation {
    private final Handler handler;
    private final Method method;
    private final List<Interceptor> interceptors;
    private final Request request;
    private int currentStack = 0;

    public InvocationImpl(Handler handler, Method method, List<Interceptor> interceptors, Request request) {
        this.handler = handler;
        this.method = method;
        this.interceptors = interceptors;
        this.request = request;
    }

    @Override
    public <T extends Annotation> Optional<T> annotation(Class<T> annotationClass) {
        return Optional.ofNullable(method.getDeclaredAnnotation(annotationClass));
    }

    @Override
    public Object proceed() throws Exception {
        if (currentStack >= interceptors.size()) {
            return handler.handle(request);
        } else {
            Interceptor interceptor = interceptors.get(currentStack);
            currentStack++;
            return interceptor.intercept(this);
        }
    }

    @Override
    public Request request() {
        return request;
    }
}
