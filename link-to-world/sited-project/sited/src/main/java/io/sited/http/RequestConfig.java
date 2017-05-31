package io.sited.http;

import io.sited.Provider;

/**
 * @author chi
 */
public interface RequestConfig {
    <T> RequestConfig bind(Class<T> type, Provider<T, Request> provider);
}
