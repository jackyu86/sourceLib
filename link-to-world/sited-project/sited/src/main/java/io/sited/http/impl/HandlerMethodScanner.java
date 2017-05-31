package io.sited.http.impl;

import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.HttpMethod;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author chi
 */
public class HandlerMethodScanner {
    private final Class<?> controllerClass;

    public HandlerMethodScanner(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public List<HandlerMethod> scan() {
        List<HandlerMethod> handlerMethods = Lists.newArrayList();
        Method[] methods = controllerClass.getDeclaredMethods();

        Arrays.stream(methods).forEach(method -> {
            if (method.isAnnotationPresent(GET.class)) {
                handlerMethods.add(parse(method.getDeclaredAnnotation(GET.class), method));
            } else if (method.isAnnotationPresent(PUT.class)) {
                handlerMethods.add(parse(method.getDeclaredAnnotation(PUT.class), method));
            } else if (method.isAnnotationPresent(POST.class)) {
                handlerMethods.add(parse(method.getDeclaredAnnotation(POST.class), method));
            } else if (method.isAnnotationPresent(DELETE.class)) {
                handlerMethods.add(parse(method.getDeclaredAnnotation(DELETE.class), method));
            }
        });
        return handlerMethods;
    }

    private HandlerMethod parse(GET get, Method method) {
        if (!method.isAnnotationPresent(Path.class)) {
            throw new StandardException("missing @Path, class={}, method={}", method.getDeclaringClass().getCanonicalName(), method.getName());
        }
        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.controllerClass = controllerClass;
        handlerMethod.path = method.getDeclaredAnnotation(Path.class).value();
        handlerMethod.permission = get.value();
        handlerMethod.httpMethod = HttpMethod.GET;
        handlerMethod.method = method;
        return handlerMethod;
    }

    private HandlerMethod parse(PUT put, Method method) {
        if (!method.isAnnotationPresent(Path.class)) {
            throw new StandardException("missing @Path, class={}, method={}", method.getDeclaringClass().getCanonicalName(), method.getName());
        }
        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.controllerClass = controllerClass;
        handlerMethod.path = method.getDeclaredAnnotation(Path.class).value();
        handlerMethod.permission = put.value();
        handlerMethod.httpMethod = HttpMethod.PUT;
        handlerMethod.method = method;
        return handlerMethod;
    }

    private HandlerMethod parse(POST post, Method method) {
        if (!method.isAnnotationPresent(Path.class)) {
            throw new StandardException("missing @Path, class={}, method={}", method.getDeclaringClass().getCanonicalName(), method.getName());
        }
        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.controllerClass = controllerClass;
        handlerMethod.path = method.getDeclaredAnnotation(Path.class).value();
        handlerMethod.permission = post.value();
        handlerMethod.httpMethod = HttpMethod.POST;
        handlerMethod.method = method;
        return handlerMethod;
    }

    private HandlerMethod parse(DELETE delete, Method method) {
        if (!method.isAnnotationPresent(Path.class)) {
            throw new StandardException("missing @Path, class={}, method={}", method.getDeclaringClass().getCanonicalName(), method.getName());
        }
        HandlerMethod handlerMethod = new HandlerMethod();
        handlerMethod.controllerClass = controllerClass;
        handlerMethod.path = method.getDeclaredAnnotation(Path.class).value();
        handlerMethod.permission = delete.value();
        handlerMethod.httpMethod = HttpMethod.DELETE;
        handlerMethod.method = method;
        return handlerMethod;
    }
}
