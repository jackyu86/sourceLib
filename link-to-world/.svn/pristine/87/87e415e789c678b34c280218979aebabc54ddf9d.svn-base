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
public @interface Max {
    long value();

    String message() default "";
}
