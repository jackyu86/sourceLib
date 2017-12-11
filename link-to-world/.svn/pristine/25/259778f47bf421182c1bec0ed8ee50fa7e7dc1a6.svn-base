package io.sited.api.impl;

import com.google.common.base.Charsets;
import io.sited.StandardException;
import io.sited.http.HttpMethod;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.http.impl.RoutePath;
import io.sited.http.impl.writer.BadRequestExceptionResponseWriter;
import io.sited.http.impl.writer.StandardExceptionResponseWriter;
import io.sited.util.JSON;
import io.sited.util.Types;
import io.sited.util.XML;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class WebServiceClientImpl implements WebServiceClient {
    private final String serviceURL;
    private final HttpClient httpClient;
    private final boolean xml;

    public WebServiceClientImpl(String serviceURL, HttpClient httpClient, boolean xml) {
        this.serviceURL = serviceURL;
        this.httpClient = httpClient;
        this.xml = xml;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object execute(HttpMethod method, String path, Map pathParams, Map queryParams, Object requestBody, Type returnType) {
        HttpUriRequest request = request(method, path, pathParams, queryParams, requestBody);
        if (xml) {
            request.addHeader("Accept", "text/xml");
        } else {
            request.addHeader("Accept", "application/json");
        }
        try (CloseableHttpResponse response = ((CloseableHttpClient) httpClient).execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();

            switch (statusCode) {
                case 200:
                    if (Types.isGenericOptional(returnType)) {
                        Class<?> valueClass = Types.optionalValueClass(returnType);
                        return Optional.of(decode(response, valueClass));
                    } else {
                        return decode(response, returnType);
                    }
                case 204:
                    if (Types.isGenericOptional(returnType)) {
                        return Optional.empty();
                    } else {
                        return null;
                    }
                case 400:
                    throw new BadRequestException(((BadRequestExceptionResponseWriter.BadRequestExceptionResponse) decode(response, BadRequestExceptionResponseWriter.BadRequestExceptionResponse.class)).invalidFields);
                case 401:
                    throw new UnauthenticatedException(((StandardExceptionResponseWriter.StandardExceptionResponse) decode(response, StandardExceptionResponseWriter.StandardExceptionResponse.class)).message);
                case 403:
                    throw new UnauthorizedException(((StandardExceptionResponseWriter.StandardExceptionResponse) decode(response, StandardExceptionResponseWriter.StandardExceptionResponse.class)).message);
                case 404:
                    throw new NotFoundException(((StandardExceptionResponseWriter.StandardExceptionResponse) decode(response, BadRequestExceptionResponseWriter.BadRequestExceptionResponse.class)).message);

                default:
                    throw new StandardException(((StandardExceptionResponseWriter.StandardExceptionResponse) decode(response, BadRequestExceptionResponseWriter.BadRequestExceptionResponse.class)).message);
            }
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }


    private HttpUriRequest request(HttpMethod method, String path, Map<String, String> pathParams, Map<String, String> queryParams, Object body) {
        RequestBuilder b;

        switch (method) {
            case GET:
                b = RequestBuilder.get(serviceURL(path, pathParams, queryParams));
                return b.build();
            case POST:
                b = RequestBuilder.post(serviceURL(path, pathParams, queryParams));
                if (xml) {
                    org.apache.http.entity.ContentType type = org.apache.http.entity.ContentType.create("text/xml", Charsets.UTF_8);
                    b.setEntity(new ByteArrayEntity(XML.toXML(body).getBytes(Charsets.UTF_8), type));
                } else {
                    org.apache.http.entity.ContentType type = org.apache.http.entity.ContentType.create("application/json", Charsets.UTF_8);
                    b.setEntity(new ByteArrayEntity(JSON.toJSON(body).getBytes(Charsets.UTF_8), type));
                }
                return b.build();
            case PUT:
                b = RequestBuilder.put(serviceURL(path, pathParams, queryParams));
                if (xml) {
                    org.apache.http.entity.ContentType type = org.apache.http.entity.ContentType.create("text/xml", Charsets.UTF_8);
                    b.setEntity(new ByteArrayEntity(XML.toXML(body).getBytes(Charsets.UTF_8), type));
                } else {
                    org.apache.http.entity.ContentType type = org.apache.http.entity.ContentType.create("application/json", Charsets.UTF_8);
                    b.setEntity(new ByteArrayEntity(JSON.toJSON(body).getBytes(Charsets.UTF_8), type));
                }
                return b.build();
            case DELETE:
                b = RequestBuilder.delete(serviceURL(path, pathParams, queryParams));
                return b.build();

            default:
                throw new StandardException("http method not supported, method={}", method);
        }
    }

    private Object decode(CloseableHttpResponse response, Type bodyClass) {
        HttpEntity entity = response.getEntity();
        try {
            if (xml) {
                return XML.fromXML(EntityUtils.toString(entity, Charsets.UTF_8), bodyClass);
            } else {
                return JSON.fromJSON(EntityUtils.toString(entity, Charsets.UTF_8), bodyClass);
            }
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    private String serviceURL(String path, Map<String, String> pathParams, Map<String, String> queryParams) {
        StringBuilder b = new StringBuilder();
        b.append(serviceURL);
        RoutePath routePath = new RoutePath(null, path);
        b.append(routePath.fill(pathParams));
        if (!queryParams.isEmpty()) {
            b.append('?');
            queryParams.forEach((name, value) -> {
                if (value != null) {
                    b.append(name).append('=').append(value);
                }
                b.append('&');
            });
            b.deleteCharAt(b.length() - 1);
        }
        return b.toString();
    }
}
