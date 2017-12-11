package io.sited.http;

import io.sited.Module;
import io.sited.http.impl.HandlerRef;

import java.util.List;

/**
 * @author chi
 */
public interface HttpConfig {
    <T> HttpConfig controller(Class<T> controllerClass, T instance, Module module);

    <T> HttpConfig interceptor(String path, Interceptor interceptor);

    <T> HttpConfig writer(Class<T> type, ResponseWriter<T> responseWriter);

    List<HandlerRef> handlerRefs();

    RequestConfig request();
}
