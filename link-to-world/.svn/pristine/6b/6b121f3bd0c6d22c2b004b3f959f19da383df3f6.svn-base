package io.sited.validator.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chi
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Size {
    long min();

    long max();

    String message() default "";
}
