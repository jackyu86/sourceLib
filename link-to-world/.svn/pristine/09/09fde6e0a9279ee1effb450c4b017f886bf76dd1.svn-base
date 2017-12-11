package io.sited.api;

/**
 * @author chi
 */
public interface APIConfig {
    <T> APIConfig service(Class<T> serviceClass, Class<? extends T> serviceImplClass);

    <T> T client(Class<T> serviceClass, String serviceURL);

    <T> T client(Class<T> serviceClass, String serviceURL, boolean xml);
}
