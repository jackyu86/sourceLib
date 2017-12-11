package io.sited.http;

/**
 * @author chi
 */
@FunctionalInterface
public interface Interceptor {
    Object intercept(Invocation invocation) throws Exception;
}
