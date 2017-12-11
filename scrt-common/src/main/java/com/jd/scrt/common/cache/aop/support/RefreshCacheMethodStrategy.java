package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * CacheMethod refresh策略
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class RefreshCacheMethodStrategy extends AbstractCacheMethodStrategy {

    @Override
    public String getStrategyName() throws Exception {
        return "refresh";
    }

    @Override
    public Object processCacheMethod(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable {
        //开始计时，执行缓存加载逻辑
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
        Object result = null;
        try {
            //重新加载数据至缓存
            result = this.loadCacheValue(cacheBean, key, cacheMethod, pjp);
            if (result == null) {
                if (cacheMethod.nullTimeout() > 0) {//开启null缓存，loadCacheValue已经缓存null，无需清理。
                    if (logger.isDebugEnabled()) {
                        logger.debug("processCacheMethod: key[" + key + "],nullTimeout[" + cacheMethod.nullTimeout() + "] don't remove! ");
                    }
                } else {//没有加载到数据，需要清理缓存。
                    Object cacheValue = this.removeCacheValue(cacheBean, key, cacheMethod, pjp);
                    if (logger.isInfoEnabled()) {
                        long t1 = System.currentTimeMillis();
                        logger.info("processCacheMethod: key[" + key + "],cacheBean[" + cacheMethod.cacheBean() + "] loadCacheValue null, remove isNull[" + (cacheValue == null) + "] in " + (t1 - t0) + " ms.");
                    }
                }
            } else {
                if (logger.isInfoEnabled()) {
                    long t1 = System.currentTimeMillis();
                    logger.info("processCacheMethod: key[" + key + "],cacheBean[" + cacheMethod.cacheBean() + "] refresh in " + (t1 - t0) + " ms.");
                }
            }
        } catch (Throwable t) {
            logger.error("processCacheMethod: key[" + key + "],cacheBean[" + cacheMethod.cacheBean() + "] refresh error", t);
            //如果出现异常，则删除缓存
            Object cacheValue = this.removeCacheValue(cacheBean, key, cacheMethod, pjp);
            if (logger.isInfoEnabled()) {
                long t1 = System.currentTimeMillis();
                logger.info("processCacheMethod: key[" + key + "],cacheBean[" + cacheMethod.cacheBean() + "] refresh error, remove isNull[" + (cacheValue == null) + "] in " + (t1 - t0) + " ms.");
            }
        }
        return result;
    }

}
