package io.sited;

/**
 * @author chi
 */
public interface Provider<T, K> {
    T get(K scope);

    //TODO
    default void missing(Class<T> type) {
        throw new StandardException("missing binding, type={}", type);
    }
}