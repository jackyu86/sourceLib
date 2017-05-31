package io.sited.http.impl.writer;

import io.sited.http.Request;
import io.sited.http.ResponseWriter;
import io.undertow.io.IoCallback;
import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;
import org.xnio.IoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;

public class FileResponseWriter implements ResponseWriter<File> {
    @Override
    public void write(Request request, File file) throws IOException {
        try {
            final FileChannel channel = new FileInputStream(file).getChannel();
            Sender sender = request.require(HttpServerExchange.class).getResponseSender();
            sender.transferFrom(channel, new IoCallback() {
                @Override
                public void onComplete(HttpServerExchange exchange, Sender sender) {
                    IoUtils.safeClose(channel);
                    END_EXCHANGE.onComplete(exchange, sender);
                }

                @Override
                public void onException(HttpServerExchange exchange, Sender sender, IOException exception) {
                    IoUtils.safeClose(channel);
                    END_EXCHANGE.onException(exchange, sender, exception);
                    throw new UncheckedIOException(exception);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


}