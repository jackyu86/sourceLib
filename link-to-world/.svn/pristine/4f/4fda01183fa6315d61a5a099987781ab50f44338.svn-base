package io.sited.http;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author chi
 */
public interface Invocation {
    <T extends Annotation> Optional<T> annotation(Class<T> annotationClass);

    Object proceed() throws Exception;

    Request request();
}
