package io.sited.cache.impl;

import io.sited.cache.CacheOptions;
import io.sited.cache.impl.redis.RedisCacheImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
@Ignore
public class RedisCacheImplTest {
    RedisCacheImpl<Map> cache;

    @Before
    public void setup() {
        cache = new RedisCacheImpl<>(Map.class, new CacheOptions(), new JedisPool("localhost", 6379));
    }

    @Test
    public void get() {
        cache.put("object", Collections.singletonMap("name", "value"));
        Optional<Map> object = cache.get("object");
        Assert.assertTrue(object.isPresent());
    }
}