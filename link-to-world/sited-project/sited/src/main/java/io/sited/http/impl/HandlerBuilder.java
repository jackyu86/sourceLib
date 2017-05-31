package io.sited.http.impl;


import com.google.common.collect.Lists;
import io.sited.http.Handler;
import io.sited.http.PathParam;
import io.sited.http.QueryParam;
import io.sited.http.Request;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;
import io.sited.util.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import static io.sited.util.CodeBuilder.typeVariableLiteral;


public class HandlerBuilder<T> {
    private final Class<T> serviceInterface;
    private final T service;
    private final Method method;

    public HandlerBuilder(Class<T> serviceInterface, T service, Method method) {
        this.serviceInterface = serviceInterface;
        this.service = service;
        this.method = method;
    }

    public Handler build() {
        DynamicInstanceBuilder<Handler> builder = new DynamicInstanceBuilder<>(Handler.class, service.getClass().getCanonicalName() + "$" + method.getName());
        builder.addField(new CodeBuilder().append("final %s delegate;", serviceInterface.getCanonicalName()).build());
        builder.constructor(new Class[]{serviceInterface}, "{this.delegate = $1;}");
        builder.addMethod(buildMethod());
        return builder.build(service);
    }

    private String buildMethod() {
        CodeBuilder builder = new CodeBuilder();
        builder.append("public %s handle(%s request) throws Exception {\n", Object.class.getCanonicalName(), Request.class.getCanonicalName());
        List<String> params = Lists.newArrayList();

        Annotation[][] annotations = method.getParameterAnnotations();
        Type[] paramTypes = method.getGenericParameterTypes();
        for (int i = 0; i < annotations.length; i++) {
            Type paramType = paramTypes[i];
            String paramTypeLiteral = Types.rawClass(paramType).getCanonicalName();

            PathParam pathParam = path(annotations[i]);
            if (pathParam != null) {
                params.add(pathParam.value());
                builder.indent(1).append("%s %s = (%s) request.pathParam(\"%s\", %s);\n",
                    paramTypeLiteral,
                    pathParam.value(),
                    paramTypeLiteral,
                    pathParam.value(),
                    typeVariableLiteral(paramType));
                continue;
            }

            QueryParam queryParam = query(annotations[i]);
            if (queryParam != null) {
                params.add(queryParam.value());
                builder.indent(1).append("%s %s = (%s) request.queryParam(\"%s\", %s).get();\n",
                    paramTypeLiteral,
                    queryParam.value(),
                    paramTypeLiteral,
                    queryParam.value(),
                    typeVariableLiteral(paramType));
                continue;
            }

            if (paramType.equals(Request.class)) {
                params.add("request");
                continue;
            }

            params.add("body" + i);
            builder.indent(1).append("%s body%s = (%s) request.body(%s);\n",
                paramTypeLiteral,
                i,
                paramTypeLiteral,
                typeVariableLiteral(paramType));
        }

        if (void.class == method.getReturnType()) {
            builder.indent(1).append("delegate.%s(", method.getName());
        } else {
            builder.indent(1).append("return delegate.%s(", method.getName());
        }

        int index = 0;
        for (String param : params) {
            if (index > 0) builder.append(", ");
            builder.append(param);
            index++;
        }
        builder.append(");\n");

        if (void.class == method.getReturnType()) {
            builder.indent(1).append("return null;\n");
        }

        builder.append("}");
        return builder.build();
    }

    private PathParam path(Annotation[] annotations) {
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
