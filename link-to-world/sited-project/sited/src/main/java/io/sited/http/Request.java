package io.sited.http;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public interface Request {
    String id();

    LocalDateTime timestamp();

    String url();

    String path();

    String host();

    int port();

    HttpMethod method();

    String scheme();

    Charset charset();

    Locale locale();

    String accept();

    String pathParam(String name);

    <T> T pathParam(String name, Class<T> type);

    Optional<String> queryParam(String key);

    <T> Optional<T> queryParam(String key, Class<T> type);

    <T> T body(Type type);

    byte[] body();

    Optional<MultipartFile> file(String name);

    Optional<String> header(String name);

    <T> Optional<T> header(String name, Class<T> type);

    Optional<String> cookie(String name);

    <T> Optional<T> cookie(String name, Class<T> type);

    Map<String, String> queries();

    Map<String, String> headers();

    Map<String, String> cookies();

    Map<Object, Object> context();

    Session session();

    Client client();

    <T> T require(Class<T> type);

    <T> T require(Class<T> type, T defaultValue);
}
