package com.jd.scrt.common.proxy;

import com.jd.scrt.common.annotation.ProxyMethod;

import java.lang.reflect.Method;


/**
 * 能选择的JDK动态代理
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class SelectableDynamicProxy<T> extends JdkDynamicProxy<T> {

    private static final long serialVersionUID = 8161802516489884307L;

    protected static final Class<ProxyMethod> PROXY_METHOD_CLASS = ProxyMethod.class;

    private Object proxy;// 与target有相同实现接口的普通实现类

    private Class<?> proxyClass = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        logger.debug("proxy invoke begin...");
        ProxyMethod pm = method.getAnnotation(PROXY_METHOD_CLASS);
        if (pm == null) {
            result = this.invokeTarget(proxy, method, args);
        } else {
            result = this.invokeProxy(proxy, method, args, pm);
        }
        logger.debug("proxy invoke end!");
        return result;
    }

    /**
     * 调用target对象
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    protected Object invokeTarget(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(this.getTarget(), args);
    }

    /**
     * 调用proxy对象
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    protected Object invokeProxy(Object proxy, Method method, Object[] args, ProxyMethod proxyMethod) throws Throwable {
        Method proxy_m = this.getProxyMethod(method, proxyMethod);
        return this.invokeProxyMethod(proxy, method, args, proxy_m);
    }

    /**
     * 调用proxy对象的方法
     *
     * @param proxy
     * @param method
     * @param args
     * @param proxyMethod
     * @return
     * @throws Throwable
     */
    protected Object invokeProxyMethod(Object proxy, Method method, Object[] args, Method proxyMethod) throws Throwable {
        return proxyMethod.invoke(this.getProxy(), args);
    }

    /**
     * 获取需要调用的proxy方法
     *
     * @param method
     * @param proxyMethod
     * @return
     * @throws Throwable
     */
    protected Method getProxyMethod(Method method, ProxyMethod proxyMethod) throws Throwable {
        String proxy_method_name = this.getProxyMethodName(proxyMethod);// 自定义方法名称
        Class<?>[] proxy_param_types = proxyMethod.parameterTypes();// 自定义参数类型

        Method proxy_m = null;
        if (proxy_method_name == null || proxy_method_name.length() < 1) {// 使用原生方法名称
            if (proxy_param_types == null || proxy_param_types.length < 1) {// 使用原生方法名称，原生参数类型
                proxy_m = method;
            } else {// 使用原生方法名称，自定义参数类型
                proxy_m = this.getProxyMethod(method.getName(), proxy_param_types);
            }
        } else {// 使用自定义方法名称
            if (proxy_param_types == null || proxy_param_types.length < 1) {// 使用自定义方法名称，原生参数类型
                proxy_m = this.getProxyMethod(proxy_method_name, method.getParameterTypes());
            } else {// 使用自定义方法名称，自定义参数类型
                proxy_m = this.getProxyMethod(proxy_method_name, proxy_param_types);
            }
        }
        return proxy_m;
    }

    /**
     * 获取proxy的方法名称
     *
     * @param proxyMethod
     * @return
     * @throws Exception
     */
    protected String getProxyMethodName(ProxyMethod proxyMethod) throws Exception {
        String proxy_method_name = proxyMethod.name();
        if (proxy_method_name == null || proxy_method_name.length() < 1) {
            logger.info("getProxyMethodName: proxyMethod.name() is empty, return proxyMethod.value()!");
            proxy_method_name = proxyMethod.value();
        }
        return proxy_method_name;
    }

    protected Method getProxyMethod(String name, Class<?>... parameterTypes) throws Exception {
        return this.getProxyClass().getMethod(name, parameterTypes);
    }

    protected Class<?> getProxyClass() {
        if (this.proxyClass == null) {
            this.proxyClass = this.getProxy().getClass();
        }
        return this.proxyClass;
    }

    // ---------- getter and setter ----------//
    protected Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
