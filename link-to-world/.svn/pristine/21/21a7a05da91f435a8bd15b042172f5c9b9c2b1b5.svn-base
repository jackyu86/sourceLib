package io.sited.http;

import com.google.common.net.MediaType;
import io.sited.StandardException;
import io.sited.http.impl.ContentTypes;
import io.sited.http.impl.ResponseImpl;
import io.sited.http.impl.TemplateBody;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author chi
 */
public interface Response {
    static Response empty() {
        return new ResponseImpl(null)
            .setStatusCode(204)
            .setContentType(MediaType.JSON_UTF_8.toString());
    }

    static Response template(String templatePath, Map<String, Object> context) {
        return new ResponseImpl(new TemplateBody(templatePath, context))
            .setStatusCode(200);
    }

    static Response redirect(String url) {
        return new ResponseImpl(null)
            .setHeader("location", url)
            .setStatusCode(302);
    }

    static Response redirect(String url, int statusCode) {
        if (statusCode != 301 && statusCode != 302) {
            throw new StandardException("invalid redirect status code, code={}", statusCode);
        }
        return new ResponseImpl(null)
            .setHeader("location", url)
            .setStatusCode(statusCode);
    }

    static Response body(Object bean) {
        if (bean == null) {
            return empty();
        }
        return new ResponseImpl(bean)
            .setContentType(MediaType.JSON_UTF_8.toString());
    }

    static Response body(File file) {
        if (file == null) {
            return empty();
        }
        return new ResponseImpl(file)
            .setContentType(ContentTypes.of(file));
    }

    static Response body(InputStream inputStream) {
        if (inputStream == null) {
            return empty();
        }
        return new ResponseImpl(inputStream);
    }

    static Response body(String text) {
        if (text == null) {
            return empty();
        }
        return new ResponseImpl(text)
            .setContentType(MediaType.PLAIN_TEXT_UTF_8.toString());
    }

    static Response body(byte[] body) {
        if (body == null) {
            return empty();
        }
        return new ResponseImpl(body)
            .setContentType(MediaType.OCTET_STREAM.toString());
    }

    <T> T body();

    Response setHeader(String name, String value);

    Response setCookie(String name, String value);

    Response setCookie(String name, String value, Integer maxAge);

    Response setCookie(String name, String value, Integer maxAge, String domain);

    Response setCookie(String name, String value, Integer maxAge, String domain, Boolean httpOnly, Boolean secure);

    Response setContentType(String contentType);

    Response setContentLength(long contentLength);

    Response setCharset(Charset charset);

    Response setStatusCode(int statusCode);

    int statusCode();


    Map<String, String> headers();

    Map<String, String> cookies();
}
