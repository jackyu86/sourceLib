package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * CacheMethod remove策略
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class RemoveCacheMethodStrategy extends AbstractCacheMethodStrategy {

    @Override
    public String getStrategyName() throws Exception {
        return "remove";
    }

    @Override
    public Object processCacheMethod(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable {
        //业务逻辑首先执行，没有异常则执行缓存移除逻辑
        Object result = pjp.proceed();

        //开始计时，执行缓存移除逻辑
        long t0 = System.currentTimeMillis();
        Cache cacheBean = this.getCacheBean(cacheMethod);
        if (cacheBean == null) {
            logger.warn("processCacheMethod: key[" + cacheMethod.key() + "],cacheBean is null,return pjp.proceed()");
            return result;
        }
        String key = this.getCacheKey(cacheMethod, pjp);
        if (key == null || key.length() < 1) {
            logger.warn("processCacheMethod: key[" + cacheMethod.key() + "],cacheKey is empty,return pjp.proceed()");
            return result;
        }
        Object cacheValue = this.removeCacheValue(cacheBean, key, cacheMethod, pjp);
        if (logger.isInfoEnabled()) {
            long t1 = System.currentTimeMillis();
            logger.info("processCacheMethod: key[" + key + "],cacheBean[" + cacheMethod.cacheBean() + "] remove isNull[" + (cacheValue == null) + "] in " + (t1 - t0) + " ms.");
        }
        return result;
    }

}
