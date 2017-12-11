package io.sited.cache.impl;

import io.sited.cache.CacheOptions;
import io.sited.cache.impl.guava.LocalCacheImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author chi
 */
public class GuavaCacheImplTest {
    private final LocalCacheImpl<Object> cache = new LocalCacheImpl<>(new CacheOptions());

    @Test
    public void get() {
        cache.put("object", new Object());
        Optional<Object> object = cache.get("object");
        Assert.assertTrue(object.isPresent());
    }

    @Test
    public void invalidate() {
        cache.put("object", new Object());
        cache.invalidate("object");
        Optional<Object> object = cache.get("object");
        Assert.assertFalse(object.isPresent());
    }
}