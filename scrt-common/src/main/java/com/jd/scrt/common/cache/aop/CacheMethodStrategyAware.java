package com.jd.scrt.common.cache.aop;

/**
 * 缓存策略应有的意识
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheMethodStrategyAware {

    /**
     * 设置CacheMethod策略中心
     *
     * @param cacheMethodStrategyCenter
     */
    public void setCacheMethodStrategyCenter(CacheMethodStrategyCenter cacheMethodStrategyCenter);
}
