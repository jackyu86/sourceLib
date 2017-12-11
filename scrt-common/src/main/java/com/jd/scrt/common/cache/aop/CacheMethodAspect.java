package com.jd.scrt.common.cache.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jd.scrt.common.annotation.CacheMethod;
import com.jd.scrt.common.cache.Cache;
import com.jd.scrt.common.cache.MemoryCache;
import com.jd.scrt.common.cache.aop.support.DefaultCacheKeyGenerator;
import com.jd.scrt.common.cache.aop.support.DefaultCacheMethodMonitor;
import com.jd.scrt.common.cache.aop.support.DefaultCacheMethodStrategy;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * CacheMethod 缓存注解切面
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
@Aspect
public class CacheMethodAspect implements CacheMethodStrategyCenter {

    private static final Logger logger = Logger.getLogger(CacheMethodAspect.class);

    private MemoryCache localCache = new MemoryCache();
    private CacheKeyGenerator cacheKeyGenerator = new DefaultCacheKeyGenerator();
    private CacheMethodMonitor cacheMethodMonitor = new DefaultCacheMethodMonitor();
    private Map<String, Cache> cacheBeans = new HashMap<String, Cache>();
    private Map<String, CacheMethodFormater> formaters = new HashMap<String, CacheMethodFormater>();
    private Map<String, CacheMethodStrategy> strategies = new HashMap<String, CacheMethodStrategy>();

    public CacheMethodAspect() {
        this.registerDefaultCacheMethodStrategy();
    }

    /**
     * 监听标注CacheMethod的方法切面环绕通知
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param pjp
     * @param cacheMethod
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *(..)) && @annotation(cacheMethod)")
    public Object onAround(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable {
        return this.processCacheMethod(pjp, cacheMethod);
    }

    /**
     * 处理带有CacheMethod注解的方法
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param pjp
     * @param cacheMethod
     * @return
     * @throws Throwable
     */
    protected Object processCacheMethod(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("processCacheMethod-begin: " + this.toString(cacheMethod));
        }
        if (cacheMethod.disabled()) {
            logger.info("processCacheMethod: key[" + cacheMethod.key() + "],disabled[" + cacheMethod.disabled() + "],return pjp.proceed()");
            return pjp.proceed();
        }
        CacheMethodStrategy cacheMethodStrategy = this.getCacheMethodStrategy(cacheMethod);
        if (cacheMethodStrategy == null) {
            logger.warn("processCacheMethod: key[" + cacheMethod.key() + "],cacheMethodStrategy is null,return pjp.proceed()");
            return pjp.proceed();
        }
        return cacheMethodStrategy.processCacheMethod(pjp, cacheMethod);
    }

    /**
     * 初始化
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void init() throws Exception {
        logger.info("CacheMethodAspect init...");
    }

    /**
     * 销毁
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void destroy() throws Exception {
        logger.info("CacheMethodAspect destroy...");
    }

    @Override
    public void registerStrategy(String name, CacheMethodStrategy strategy) throws Exception {
        logger.info("registerStrategy: name[" + name + "]");
        this.getStrategies().put(name, strategy);
    }

    @Override
    public void removeStrategy(String name) throws Exception {
        logger.info("removeStrategy: name[" + name + "]");
        this.getStrategies().remove(name);
    }

    @Override
    public MemoryCache getLocalCache() throws Exception {
        return this.localCache;
    }

    @Override
    public Cache getCacheBean(CacheMethod cacheMethod) throws Exception {
        return this.getCacheBeans().get(cacheMethod.cacheBean());
    }

    @Override
    public CacheMethodFormater getCacheMethodFormater(CacheMethod cacheMethod) throws Exception {
        return this.getFormaters().get(cacheMethod.format());
    }

    /**
     * 获取CacheMethod执行策略
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheMethod
     * @return
     * @throws Exception
     */
    protected CacheMethodStrategy getCacheMethodStrategy(CacheMethod cacheMethod) throws Exception {
        return this.getStrategies().get(cacheMethod.strategy());
    }

    /**
     * 注册默认执行策略
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    protected void registerDefaultCacheMethodStrategy() {
        logger.info("registerDefaultCacheMethodStrategy begin...");
        try {
            DefaultCacheMethodStrategy defaultStrategy = new DefaultCacheMethodStrategy();
            defaultStrategy.setCacheMethodStrategyCenter(this);
            defaultStrategy.init();
        } catch (Exception e) {
            logger.error("registerDefaultCacheMethodStrategy-error:");
        }
    }

    /**
     * 将CacheMethod转换为String
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cacheMethod
     * @return
     * @throws Exception
     */
    protected String toString(CacheMethod cacheMethod) throws Exception {
        if (cacheMethod == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("key:").append(cacheMethod.key()).append(",");
        sb.append("cacheBean:").append(cacheMethod.cacheBean()).append(",");
        sb.append("timeout:").append(cacheMethod.timeout()).append(",");
        sb.append("nullTimeout:").append(cacheMethod.nullTimeout()).append(",");
        sb.append("disabled:").append(cacheMethod.disabled()).append(",");
        sb.append("readonly:").append(cacheMethod.readonly()).append(",");
        sb.append("lock:").append(cacheMethod.lock()).append(",");
        sb.append("format:").append(cacheMethod.format()).append(",");
        sb.append("strategy:").append(cacheMethod.strategy()).append(",");
        sb.append("strategyArgs:").append(cacheMethod.strategyArgs());
        sb.append("}");
        return sb.toString();
    }

    // ---------- getter and setter ----------//

    public CacheKeyGenerator getCacheKeyGenerator() {
        return cacheKeyGenerator;
    }

    public void setCacheKeyGenerator(CacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    public CacheMethodMonitor getCacheMethodMonitor() {
        return cacheMethodMonitor;
    }

    public void setCacheMethodMonitor(CacheMethodMonitor cacheMethodMonitor) {
        this.cacheMethodMonitor = cacheMethodMonitor;
    }

    protected Map<String, Cache> getCacheBeans() {
        return cacheBeans;
    }

    public void setCacheBeans(Map<String, Cache> cacheBeans) {
        if (cacheBeans != null && cacheBeans.size() > 0) {
            this.cacheBeans.putAll(cacheBeans);
        }
    }

    protected Map<String, CacheMethodFormater> getFormaters() {
        return formaters;
    }

    public void setFormaters(Map<String, CacheMethodFormater> formaters) {
        if (formaters != null && formaters.size() > 0) {
            this.formaters.putAll(formaters);
        }
    }

    protected Map<String, CacheMethodStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<CacheMethodStrategy> strategies) {
        if (strategies != null && strategies.size() > 0) {
            for (CacheMethodStrategy strategy : strategies) {
                if (strategy == null) {
                    continue;
                }
                try {
                    strategy.setCacheMethodStrategyCenter(this);
                    strategy.init();
                } catch (Exception e) {
                    logger.error("setStrategies-error", e);
                }
            }
        }
    }

}
