package io.sited.cache.impl.redis;

import io.sited.cache.Cache;
import io.sited.cache.CacheOptions;
import io.sited.cache.RedisOptions;
import io.sited.cache.impl.CacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

/**
 * @author chi
 */
public class RedisCacheProviderImpl implements CacheProvider {
    private final Logger logger = LoggerFactory.getLogger(RedisCacheProviderImpl.class);

    private final JedisPool jedisPool;

    public RedisCacheProviderImpl(RedisOptions redisOptions) {
        jedisPool = new JedisPool(redisOptions.host, redisOptions.port);
    }

    @Override
    public <T> Cache<T> create(Class<T> type, CacheOptions options) {
        logger.info("create redis cache, type={}", type);
        return new RedisCacheImpl<>(type, options, jedisPool);
    }
}
