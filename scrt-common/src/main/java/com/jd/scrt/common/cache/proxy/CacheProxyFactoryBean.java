package com.jd.scrt.common.cache.proxy;


import com.jd.scrt.common.proxy.SelectableProxyFactoryBean;

/**
 * Cache能选择的JDK动态代理创建工厂Bean
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.6
 */
public class CacheProxyFactoryBean<T> extends SelectableProxyFactoryBean<T> {

    private Class<?> dynamicProxyClass = CacheDynamicProxy.class;//默认为：CacheDynamicProxy.class

    public Class<?> getDynamicProxyClass() {
        return dynamicProxyClass;
    }

    public void setDynamicProxyClass(Class<?> dynamicProxyClass) {
        this.dynamicProxyClass = dynamicProxyClass;
    }

}
