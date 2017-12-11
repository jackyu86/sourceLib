package com.jd.scrt.common.cache.aop.support;

import java.lang.reflect.Method;

import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import com.jd.scrt.common.cache.CacheLoader;
import com.jd.scrt.common.cache.MemoryCache;
import com.jd.scrt.common.cache.aop.*;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * CacheMethod策略抽象类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public abstract class AbstractCacheMethodStrategy implements CacheMethodStrategy {

    protected final Logger logger = Logger.getLogger(this.getClass());

    public static final String NULL_SIGN = "null";

    private CacheMethodStrategyCenter cacheMethodStrategyCenter;

    @Override
    public void init() throws Exception {
        this.registerStrategy();
    }

    @Override
    public String getStrategyName() throws Exception {
        throw new UnsupportedOperationException("CacheMethodStrategy getStrategyName is not supported, Override this method please!");
    }

    @Override
    public void destroy() throws Exception {
        this.removeStrategy();
    }

    /**
     * 注册缓存策略
     *
     * @throws Exception
     */
    protected void registerStrategy() throws Exception {
        if (this.cacheMethodStrategyCenter == null) {
            logger.warn("registerStrategy: cacheMethodStrategyCenter is null,registerStrategy failed!");
            return;
        }
        this.cacheMethodStrategyCenter.registerStrategy(this.getStrategyName(), this);
    }

    /**
     * 移除缓存策略
     *
     * @throws Exception
     */
    protected void removeStrategy() throws Exception {
        if (this.cacheMethodStrategyCenter == null) {
            logger.warn("removeStrategy: cacheMethodStrategyCenter is null,removeStrategy failed!");
            return;
        }
        this.cacheMethodStrategyCenter.removeStrategy(this.getStrategyName());
    }

    protected MemoryCache getLocalCache() throws Exception {
        return this.cacheMethodStrategyCenter.getLocalCache();
    }

    protected CacheKeyGenerator getCacheKeyGenerator() throws Exception {
        return this.cacheMethodStrategyCenter.getCacheKeyGenerator();
    }

    protected CacheMethodMonitor getCacheMethodMonitor() throws Exception {
        return this.cacheMethodStrategyCenter.getCacheMethodMonitor();
    }

    protected Cache getCacheBean(CacheMethod cacheMethod) throws Exception {
        return this.cacheMethodStrategyCenter.getCacheBean(cacheMethod);
    }

    protected CacheMethodFormater getCacheMethodFormater(CacheMethod cacheMethod) throws Exception {
        return this.cacheMethodStrategyCenter.getCacheMethodFormater(cacheMethod);
    }

    /**
     * 获取缓存KEY
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheMethod
     * @param args
     * @return
     * @throws Exception
     */
    protected String getCacheKey(CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        String key = cacheMethod.key();
        try {
            CacheKeyGenerator generator = this.getCacheKeyGenerator();
            Object target = pjp.getTarget();
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            Object[] args = pjp.getArgs();
            return generator.generate(key, target, method, args);
        } catch (Exception e) {
            logger.error("getCacheKey-error: key[" + key + "]", e);
            return null;
        }
    }

    /**
     * 加载业务数据(禁止捕获异常)
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @param cacheMethod
     * @param pjp
     * @return
     * @throws Exception
     */
    protected Object loadCacheValue(Cache cacheBean, String cacheKey, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Throwable {
        if (cacheMethod.lock()) {
            return
                    this.loadCacheValueInLock(cacheBean, cacheKey, cacheMethod, pjp);
        }
        return this.loadCacheValueNoLock(cacheBean, cacheKey, cacheMethod, pjp);
    }

    /**
     * 加载业务数据(同步锁)(禁止捕获异常)
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @param cacheMethod
     * @param pjp
     * @return
     * @throws Throwable
     */
    protected Object loadCacheValueInLock(final Cache cacheBean, final String cacheKey, final CacheMethod cacheMethod, final ProceedingJoinPoint pjp) throws Throwable {
        MemoryCache localCache = this.getLocalCache();
        Object value = null;
        try {
            //cacheLoader.load方法锁定单线程加载业务数据
            CacheLoader cacheLoader = new CacheLoader() {
                @Override
                public Object load(String key) throws Throwable {
                    logger.debug("loadCacheValueInLock: key[" + key + "] load begin...");
                    //加锁后再一次验证缓存中是否有数据
                    Object result = getCacheValue(cacheBean, cacheKey, cacheMethod, pjp);
                    if (result == null) {//没有命中缓存，则需要加载业务数据
                        if (logger.isDebugEnabled()) {
                            logger.debug("loadCacheValueInLock: key[" + key + "] miss cacheBean[" + cacheMethod.cacheBean() + "]...");
                        }
                        result = loadCacheValueNoLock(cacheBean, cacheKey, cacheMethod, pjp);
                        getCacheMethodMonitor().missCache(cacheKey, cacheMethod.cacheBean());
                    } else if (isNullSign(result)) {//如果是自标记的null标识,则将结果置null
                        result = null;
                        logger.info("loadCacheValueInLock: key[" + key + "] hit null cacheBean[" + cacheMethod.cacheBean() + "]");
                        getCacheMethodMonitor().hitCache(cacheKey, cacheMethod.cacheBean());
                    } else {//如果命中缓存，直接返回
                        logger.info("loadCacheValueInLock: key[" + key + "] hit cacheBean[" + cacheMethod.cacheBean() + "]");
                        getCacheMethodMonitor().hitCache(cacheKey, cacheMethod.cacheBean());
                    }
                    return result;
                }
            };
            value = localCache.load(cacheKey, cacheLoader, 60000);//在同步锁中加载业务数据
        } finally {
            localCache.remove(cacheKey);//最后一定要记得清理本地缓存。
        }
        return value;
    }

    /**
     * 加载业务数据(没有锁)(禁止捕获异常)
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @param cacheMethod
     * @param pjp
     * @return
     * @throws Throwable
     */
    protected Object loadCacheValueNoLock(Cache cacheBean, String cacheKey, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("loadCacheValueNoLock: key[" + cacheKey + "] pjp.proceed() begin...");
        }
        this.getCacheMethodMonitor().loadCache(cacheKey, cacheMethod.cacheBean());
        Object result = pjp.proceed();
        if (cacheMethod.readonly()) {
            if (logger.isDebugEnabled()) {
                logger.debug("loadCacheValueNoLock: key[" + cacheKey + "] is readonly,don't put cache!");
            }
        } else {
            if (result == null) {// 如果没数据，日志记录
                if (cacheMethod.nullTimeout() > 0) {
                    this.putCacheNullValue(cacheBean, cacheKey, cacheMethod, pjp);
                    logger.info("loadCacheValueNoLock: key[" + cacheKey + "],pjp.proceed() return null,cache null! ");
                } else {
                    logger.debug("loadCacheValueNoLock: key[" + cacheKey + "],pjp.proceed() return null,don't cache! ");
                }
            } else {// 如果有数据，则进行缓存
                Object ret = this.putCacheValue(cacheBean, cacheKey, result, cacheMethod, pjp);
                if (ret == null) {
                    logger.warn("loadCacheValueNoLock: key[" + cacheKey + "],cacheBean[" + cacheMethod.cacheBean() + "],timeout[" + cacheMethod.timeout() + "],putCacheValue failed! ");
                } else {
                    logger.info("loadCacheValueNoLock: key[" + cacheKey + "],cacheBean[" + cacheMethod.cacheBean() + "],timeout[" + cacheMethod.timeout() + "],putCacheValue succeed! ");
                }
            }
        }
        return result;
    }

    /**
     * 从缓存获取数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @return
     * @throws Exception
     */
    protected Object getCacheValue(Cache cacheBean, String cacheKey, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (cacheBean == null || cacheKey == null) {
            return null;
        }
        try {
            this.getCacheMethodMonitor().getCache(cacheKey, cacheMethod.cacheBean());
            Object value = cacheBean.get(cacheKey);
            return this.parseCacheValue(value, cacheMethod, pjp);
        } catch (Exception e) {
            logger.error("getCacheValue-error: key[" + cacheKey + "]", e);
            return null;
        }
    }

    /**
     * 解析缓存数据
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @param format
     * @param pjp
     * @return
     * @throws Exception
     */
    protected Object parseCacheValue(Object value, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (value == null) {
            return null;
        }
        CacheMethodFormater formater = this.getCacheMethodFormater(cacheMethod);
        if (formater != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("parseCacheValue: key[" + cacheMethod.key() + "],format[" + cacheMethod.format() + "]...");
            }
            Object target = pjp.getTarget();
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            return formater.parse(value, target, method);
        }
        return value;
    }

    /**
     * 将数据放入缓存(null)
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @param timeout
     * @return
     * @throws Exception
     */
    protected Object putCacheNullValue(Cache cacheBean, String cacheKey, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (cacheBean == null || cacheKey == null) {
            return null;
        }
        try {
            this.getCacheMethodMonitor().putCache(cacheKey, cacheMethod.cacheBean());
            Object formated_value = this.formatCacheValue(NULL_SIGN, cacheMethod, pjp);
            return cacheBean.put(cacheKey, formated_value, cacheMethod.nullTimeout());
        } catch (Exception e) {
            logger.error("putCacheNullValue-error: key[" + cacheKey + "]", e);
            return null;
        }
    }

    /**
     * 将数据放入缓存
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @param value
     * @param timeout
     * @return
     * @throws Exception
     */
    protected Object putCacheValue(Cache cacheBean, String cacheKey, Object value, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (cacheBean == null || cacheKey == null) {
            return null;
        }
        try {
            this.getCacheMethodMonitor().putCache(cacheKey, cacheMethod.cacheBean());
            Object formated_value = this.formatCacheValue(value, cacheMethod, pjp);
            return cacheBean.put(cacheKey, formated_value, cacheMethod.timeout());
        } catch (Exception e) {
            logger.error("putCacheValue-error: key[" + cacheKey + "]", e);
            return null;
        }
    }

    /**
     * 格式化缓存数据
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @param format
     * @param pjp
     * @return
     * @throws Exception
     */
    protected Object formatCacheValue(Object value, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (value == null) {
            return null;
        }
        CacheMethodFormater formater = this.getCacheMethodFormater(cacheMethod);
        if (formater != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("formatCacheValue: key[" + cacheMethod.key() + "],format[" + cacheMethod.format() + "]...");
            }
            Object target = pjp.getTarget();
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            return formater.format(value, target, method);
        }
        return value;
    }

    /**
     * 移除缓存数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheBean
     * @param cacheKey
     * @return
     * @throws Exception
     */
    protected Object removeCacheValue(Cache cacheBean, String cacheKey, CacheMethod cacheMethod, ProceedingJoinPoint pjp) throws Exception {
        if (cacheBean == null || cacheKey == null) {
            return null;
        }
        try {
            this.getCacheMethodMonitor().removeCache(cacheKey, cacheMethod.cacheBean());
            return cacheBean.remove(cacheKey);
        } catch (Exception e) {
            logger.error("removeCacheValue-error: key[" + cacheKey + "]", e);
            return null;
        }
    }

    /**
     * 校正结果值
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param result
     * @return
     * @throws Exception
     */
    protected Object reviseResult(Object result) throws Exception {
        if (result == null || this.isNullSign(result)) {
            return null;
        }
        return result;
    }

    /**
     * 判断是否是自标识null值
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @return
     * @throws Exception
     */
    protected boolean isNullSign(Object value) throws Exception {
        if (value == null) {
            return false;
        }
        return (value instanceof String && NULL_SIGN.equals(value));
    }

    // ---------- getter and setter ----------//
    public CacheMethodStrategyCenter getCacheMethodStrategyCenter() {
        return cacheMethodStrategyCenter;
    }

    public void setCacheMethodStrategyCenter(CacheMethodStrategyCenter cacheMethodStrategyCenter) {
        this.cacheMethodStrategyCenter = cacheMethodStrategyCenter;
    }

}
