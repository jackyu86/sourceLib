package com.jd.scrt.common.cache.aop;


import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import com.jd.scrt.common.cache.MemoryCache;

/**
 * CacheMethodStrategy策略管理中心
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheMethodStrategyCenter {

    /**
     * 注册策略
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param name
     * @param strategy
     * @throws Exception
     */
    public void registerStrategy(String name, CacheMethodStrategy strategy) throws Exception;

    /**
     * 移除策略
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param name
     * @throws Exception
     */
    public void removeStrategy(String name) throws Exception;

    /**
     * 获取本地缓存（单线程同步锁）
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public MemoryCache getLocalCache() throws Exception;

    /**
     * 获取缓存KEY生成器
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public CacheKeyGenerator getCacheKeyGenerator() throws Exception;

    /**
     * 获取CacheMethod监控器
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public CacheMethodMonitor getCacheMethodMonitor() throws Exception;

    /**
     * 获取Cache操作类
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheMethod
     * @return
     * @throws Exception
     */
    public Cache getCacheBean(CacheMethod cacheMethod) throws Exception;

    /**
     * 获取CacheMethodFormater转换类
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheMethod
     * @return
     * @throws Exception
     */
    public CacheMethodFormater getCacheMethodFormater(CacheMethod cacheMethod) throws Exception;

}
