package io.sited.http;

import com.google.common.collect.Maps;
import io.sited.Binder;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.cache.CacheConfig;
import io.sited.cache.CacheModule;
import io.sited.cache.CacheOptions;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.http.impl.ClientProviderImpl;
import io.sited.http.impl.HandlerBuilder;
import io.sited.http.impl.HandlerMethodScanner;
import io.sited.http.impl.HandlerRef;
import io.sited.http.impl.HealthCheckController;
import io.sited.http.impl.HttpClientBuilder;
import io.sited.http.impl.HttpServerExchangeProviderImpl;
import io.sited.http.impl.Router;
import io.sited.http.impl.SessionImpl;
import io.sited.http.impl.SessionInterceptor;
import io.sited.http.impl.SessionProvider;
import io.sited.http.impl.writer.BadRequestExceptionResponseWriter;
import io.sited.http.impl.writer.ByteArrayResponseWriter;
import io.sited.http.impl.writer.FileResponseWriter;
import io.sited.http.impl.writer.InputStreamResponseWriter;
import io.sited.http.impl.writer.NotFoundExceptionResponseWriter;
import io.sited.http.impl.writer.ObjectResponseWriter;
import io.sited.http.impl.writer.OptionalResponseWriter;
import io.sited.http.impl.writer.ResponseResponseWriter;
import io.sited.http.impl.writer.StandardExceptionResponseWriter;
import io.sited.http.impl.writer.UnauthenticatedExceptionResponseWriter;
import io.sited.http.impl.writer.UnauthorizedExceptionResponseWriter;
import io.sited.i18n.I18nModule;
import io.sited.log.LogModule;
import io.sited.validator.ValidatorModule;
import io.undertow.server.HttpServerExchange;
import org.apache.http.client.HttpClient;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
@ModuleInfo(name = "http", require = {ValidatorModule.class, LogModule.class, I18nModule.class, CacheModule.class})
public class HttpModule extends Module implements HttpConfig {
    public final RequestConfigImpl requestConfig = new RequestConfigImpl();
    private final Router router = new Router();
    private final Map<Class<?>, ResponseWriter> writers = Maps.newHashMap();

    @Override
    protected void configure() throws Exception {
        HttpOptions httpOptions = options(HttpOptions.class);

        CacheOptions cacheOptions = new CacheOptions();
        cacheOptions.expireTime = httpOptions.session.expireTime;
        require(CacheConfig.class).create(SessionImpl.SessionData.class, cacheOptions);

        request()
            .bind(HttpServerExchange.class, new HttpServerExchangeProviderImpl())
            .bind(Session.class, bind(SessionProvider.class))
            .bind(Client.class, new ClientProviderImpl(site()));

        controller(HealthCheckController.class, new HealthCheckController(), this);

        writer(File.class, new FileResponseWriter());
        writer(InputStream.class, new InputStreamResponseWriter());
        writer(byte[].class, new ByteArrayResponseWriter());
        writer(Response.class, new ResponseResponseWriter(this));
        writer(Object.class, new ObjectResponseWriter());
        writer(Optional.class, new OptionalResponseWriter());

        writer(Throwable.class, new StandardExceptionResponseWriter());
        writer(UnauthorizedException.class, new UnauthorizedExceptionResponseWriter());
        writer(UnauthenticatedException.class, new UnauthenticatedExceptionResponseWriter());
        writer(NotFoundException.class, new NotFoundExceptionResponseWriter());
        writer(BadRequestException.class, bind(BadRequestExceptionResponseWriter.class));

        interceptor("/", bind(SessionInterceptor.class));

        bind(HttpClient.class, (HttpClient) new HttpClientBuilder().build());
        export(HttpClient.class);

        bind(HttpConfig.class, this);
        export(HttpConfig.class);
    }

    public Optional<Route> route(HttpMethod method, String path) {
        return router.find(method, path);
    }

    @SuppressWarnings("unchecked")
    public <T> ResponseWriter<T> writer(Class<T> type) {
        ResponseWriter responseWriter = writers.get(type);
        if (responseWriter != null) {
            return responseWriter;
        }
        if (Throwable.class.isAssignableFrom(type)) {
            return writers.get(Throwable.class);
        } else if (InputStream.class.isAssignableFrom(type)) {
            return writers.get(InputStream.class);
        } else {
            return writers.get(Object.class);
        }
    }

    @Override
    public <T> HttpConfig writer(Class<T> type, ResponseWriter<T> responseWriter) {
        writers.put(type, responseWriter);
        return this;
    }

    @Override
    public List<HandlerRef> handlerRefs() {
        return router.handlerRefs();
    }

    @Override
    public RequestConfig request() {
        return requestConfig;
    }

    @Override
    public <T> HttpConfig controller(Class<T> controllerClass, T instance, Module module) {
        HandlerMethodScanner handlerMethodScanner = new HandlerMethodScanner(controllerClass);
        handlerMethodScanner.scan().forEach(handlerMethod -> {
            Handler handler = new HandlerBuilder<>(controllerClass, instance, handlerMethod.method).build();
            HandlerRef handlerRef = new HandlerRef();
            handlerRef.path = handlerMethod.path;
            handlerRef.method = handlerMethod.method;
            handlerRef.httpMethod = handlerMethod.httpMethod;
            handlerRef.handler = handler;
            handlerRef.permission = module.name() + '.' + handlerMethod.permission;
            router.add(handlerRef.path, handlerRef);
        });
        return this;
    }

    @Override
    public HttpConfig interceptor(String route, Interceptor interceptor) {
        router.add(route, interceptor);
        return this;
    }

    public static class RequestConfigImpl implements RequestConfig {
        public final Binder<Request> binder = new Binder<>(null);

        @Override
        public <T> RequestConfig bind(Class<T> type, Provider<T, Request> provider) {
            binder.bind(type, provider);
            return this;
        }
    }
}
