package io.sited.api.impl;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.http.PathParam;
import io.sited.http.QueryParam;
import io.sited.http.impl.HandlerMethod;
import io.sited.http.impl.HandlerMethodScanner;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;
import io.sited.util.Types;
import org.apache.http.client.HttpClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import static io.sited.util.CodeBuilder.enumVariableLiteral;
import static io.sited.util.CodeBuilder.typeVariableLiteral;

/**
 * @author chi
 */
public class WebServiceClientBuilder<T> {
    private final Class<T> serviceInterface;
    private final String serviceURL;
    private final HttpClient httpClient;
    private final boolean xml;

    public WebServiceClientBuilder(Class<T> serviceInterface, String serviceURL, HttpClient httpClient, boolean xml) {
        this.serviceInterface = serviceInterface;
        this.serviceURL = serviceURL;
        this.httpClient = httpClient;
        this.xml = xml;
    }

    public T build() {
        DynamicInstanceBuilder<T> builder = new DynamicInstanceBuilder<>(serviceInterface, serviceInterface.getCanonicalName() + "$Client");

        builder.addField(new CodeBuilder().append("final %s client;", WebServiceClientImpl.class.getCanonicalName()).build());
        builder.constructor(new Class[]{WebServiceClientImpl.class}, "this.client = $1;");

        HandlerMethodScanner scanner = new HandlerMethodScanner(serviceInterface);
        scanner.scan().forEach(handlerMethod -> {
            String method = method(handlerMethod);
            builder.addMethod(method);
        });

        return builder.build(new WebServiceClientImpl(serviceURL, httpClient, xml));
    }

    private String method(HandlerMethod handlerMethod) {
        CodeBuilder builder = new CodeBuilder();

        Map<String, Integer> pathParamIndexes = Maps.newHashMap();
        Map<String, Integer> queryParamIndexes = Maps.newHashMap();

        Type returnType = handlerMethod.method.getGenericReturnType();
        Integer bodyIndex = null;
        builder.append("public %s %s(", Types.rawClass(handlerMethod.method.getGenericReturnType()).getCanonicalName(), handlerMethod.method.getName());
        Annotation[][] annotations = handlerMethod.method.getParameterAnnotations();
        Class<?>[] parameterTypes = handlerMethod.method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> paramClass = parameterTypes[i];
            if (i > 0) builder.append(", ");
            builder.append("%s param%s", paramClass.getCanonicalName(), i);

            PathParam pathParam = pathParam(annotations[i]);
            if (pathParam != null) {
                pathParamIndexes.put(pathParam.value(), i);
                continue;
            }

            QueryParam queryParam = query(annotations[i]);
            if (queryParam != null) {
                queryParamIndexes.put(queryParam.value(), i);
                continue;
            }

            if (bodyIndex != null) {
                throw new StandardException("Web service allow one body only, class={}, method={}", serviceInterface.getCanonicalName(), handlerMethod.method);
            }
            bodyIndex = i;
        }

        builder.append(") {\n");
        builder.indent(1).append("Object body = %s;\n", bodyIndex == null ? "null" : "param" + bodyIndex);

        builder.indent(1).append("java.util.Map pathParams = new java.util.HashMap();\n");
        pathParamIndexes.forEach((name, index) ->
            builder.indent(1).append("pathParams.put(\"%s\", param%s.toString());\n", name, index));

        builder.indent(1).append("java.util.Map queryParams = new java.util.HashMap();\n");
        queryParamIndexes.forEach((name, index) ->
            builder.indent(1).append("queryParams.put(\"%s\", param%s.toString());\n", name, index));

        String returnTypeLiteral = returnType == void.class ? Void.class.getCanonicalName() : Types.rawClass(returnType).getCanonicalName();

        builder.indent(1).append("%s response = (%s) client.execute(%s, \"%s\", pathParams, queryParams, body, %s);\n",
            returnTypeLiteral,
            returnTypeLiteral,
            enumVariableLiteral(handlerMethod.httpMethod),
            handlerMethod.path,
            typeVariableLiteral(returnType));

        if (returnType != void.class) builder.indent(1).append("return response;\n");

        builder.append("}");
        return builder.build();
    }

    private PathParam pathParam(Annotation[] annotations) {
        if (annotations.length == 0) return null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof PathParam) return (PathParam) annotation;
        }
        return null;
    }

    private QueryParam query(Annotation[] annotations) {
        if (annotations.length == 0) return null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof QueryParam) return (QueryParam) annotation;
        }
        return null;
    }
}
