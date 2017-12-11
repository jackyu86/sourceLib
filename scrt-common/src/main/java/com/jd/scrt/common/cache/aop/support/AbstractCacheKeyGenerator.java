package com.jd.scrt.common.cache.aop.support;

import java.lang.reflect.Method;

import com.jd.scrt.common.cache.aop.CacheKeyGenerator;
import org.apache.log4j.Logger;

/**
 * 缓存KEY抽象生成器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public abstract class AbstractCacheKeyGenerator implements CacheKeyGenerator {

    protected final Logger logger = Logger.getLogger(this.getClass());


    /**
     * 获取默认的缓存key(返回默认key:[全类名]+[.]+[方法名]+[-args1]+...)
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param pjp
     * @return
     * @throws Exception
     */
    protected String generateDefaultCacheKey(Object target, Method method, Object[] args) throws Exception {
        StringBuilder result = new StringBuilder();

        String clazz_name = target.getClass().getName();
        String method_name = method.getName();
        result.append(clazz_name).append(".").append(method_name);

        if (args != null && args.length > 0) {
            for (Object obj : args) {
                result.append("-").append(String.valueOf(obj));
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("generateDefaultCacheKey: " + result.toString());
        }
        return result.toString();
    }
}
