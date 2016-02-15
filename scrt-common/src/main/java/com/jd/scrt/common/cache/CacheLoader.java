package com.jd.scrt.common.cache;

/**
 * 缓存数据加载器
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheLoader {

    /**
     * 加载缓存数据
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Object load(String key) throws Throwable;

}
