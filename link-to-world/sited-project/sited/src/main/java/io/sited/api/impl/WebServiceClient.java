package io.sited.api.impl;

import io.sited.http.HttpMethod;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author chi
 */
public interface WebServiceClient {
    Object execute(HttpMethod method, String path, Map pathParams, Map queryParams, Object body, Type returnType);
}
