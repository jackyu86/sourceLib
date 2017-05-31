package io.sited;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chi
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ModuleInfo {
    String name();

    String description() default "";

    Class<? extends Module>[] require() default {};
}
