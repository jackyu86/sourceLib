package com.jd.scrt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代理方法注解
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProxyMethod {

    /**
     * 默认为方法名称(method name), eg. $ProxyMethod("sayHello")
     *
     * @return
     * @since 1.0.3
     */
    public String value() default "";

    /**
     * 代理方法名称
     *
     * @return
     * @since 1.0.3
     */
    public String name() default "";

    /**
     * 代理参数类型
     *
     * @return
     * @since 1.0.3
     */
    public Class<?>[] parameterTypes() default {};

}
