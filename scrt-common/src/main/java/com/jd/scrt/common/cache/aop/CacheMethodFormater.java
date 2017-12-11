package com.jd.scrt.common.cache.aop;

import java.lang.reflect.Method;

/**
 * 缓存数据转换器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface CacheMethodFormater {

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
    public Object format(Object value, Object target, Method method) throws Exception;

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
    public Object parse(Object value, Object target, Method method) throws Exception;
}
