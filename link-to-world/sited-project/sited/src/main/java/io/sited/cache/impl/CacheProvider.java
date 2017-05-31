package io.sited.cache.impl;

import io.sited.cache.Cache;
import io.sited.cache.CacheOptions;

/**
 * @author chi
 */
public interface CacheProvider {
    <T> Cache<T> create(Class<T> type, CacheOptions options);
}
