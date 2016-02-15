package com.jd.scrt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存方法注解
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheMethod {

    public String value() default "";

    /**
     * 缓存KEY
     *
     * @return
     * @since 1.0.3
     */
    public String key() default "";

    /**
     * 缓存操作BeanId
     *
     * @return
     * @since 1.0.3
     */
    public String cacheBean() default "";

    /**
     * 缓存超时时间(单位：毫秒)
     *
     * @return
     * @since 1.0.3
     */
    public long timeout() default 0;

    /**
     * null数据缓存超时时间(单位：毫秒)
     *
     * @return
     * @since 1.0.6
     */
    public long nullTimeout() default 0;

    /**
     * 是否禁用
     *
     * @return
     * @since 1.0.6
     */
    public boolean disabled() default false;

    /**
     * 是否只读
     *
     * @return
     * @since 1.0.6
     */
    public boolean readonly() default false;

    /**
     * 是否启用同步加载锁(锁定单线程加载数据)
     *
     * @return
     * @since 1.0.6
     */
    public boolean lock() default false;

    /**
     * 缓存数据格式化类型 (如:byte,json,需要注册CacheMethodFormater才可使用)
     *
     * @return
     * @since 1.0.6
     */
    public String format() default "";

    /**
     * 缓存执行策略
     *
     * @return
     * @since 1.0.6
     */
    public String strategy() default "";

    /**
     * 缓存执行策略参数
     *
     * @return
     * @since 1.0.6
     */
    public String strategyArgs() default "";

}
