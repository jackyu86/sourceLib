package io.sited;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chi
 */
public interface BeanFactory {
    <T> T bind(Type type, T instance);

    <T> T bind(Class<T> type);

    default <T> T bind(T instance) {
        return bind(instance.getClass(), instance);
    }

    <T> T require(Type type);

    <T> T require(Class<T> type);

    boolean contains(Type type);

    void export(Type type);

    List<Type> exportedTypes();
}
