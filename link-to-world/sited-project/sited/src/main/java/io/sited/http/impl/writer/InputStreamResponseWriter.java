package io.sited.http.impl.writer;

import com.google.common.io.ByteStreams;
import io.sited.http.ResponseWriter;
import io.sited.http.Request;
import io.sited.http.impl.ContentTypes;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author chi
 */
public class InputStreamResponseWriter implements ResponseWriter<InputStream> {
    @Override
    public void write(Request request, InputStream response) throws IOException {
        HttpServerExchange exchange = request.require(HttpServerExchange.class);

        Optional<String> range = request.header("Range");
        if (range.isPresent()) {
            String[] ranges = range.get().substring(6).split("-");

            byte[] content = ByteStreams.toByteArray(response);
            int start = Integer.parseInt(ranges[0]);
            int end = ranges.length == 1 ? content.length - 1 : Integer.parseInt(ranges[1]);
            byte[] bytes = Arrays.copyOfRange(content, start, end);

            exchange.setStatusCode(206);

            exchange.getResponseHeaders().add(new HttpString("Content-Type"), ContentTypes.of(request.path()));
            exchange.getResponseHeaders().add(new HttpString("Accept-Ranges"), "bytes");
            exchange.getResponseHeaders().add(new HttpString("Content-range"), "bytes " + start + "-" + end + "/" + content.length);
            exchange.getResponseSender().send(ByteBuffer.wrap(bytes));
        } else {
            if (!exchange.isBlocking()) {
                exchange.startBlocking();
            }

            exchange.getResponseHeaders().add(new HttpString("Content-Type"), ContentTypes.of(request.path()));
            try (InputStream inputStream = response) {
                ByteStreams.copy(inputStream, exchange.getOutputStream());
            }
        }
    }
}
