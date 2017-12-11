package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * CacheMethod默认策略
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class DefaultCacheMethodStrategy extends AbstractCacheMethodStrategy {

    /**
     * 默认执行策略名称（应与CacheMethod.strategy() default 保持一致）
     */
    private static final String STRATEGY_NAME = "";

    @Override
    public String getStrategyName() throws Exception {
        return STRATEGY_NAME;
    }

    @Override
    public Object processCacheMethod(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable {
        //开始计时，执行缓存处理逻辑
        long t0 = System.currentTimeMillis();

        Cache cacheBean = this.getCacheBean(cacheMethod);
        if (cacheBean == null) {
            logger.warn("processCacheMethod: key[" + cacheMethod.key() + "],cacheBean is null,return pjp.proceed()");
            return pjp.proceed();
        }
        String key = this.getCacheKey(cacheMethod, pjp);
        if (key == null || key.length() == 0) {
            logger.warn("processCacheMethod: key[" + cacheMethod.key() + "],cacheKey is empty,return pjp.proceed()");
            return pjp.proceed();
        }
        Object result = this.getCacheValue(cacheBean, key, cacheMethod, pjp);
        if (result == null) {// 先从缓存取，没有再加载
            if (logger.isDebugEnabled()) {
                logger.debug("processCacheMethod: key[" + key + "] miss cacheBean[" + cacheMethod.cacheBean() + "]...");
            }
            result = this.loadCacheValue(cacheBean, key, cacheMethod, pjp);
            this.getCacheMethodMonitor().missCache(key, cacheMethod.cacheBean());
        } else if (this.isNullSign(result)) {//如果是自标记的null标识,则将结果置null
            result = null;
            if (logger.isInfoEnabled()) {
                long t1 = System.currentTimeMillis();
                logger.info("processCacheMethod: key[" + key + "] hit null cacheBean[" + cacheMethod.cacheBean() + "] in " + (t1 - t0) + " ms.");
            }
            this.getCacheMethodMonitor().hitCache(key, cacheMethod.cacheBean());
        } else {
            if (logger.isInfoEnabled()) {
                long t1 = System.currentTimeMillis();
                logger.info("processCacheMethod: key[" + key + "] hit cacheBean[" + cacheMethod.cacheBean() + "] in " + (t1 - t0) + " ms.");
            }
            this.getCacheMethodMonitor().hitCache(key, cacheMethod.cacheBean());
        }
        return result;
    }

}
