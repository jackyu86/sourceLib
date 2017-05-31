package io.sited.web;

import java.util.List;

/**
 * @author chi
 */
public interface WebConfig {
    String baseURL();

    List<String> baseCdnURLs();

    Boolean isCacheEnabled();

    <T> WebConfig controller(Class<T> controllerClass);

    AssetsConfig assets();
}
