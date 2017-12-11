package com.jd.scrt.common.cache.aop;

import com.jd.scrt.common.annotation.CacheMethod;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * CacheMethod执行策略
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheMethodStrategy extends CacheMethodStrategyAware {

    /**
     * 初始化
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void init() throws Exception;

    /**
     * 获取策略名称
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     */
    public String getStrategyName() throws Exception;

    /**
     * CacheMethod执行策略逻辑
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param pjp
     * @param cacheMethod
     * @param cacheBean
     * @return
     * @throws Throwable
     */
    public Object processCacheMethod(ProceedingJoinPoint pjp, CacheMethod cacheMethod) throws Throwable;

    /**
     * 销毁
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void destroy() throws Exception;
}
