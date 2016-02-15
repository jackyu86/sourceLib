package com.jd.scrt.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

/**
 * JDK动态代理
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class JdkDynamicProxy<T> implements DynamicProxy<T>, InvocationHandler {

    private static final long serialVersionUID = -4707843773530388910L;
    protected final Logger logger = Logger.getLogger(this.getClass());

    private Class<?> targetInterface;// 被代理的目标对象接口
    private Object target;// 被代理的目标对象

    @SuppressWarnings("unchecked")
    @Override
    public T newProxy() throws Exception {
        if (this.targetInterface != null) {
            return (T) JdkDynamicProxy.newProxy(targetInterface, this);
        }
        return (T) JdkDynamicProxy.newProxy(target, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // do nothing...
        return null;
    }

    /**
     * 静态工厂方式创建
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param target
     * @param invocationHandler
     * @return
     */
    public static Object newProxy(Object target, InvocationHandler invocationHandler) throws Exception {
        if (target == null || invocationHandler == null) {
            throw new IllegalArgumentException("newProxy: this argument is required; it must not be null");
        }
        return JdkDynamicProxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                invocationHandler);
    }

    public static Object newProxy(Class<?> targetInterface, InvocationHandler invocationHandler) throws Exception {
        if (targetInterface == null || invocationHandler == null) {
            throw new IllegalArgumentException("newProxy: this argument is required; it must not be null");
        }
        Class<?>[] interfaces = new Class<?>[]{targetInterface};
        return JdkDynamicProxy.newProxyInstance(targetInterface.getClassLoader(), interfaces, invocationHandler);
    }

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
            throws Exception {
        // 需要绑定接口(这是一个缺陷，cglib弥补了这一缺陷,通过虚拟子类继承实现，但final类和方法不灵)
        return Proxy.newProxyInstance(loader, interfaces, h);
    }

    // ---------- getter and setter ----------//
    protected Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    protected Class<?> getTargetInterface() {
        return targetInterface;
    }

    public void setTargetInterface(Class<?> targetInterface) {
        this.targetInterface = targetInterface;
    }

}
