package io.sited.cache;

import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.StandardException;
import io.sited.cache.impl.CacheProvider;
import io.sited.cache.impl.guava.LocalCacheProviderImpl;
import io.sited.cache.impl.redis.RedisCacheProviderImpl;
import io.sited.util.Types;

import java.util.Map;

/**
 * @author chi
 */
@ModuleInfo(name = "cache")
public class CacheModule extends Module {
    private final Map<Class<?>, Cache<?>> caches = Maps.newHashMap();
    private CacheProvider provider;

    @Override
    protected void configure() throws Exception {
        CacheModuleOptions options = options(CacheModuleOptions.class);
        if (options.redis != null) {
            provider = new RedisCacheProviderImpl(options.redis);
        } else {
            provider = new LocalCacheProviderImpl();
        }
        bind(CacheConfig.class, cacheConfig());
        export(CacheConfig.class);
    }

    private Provider<CacheConfig, Module> cacheConfig() {
        return module -> new CacheConfig() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> Cache<T> get(Class<T> type) {
                if (!caches.containsKey(type)) {
                    throw new StandardException("missing cache, type={}", type);
                }
                return (Cache<T>) caches.get(type);
            }

            @Override
            public <T> Cache<T> create(Class<T> type, io.sited.cache.CacheOptions cacheOptions) {
                Cache<T> cache = provider.create(type, cacheOptions);
                caches.put(type, cache);
                module.bind(Types.generic(Cache.class, type), cache);
                return cache;
            }
        };
    }
}
