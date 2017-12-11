package io.sited.validator;

import java.lang.annotation.Annotation;

/**
 * @author chi
 */
public interface ValidatorConfig {
    ValidatorConfig bind(Class<? extends Annotation> annotationClass, Validator validator);

    ValidatorConfig bind(String name, Validator validator);

    Validator validator(Class<?> type);
}
