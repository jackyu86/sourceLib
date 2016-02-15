package com.jd.scrt.common.cache.proxy;

import com.jd.scrt.common.annotation.ProxyMethod;
import com.jd.scrt.common.proxy.SelectableDynamicProxy;

import java.lang.reflect.Method;

/**
 * Cache动态代理(有选择的JDK动态代理)
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.6
 */
public class CacheDynamicProxy<T> extends SelectableDynamicProxy<T> {

    private static final long serialVersionUID = 3065367307556563483L;

    public static final String NULL_SIGN = "null";

    protected Object invokeProxy(Object proxy, Method method, Object[] args, ProxyMethod proxyMethod) throws Throwable {
        Method proxy_m = this.getProxyMethod(method, proxyMethod);
        Object result = this.invokeProxyMethod(proxy, method, args, proxy_m);
        if (result == null) {//如果代理方法返回为空，则调用原生实现（适用于cache只读方式）
            result = this.invokeTarget(proxy, method, args);
        } else if (this.isNullSign(result)) {//如果是自标记的null标识,则将结果置null
            result = null;
            logger.debug("invokeProxy: result is NULL_SIGN.");
        }
        return result;
    }

    /**
     * 判断是否是自标识null值
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @return
     * @throws Exception
     */
    protected boolean isNullSign(Object value) throws Exception {
        if (value == null) {
            return false;
        }
        return (value instanceof String && NULL_SIGN.equals(value));
    }

}
