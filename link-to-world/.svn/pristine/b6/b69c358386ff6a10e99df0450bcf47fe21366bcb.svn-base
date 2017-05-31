package io.sited;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import io.sited.http.impl.RequestBodyParser;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.channels.StreamSourceChannel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.undertow.Handlers.path;

/**
 * @author chi
 */
public class Sited {
    private static final String KEY_HOST = "sited.host";
    private static final String KEY_PORT = "sited.port";
    private static final String KEY_DIR = "sited.dir";

    private final Logger logger = LoggerFactory.getLogger(Sited.class);
    private final Map<String, SiteHandler> handlers = Maps.newHashMap();
    private final Undertow server;
    private boolean multiSitesEnabled = false;
    private final Application app;

    public Sited() {
        app = app();
        server = Undertow.builder()
            .addHttpListener(app.port, app.host)
            .setServerOption(UndertowOptions.MAX_ENTITY_SIZE, 10000000L)
            .setHandler(path()
                .addPrefixPath("/", new SitedIOHandler(this)))
            .build();
    }

    static Application app() {
        Application app = new Application();
        try (InputStream inputStream = Resources.getResource("sited.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            if (properties.containsKey(KEY_HOST)) {
                app.host = properties.getProperty(KEY_HOST);
            }
            if (properties.containsKey(KEY_PORT)) {
                app.port = Integer.parseInt(properties.getProperty(KEY_PORT));
            }
            if (properties.containsKey(KEY_DIR)) {
                app.dir = new File(properties.getProperty(KEY_DIR));
            }
        } catch (IOException e) {
            throw new StandardException(e);
        }

        //TODO: remove later
        String conf = System.getProperty("site.conf");
        if (!Strings.isNullOrEmpty(conf)) {
            app.dir = new File(conf).getParentFile();
        }
        String host = System.getProperty(KEY_HOST);
        if (!Strings.isNullOrEmpty(host)) {
            app.host = host;
        }
        String port = System.getProperty(KEY_PORT);
        if (!Strings.isNullOrEmpty(port)) {
            app.port = Integer.parseInt(port);
        }
        String dir = System.getProperty(KEY_DIR);
        if (!Strings.isNullOrEmpty(dir)) {
            app.dir = new File(dir);
        }
        return app;
    }

    public Sited enableMultiSites() {
        multiSitesEnabled = true;
        return this;
    }

    public void start() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        handlers.values().forEach(handler -> handler.site.start());

        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
        logger.info("sited {}:{} started in {}ms", app.host, app.port, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public Sited install(Site site) {
        logger.info("install site, host={}", site.host());
        handlers.put(site.host(), new SiteHandler(site));
        return this;
    }

    public Site site(String host) {
        SiteHandler handler = handlers.get(host);
        if (handler == null) {
            throw new StandardException("missing site, host={}", host);
        }
        return handler.site;
    }

    public synchronized void stop() {
        server.stop();
        handlers.values().forEach(handler -> handler.site.stop());
        logger.info("sited stopped");
    }

    private static class SitedIOHandler implements HttpHandler {
        private final FormParserFactory formParserFactory;
        private final HttpHandler next;
        private final Sited sited;

        SitedIOHandler(Sited sited) {
            this.sited = sited;
            this.next = exchange -> {
                SiteHandler handler = handler(exchange.getHostName());
                handler.handleRequest(exchange);
            };
            FormParserFactory.Builder builder = FormParserFactory.builder();
            builder.setDefaultCharset(Charsets.UTF_8.name());
            formParserFactory = builder.build();
        }

        private SiteHandler handler(String hostName) {
            SiteHandler handler = sited.multiSitesEnabled ? sited.handlers.get(hostName) : sited.handlers.get("localhost");
            if (handler == null) {
                throw new StandardException("missing site, host={}", hostName);
            }
            return handler;
        }

        @Override
        public void handleRequest(HttpServerExchange exchange) throws Exception {
            if (exchange.isInIoThread()) {
                exchange.dispatch(this);
                return;
            }

            HttpString method = exchange.getRequestMethod();
            if (Methods.POST.equals(method)) {
                FormDataParser parser = formParserFactory.createParser(exchange);
                if (parser != null) {
                    parser.parse(next);
                    return;
                }
            }

            if (hasBody(exchange)) {
                RequestBodyParser reader = new RequestBodyParser(exchange, next);
                StreamSourceChannel channel = exchange.getRequestChannel();
                reader.read(channel);
                if (!reader.complete()) {
                    channel.getReadSetter().set(reader);
                    channel.resumeReads();
                    return;
                }
            }

            exchange.dispatch(next);
        }

        private boolean hasBody(HttpServerExchange exchange) {
            int length = (int) exchange.getRequestContentLength();
            if (length == 0) return false;

            HttpString method = exchange.getRequestMethod();
            return Methods.POST.equals(method) || Methods.PUT.equals(method);
        }
    }

    public static class Application {
        String host = "0.0.0.0";
        int port = 8080;
        File dir = new File("./src/main/dist/");

        public String host() {
            return host;
        }

        public int port() {
            return port;
        }

        public File dir() {
            return dir;
        }
    }
}
