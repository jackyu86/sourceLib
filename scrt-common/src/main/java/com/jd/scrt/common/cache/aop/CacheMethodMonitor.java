package com.jd.scrt.common.cache.aop;

/**
 * CacheMethod监控器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheMethodMonitor {

    /**
     * 没命中缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void missCache(String cacheKey, String cacheBean);

    /**
     * 命中缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void hitCache(String cacheKey, String cacheBean);

    /**
     * 加载缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void loadCache(String cacheKey, String cacheBean);

    /**
     * 设置缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void putCache(String cacheKey, String cacheBean);

    /**
     * 获取缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void getCache(String cacheKey, String cacheBean);

    /**
     * 删除缓存
     *
     * @param cacheKey
     * @param cacheBean
     */
    public void removeCache(String cacheKey, String cacheBean);

}
