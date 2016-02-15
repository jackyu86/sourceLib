package com.jd.scrt.common.cache.aop;

import java.lang.reflect.Method;

/**
 * 缓存KEY生成器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheKeyGenerator {

    /**
     * 生成缓存KEY
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @param target
     * @param method
     * @param args
     * @return
     * @throws Exception
     */
    public String generate(String key, Object target, Method method, Object[] args) throws Exception;

}
