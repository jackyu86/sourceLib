package com.jd.scrt.common.cache;

/**
 * 缓存接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public interface Cache {

    public Object put(String key, Object value) throws Exception;

    public Object put(String key, Object value, long timeout) throws Exception;

    public Object get(String key) throws Exception;

    public Object remove(String key) throws Exception;

    public boolean containsKey(String key) throws Exception;
}
