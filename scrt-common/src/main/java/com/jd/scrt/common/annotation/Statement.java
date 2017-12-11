package com.jd.scrt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Statement注解
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Statement {

    /**
     * namespace + "." + sqlId
     *
     * @return
     */
    public String value() default "";

    /**
     * 命名空间
     *
     * @return
     * @since 1.0.3
     */
    public String namespace() default "";

    /**
     * SQL主键标识
     *
     * @return
     * @since 1.0.3
     */
    public String sqlId() default "";

    /**
     * SQL类型(select/insert/update/delete)
     *
     * @return
     * @since 1.0.3
     */
    public String type() default "";//

    /**
     * SQL内容
     *
     * @return
     * @since 1.0.3
     */
    public String sql() default "";

    /**
     * 参数索引
     *
     * @return
     * @since 1.0.3
     */
    public int argsIndex() default 0;
}
