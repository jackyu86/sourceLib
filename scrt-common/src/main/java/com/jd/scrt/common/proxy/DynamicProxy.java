package com.jd.scrt.common.proxy;

import java.io.Serializable;

/**
 * 动态代理
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public interface DynamicProxy<T> extends Serializable {

    /**
     * 实例工厂方式创建
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public T newProxy() throws Exception;

}
