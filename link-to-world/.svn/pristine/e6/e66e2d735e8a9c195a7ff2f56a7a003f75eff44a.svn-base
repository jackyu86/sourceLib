package io.sited.http;

import java.util.Optional;

/**
 * @author chi
 */
public interface Session {
    String id();

    Optional<String> get(String key);

    <T> Optional<T> get(String key, Class<T> type);

    <T> Session set(String key, T value);

    Session remove(String key);

    void invalidate();
}
