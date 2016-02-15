package com.jd.scrt.common.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * 能选择的JDK动态代理创建工厂Bean
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public class SelectableProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<?> targetInterface;// 被代理的目标对象接口
    private Object target;// 被代理的目标对象
    private Object proxy;// 与target有相同实现接口的普通实现类

    private boolean singleton = true;// 默认为单例

    private Class<?> dynamicProxyClass = SelectableDynamicProxy.class;//默认为：SelectableDynamicProxy.class

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected DynamicProxy<T> getDynamicProxyObject() throws Exception {
        SelectableDynamicProxy<T> dynamicProxy = (SelectableDynamicProxy) this.getDynamicProxyClass().newInstance();
        dynamicProxy.setTargetInterface(targetInterface);
        dynamicProxy.setTarget(target);
        dynamicProxy.setProxy(proxy);
        return dynamicProxy;
    }

    @Override
    public T getObject() throws Exception {
        DynamicProxy<T> dynamicProxy = this.getDynamicProxyObject();
        return dynamicProxy.newProxy();
    }

    @Override
    public Class<?> getObjectType() {
        if (this.targetInterface != null) {
            return this.targetInterface;
        }
        if (this.target != null) {
            return this.target.getClass();
        }
        return Object.class;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    // ---------- getter and setter ----------//
    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public Class<?> getDynamicProxyClass() {
        return dynamicProxyClass;
    }

    public void setDynamicProxyClass(Class<?> dynamicProxyClass) {
        this.dynamicProxyClass = dynamicProxyClass;
    }

}
