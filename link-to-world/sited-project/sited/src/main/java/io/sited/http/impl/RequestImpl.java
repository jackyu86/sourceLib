package io.sited.http.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.sited.Provider;
import io.sited.StandardException;
import io.sited.http.Client;
import io.sited.http.HttpMethod;
import io.sited.http.HttpModule;
import io.sited.http.MultipartFile;
import io.sited.http.Request;
import io.sited.http.Session;
import io.sited.http.exception.BadRequestException;
import io.sited.util.JSON;
import io.sited.util.Types;
import io.sited.validator.Validator;
import io.sited.validator.ValidatorConfig;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.HeaderMap;
import io.undertow.util.Headers;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Deque;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class RequestImpl implements Request {
    public final Map<String, String> pathParams = Maps.newHashMap();
    public final Map<Object, Object> context = Maps.newHashMap();
    public final HttpModule.RequestConfigImpl requestConfig;
    public final ValidatorConfig validatorConfig;

    private final String id;
    private final LocalDateTime timestamp;
    private final Charset charset;
    private final Locale locale;

    public RequestImpl(HttpModule.RequestConfigImpl requestConfig, ValidatorConfig validatorConfig, Charset charset, Locale locale) {
        this.requestConfig = requestConfig;
        this.validatorConfig = validatorConfig;
        this.charset = charset;
        this.locale = locale;

        id = UUID.randomUUID().toString();
        timestamp = LocalDateTime.now();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public String url() {
        return new RequestURLBuilder(require(HttpServerExchange.class)).url();
    }

    @Override
    public HttpMethod method() {
        return HttpMethod.valueOf(require(HttpServerExchange.class).getRequestMethod().toString());
    }

    @Override
    public String scheme() {
        return require(HttpServerExchange.class).getRequestScheme();
    }

    public String path() {
        return require(HttpServerExchange.class).getRequestPath();
    }

    @Override
    public String host() {
        return require(HttpServerExchange.class).getHostName();
    }

    @Override
    public int port() {
        String xForwardedPort = require(HttpServerExchange.class).getRequestHeaders().getFirst(Headers.X_FORWARDED_PORT);
        if (xForwardedPort != null) {
            int index = xForwardedPort.indexOf(',');
            if (index > 0)
                return Integer.parseInt(xForwardedPort.substring(0, index));
            else
                return Integer.parseInt(xForwardedPort);
        }
        return require(HttpServerExchange.class).getHostPort();
    }

    @Override
    public String pathParam(String name) {
        return pathParam(name, String.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T pathParam(String name, Class<T> type) {
        if (!pathParams.containsKey(name)) {
            throw new StandardException("missing path param, name={}", name);
        }
        String value = pathParams.get(name);
        if (type.equals(String.class)) {
            return (T) value;
        }
        return JSON.convert(value, type);
    }

    @Override
    public Optional<String> queryParam(String name) {
        Deque<String> value = require(HttpServerExchange.class).getQueryParameters().get(name);
        if (value == null || Strings.isNullOrEmpty(value.getFirst())) {
            return Optional.empty();
        }
        return Optional.of(value.getFirst());
    }

    @Override
    public <T> Optional<T> queryParam(String name, Class<T> type) {
        Deque<String> value = require(HttpServerExchange.class).getQueryParameters().get(name);
        if (value == null || Strings.isNullOrEmpty(value.getFirst())) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(value.getFirst(), type));
    }

    @Override
    public Optional<String> header(String name) {
        String value = require(HttpServerExchange.class).getRequestHeaders().getFirst(name);
        if (Strings.isNullOrEmpty(value)) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public <T> Optional<T> header(String name, Class<T> type) {
        String value = require(HttpServerExchange.class).getRequestHeaders().getFirst(name);
        if (Strings.isNullOrEmpty(value)) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(value, type));
    }

    @Override
    public Optional<String> cookie(String name) {
        Cookie cookie = require(HttpServerExchange.class).getRequestCookies().get(name);
        if (cookie == null) {
            return Optional.empty();
        }
        return Optional.of(cookie.getValue());
    }

    @Override
    public <T> Optional<T> cookie(String name, Class<T> type) {
        Cookie cookie = require(HttpServerExchange.class).getRequestCookies().get(name);
        if (cookie == null) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(cookie.getValue(), type));
    }


    @Override
    public <T> T body(Type type) {
        T value;
        if (isFormBody()) {
            Map<String, String> form = Maps.newHashMap();
            FormData formBody = require(HttpServerExchange.class).getAttachment(FormDataParser.FORM_DATA);
            for (String name : formBody) {
                FormData.FormValue formValue = formBody.get(name).getFirst();
                if (!formValue.isFile()) {
                    form.put(name, formValue.getValue());
                }
            }
            value = JSON.convert(form, type);
        } else {
            byte[] body = body();
            if (body == null) {
                throw new StandardException("missing json request body");
            }
            value = JSON.fromJSON(body, type);
        }

        Validator validator = validatorConfig.validator(Types.raw(type));
        Validator.Context context = new Validator.Context();
        boolean valid = validator.validate(value, context);
        if (!valid) {
            throw new BadRequestException(context.invalidFields);
        }
        return value;
    }


    @Override
    public byte[] body() {
        RequestBodyParser.RequestBody requestBody = require(HttpServerExchange.class).getAttachment(RequestBodyParser.REQUEST_BODY);
        return requestBody == null ? null : requestBody.body();
    }

    private boolean isFormBody() {
        Optional<String> header = header("Content-Type");
        if (header.isPresent()) {
            return header.get().contains("application/x-www-form-urlencoded") || header.get().contains("multipart/form-data");
        }
        return false;
    }

    @Override
    public Optional<MultipartFile> file(String name) {
        FormData formBody = require(HttpServerExchange.class).getAttachment(FormDataParser.FORM_DATA);
        if (formBody == null || !formBody.contains("file")) {
            return Optional.empty();
        }
        FormData.FormValue file = formBody.get("file").getFirst();
        if (!file.isFile()) {
            return Optional.empty();
        }
        MultipartFile multipartFile = new MultipartFile();
        multipartFile.file = file.getPath().toFile();
        multipartFile.fileName = file.getFileName();
        return Optional.of(multipartFile);
    }

    @Override
    public Map<String, String> queries() {
        HttpServerExchange exchange = require(HttpServerExchange.class);
        if (exchange.getQueryParameters().isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> queries = Maps.newHashMapWithExpectedSize(exchange.getQueryParameters().size());
        exchange.getQueryParameters().forEach((name, values) -> queries.put(name, values.getFirst()));
        return queries;
    }

    @Override
    public Map<String, String> headers() {
        HttpServerExchange exchange = require(HttpServerExchange.class);
        HeaderMap headerMap = exchange.getRequestHeaders();
        if (headerMap.size() == 0) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = Maps.newHashMapWithExpectedSize(headerMap.size());
        headerMap.getHeaderNames().forEach((name) -> headers.put(name.toString(), headerMap.getFirst(name)));
        return headers;
    }

    @Override
    public Map<String, String> cookies() {
        HttpServerExchange exchange = require(HttpServerExchange.class);
        if (exchange.getRequestCookies().isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> cookies = Maps.newHashMapWithExpectedSize(exchange.getRequestCookies().size());
        exchange.getRequestCookies().forEach((name, cookie) -> cookies.put(name, cookie.getValue()));
        return cookies;
    }

    @Override
    public Map<Object, Object> context() {
        return context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T require(Class<T> type) {
        T value = (T) context().get(type);
        if (value == null) {
            Provider<T, Request> provider = requestConfig.binder.provider(type);
            value = provider.get(this);
            if (value == null) {
                provider.missing(type);
            }
            context().put(type, value);
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T require(Class<T> type, T defaultValue) {
        T value = (T) context().get(type);
        if (value == null) {
            Provider<T, Request> provider = requestConfig.binder.provider(type);
            value = provider.get(this);
            if (value == null) {
                return defaultValue;
            }
            context().put(type, value);
        }
        return value;
    }


    @Override
    public Session session() {
        return require(Session.class);
    }


    @Override
    public Client client() {
        return require(Client.class);
    }

    @Override
    public Charset charset() {
        return charset;
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public String accept() {
        Optional<String> header = header("Accept");
        return header.isPresent() ? header.get() : "*/*";
    }
}