package io.sited.cache.impl.redis;


import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.sited.cache.Cache;
import io.sited.cache.CacheOptions;
import io.sited.util.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class RedisCacheImpl<T> implements Cache<T> {
    private final Class<T> type;
    private final JedisPool jedisPool;
    private final int expireTime;

    public RedisCacheImpl(Class<T> type, CacheOptions cacheOptions, JedisPool jedisPool) {
        this.type = type;
        this.expireTime = (int) cacheOptions.expireTime.getSeconds();
        this.jedisPool = jedisPool;
    }

    @Override
    public Optional<T> get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get(redisKey(key));
            if (json == null) {
                return Optional.empty();
            }
            return Optional.of(JSON.fromJSON(json, type));
        }
    }

    @Override
    public Map<String, T> batchGet(List<String> keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> redisKeys = keys.stream().map(this::redisKey).collect(Collectors.toList());
            List<String> values = jedis.mget(redisKeys.toArray(new String[redisKeys.size()]));
            Map<String, T> results = Maps.newHashMap();
            for (int i = 0; i < keys.size(); i++) {
                String json = values.get(i);
                if (!Strings.isNullOrEmpty(json)) {
                    results.put(keys.get(i), JSON.fromJSON(json, type));
                }
            }
            return results;
        }
    }

    @Override
    public void put(String key, T value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(redisKey(key), expireTime, JSON.toJSON(value));
        }
    }

    @Override
    public void invalidate(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(redisKey(key));
        }
    }

    private String redisKey(String key) {
        return type.getSimpleName().toLowerCase() + ":" + key;
    }
}
