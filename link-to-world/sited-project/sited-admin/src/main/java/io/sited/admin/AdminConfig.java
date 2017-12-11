package io.sited.admin;

/**
 * @author chi
 */
public interface AdminConfig {
    <T> AdminConfig controller(Class<T> controllerClass);

    Console console();

    AdminConfig excludeURL(String... urls);
}
