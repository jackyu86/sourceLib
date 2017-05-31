package io.sited.cache.impl.guava;

import io.sited.cache.Cache;
import io.sited.cache.CacheOptions;
import io.sited.cache.impl.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chi
 */
public class LocalCacheProviderImpl implements CacheProvider {
    private final Logger logger = LoggerFactory.getLogger(LocalCacheProviderImpl.class);

    @Override
    public <T> Cache<T> create(Class<T> type, CacheOptions options) {
        logger.info("create local cache, type={}", type);
        return new LocalCacheImpl<>(options);
    }

}
