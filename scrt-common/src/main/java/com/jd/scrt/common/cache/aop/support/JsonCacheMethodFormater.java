package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.cache.aop.CacheMethodFormater;
import com.jd.scrt.common.util.JacksonUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * json缓存值转换器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class JsonCacheMethodFormater implements CacheMethodFormater {

    @Override
    public Object format(Object value, Object target, Method method) throws Exception {
        if (value == null) {
            return null;
        }
        return JacksonUtils.toJson(value);
    }

    @Override
    public Object parse(Object value, Object target, Method method) throws Exception {
        if (value == null) {
            return null;
        }
        String json = (String) value;
        if (json == null || json.length() == 0) {
            return null;
        }
        Type type = method.getGenericReturnType();
        return JacksonUtils.parseJson(json, type);
    }

}
