package io.sited.cache.impl.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import io.sited.cache.Cache;
import io.sited.cache.CacheOptions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class LocalCacheImpl<T> implements Cache<T> {
    private final com.google.common.cache.Cache<String, T> cache;

    public LocalCacheImpl(CacheOptions options) {
        cache = CacheBuilder.newBuilder()
            .expireAfterAccess(options.expireTime.toMillis(), TimeUnit.MILLISECONDS)
            .maximumSize(options.max)
            .build();
    }

    @Override
    public Optional<T> get(String key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }

    @Override
    public Map<String, T> batchGet(List<String> keys) {
        Map<String, T> results = Maps.newHashMap();
        keys.forEach(key -> {
            T value = cache.getIfPresent(key);
            if (value != null) {
                results.put(key, value);
            }
        });
        return results;
    }

    @Override
    public void put(String key, T value) {
        cache.put(key, value);
    }

    @Override
    public void invalidate(String key) {
        cache.invalidate(key);
    }
}