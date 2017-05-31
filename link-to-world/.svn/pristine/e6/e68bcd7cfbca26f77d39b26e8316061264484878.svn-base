package io.sited.test.impl;

import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.http.Client;
import io.sited.http.HttpMethod;
import io.sited.http.HttpModule;
import io.sited.http.MultipartFile;
import io.sited.http.Session;
import io.sited.http.exception.BadRequestException;
import io.sited.http.impl.RequestImpl;
import io.sited.util.JSON;
import io.sited.util.Types;
import io.sited.validator.Validator;
import io.sited.validator.ValidatorConfig;

import java.io.File;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class MockRequestImpl extends RequestImpl {
    public Map<String, String> headers;
    public Map<String, String> cookies;
    public Map<String, String> queries;

    public HttpMethod method;
    public String baseURL;
    public String path;
    public Object body;
    public String contentType;

    public MockRequestImpl(HttpModule.RequestConfigImpl requestConfig, ValidatorConfig validatorConfig, Charset charset, Locale locale) {
        super(requestConfig, validatorConfig, charset, locale);
    }

    @Override
    public String url() {
        StringBuilder b = new StringBuilder();
        b.append(baseURL);
        b.append(path);

        List<Map.Entry<String, String>> entries = Lists.newArrayList(queries.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            if (i == 0) {
                b.append('?');
            }
            b.append(entries.get(i).getKey()).append('=').append(entries.get(i).getValue());
            if (i != entries.size() - 1) {
                b.append('&');
            }
        }
        return b.toString();
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public String host() {
        try {
            return new URL(baseURL).getHost();
        } catch (MalformedURLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public int port() {
        try {
            return new URL(baseURL).getPort();
        } catch (MalformedURLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public String scheme() {
        try {
            return new URL(baseURL).getProtocol();
        } catch (MalformedURLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public String accept() {
        Optional<String> header = header("Accept");
        if (header.isPresent()) {
            return header.get();
        }
        return "*/*";
    }

    @Override
    public String pathParam(String name) {
        return pathParam(name, String.class);
    }

    @Override
    public <T> T pathParam(String name, Class<T> type) {
        if (!pathParams.containsKey(name)) {
            throw new StandardException("missing path param, name={}", name);
        }
        return JSON.convert(pathParams.get(name), type);
    }

    @Override
    public Optional<String> queryParam(String key) {
        return queryParam(key, String.class);
    }

    @Override
    public <T> Optional<T> queryParam(String key, Class<T> type) {
        if (!queries.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(queries.get(key), type));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T body(Type type) {
        if (body == null) {
            throw new StandardException("missing body");
        }
        Validator validator = validatorConfig.validator(Types.raw(type));
        Validator.Context context = new Validator.Context();
        boolean valid = validator.validate(body, context);
        if (!valid) {
            throw new BadRequestException(context.invalidFields);
        }
        return (T) body;
    }

    @Override
    public byte[] body() {
        if (body == null) {
            throw new StandardException("missing body");
        } else if (body instanceof byte[]) {
            return (byte[]) body;
        } else {
            return JSON.toJSONBytes(body);
        }
    }

    @Override
    public Optional<MultipartFile> file(String name) {
        if (body instanceof File) {
            MultipartFile file = new MultipartFile();
            file.file = (File) body;
            file.fileName = file.file.getName();
            return Optional.of(file);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> header(String name) {
        return header(name, String.class);
    }

    @Override
    public <T> Optional<T> header(String name, Class<T> type) {
        if (!headers.containsKey(name)) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(headers.get(name), type));
    }

    @Override
    public Optional<String> cookie(String name) {
        return cookie(name, String.class);
    }

    @Override
    public <T> Optional<T> cookie(String name, Class<T> type) {
        if (!cookies.containsKey(name)) {
            return Optional.empty();
        }
        return Optional.of(JSON.convert(cookies.get(name), type));
    }

    @Override
    public Map<String, String> queries() {
        return queries;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Map<String, String> cookies() {
        return cookies;
    }

    @Override
    public Client client() {
        return MockClient.get();
    }

    @Override
    public Session session() {
        return require(Session.class);
    }
}