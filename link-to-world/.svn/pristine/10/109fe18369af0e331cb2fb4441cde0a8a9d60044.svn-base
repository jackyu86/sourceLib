package io.sited.http;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public interface ServerResponse {
    <T> T body(Type bodyClass);

    String contentType();

    int statusCode();

    Optional<String> header(String name);

    <T> Optional<T> header(String name, Class<T> type);

    Optional<String> cookie(String name);

    <T> Optional<T> cookie(String name, Class<T> type);

    Map<String, String> headers();

    Map<String, String> cookies();
}
