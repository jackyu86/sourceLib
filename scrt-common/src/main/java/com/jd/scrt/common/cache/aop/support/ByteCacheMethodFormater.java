package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.cache.aop.CacheMethodFormater;
import com.jd.scrt.common.util.SerializeUtils;

import java.lang.reflect.Method;

/**
 * byte缓存值转换器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ByteCacheMethodFormater implements CacheMethodFormater {

    @Override
    public Object format(Object value, Object target, Method method) throws Exception {
        if (value == null) {
            return null;
        }
        return SerializeUtils.serialize(value);
    }

    @Override
    public Object parse(Object value, Object target, Method method) throws Exception {
        if (value == null) {
            return null;
        }
        return SerializeUtils.unserialize((byte[]) value);
    }

}
