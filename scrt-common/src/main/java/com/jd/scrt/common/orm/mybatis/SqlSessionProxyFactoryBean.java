package com.jd.scrt.common.orm.mybatis;


import com.jd.scrt.common.proxy.SelectableProxyFactoryBean;

/**
 * SqlSession能选择的JDK动态代理创建工厂Bean
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public class SqlSessionProxyFactoryBean<T> extends SelectableProxyFactoryBean<T> {

    private Class<?> dynamicProxyClass = SqlSessionDynamicProxy.class;//默认为：SqlSessionDynamicProxy.class

    public Class<?> getDynamicProxyClass() {
        return dynamicProxyClass;
    }

    public void setDynamicProxyClass(Class<?> dynamicProxyClass) {
        this.dynamicProxyClass = dynamicProxyClass;
    }

}
