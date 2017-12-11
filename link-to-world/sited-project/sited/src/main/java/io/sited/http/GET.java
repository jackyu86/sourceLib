package io.sited.http;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chi
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface GET {
    String value() default "GET";
}