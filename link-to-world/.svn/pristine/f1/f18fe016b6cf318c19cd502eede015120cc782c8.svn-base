package io.sited;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import io.sited.http.HttpModule;
import io.sited.http.Route;
import io.sited.http.exception.NotFoundException;
import io.sited.http.impl.InvocationImpl;
import io.sited.http.impl.RequestImpl;
import io.sited.util.Graph;
import io.sited.util.JSON;
import io.sited.util.JSONModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class Site {
    private final Logger logger = LoggerFactory.getLogger(Site.class);
    private final Graph<Class<? extends Module>> graph = new Graph<>();
    private final Map<Class<? extends Module>, Module> modules = Maps.newHashMap();
    private final String host;
    private final File dir;
    private final Map<String, Map<String, Object>> profile;
    private final SiteOptions siteOptions;
    private final HttpModule httpModule;

    public Site(String host, File dir, Map<String, Map<String, Object>> profile) {
        this.host = host;
        this.dir = dir;
        this.profile = profile;
        siteOptions = options("site", SiteOptions.class);
        httpModule = new HttpModule();
        install(httpModule);
    }

    public Site() {
        Sited.Application app = Sited.app();
        this.host = "localhost";
        this.dir = app.dir();
        if (this.dir.equals(new File("./src/main/dist/"))) {
            this.profile = profile(new File("./src/main/resources/site.yml"));
        } else {
            File file = new File(this.dir, "site.yml");
            if (file.exists()) {
                this.profile = profile(file);
            } else {
                this.profile = profile("site.yml");
            }
        }
        siteOptions = options("site", SiteOptions.class);
        httpModule = new HttpModule();
        install(httpModule);
    }

    public Object handle(RequestImpl request) throws Exception {
        Optional<Route> routeOptional = httpModule.route(request.method(), request.path());
        if (!routeOptional.isPresent()) {
            throw new NotFoundException(request.path());
        }
        Route route = routeOptional.get();
        request.pathParams.putAll(route.pathParams);
        request.context().put(Route.class, route);
        InvocationImpl invocation = new InvocationImpl(route.handler, route.method, route.interceptors, request);
        return invocation.proceed();
    }

    @SuppressWarnings("unchecked")
    protected static Map<String, Map<String, Object>> profile(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new JSONModule());
        try (InputStream inputStream = Resources.getResource(path).openStream()) {
            if (inputStream == null) {
                throw new StandardException("missing profile, path={}", path);
            }
            return mapper.readValue(CharStreams.toString(new InputStreamReader(inputStream)), Map.class);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected static Map<String, Map<String, Object>> profile(File file) {
        if (!file.exists()) {
            throw new StandardException("missing profile, file={}", file.getAbsolutePath());
        }
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new JSONModule());
        try {
            return mapper.readValue(file, Map.class);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final <T> T options(String key, Class<T> type) {
        Map<String, Object> options = defaultOptions(type);
        if (profile.containsKey(key)) {
            options.putAll(profile.get(key));
        }
        return JSON.convert(options, type);
    }

    private <T> Map defaultOptions(Class<T> type) {
        try {
            return JSON.convert(type.getDeclaredConstructor().newInstance(), Map.class);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new StandardException(e);
        }
    }

    public File dir() {
        return dir;
    }

    public Charset charset() {
        return siteOptions.charset;
    }

    public Locale locale() {
        return siteOptions.locale;
    }

    public String host() {
        return host;
    }

    public String build() {
        return siteOptions.build;
    }


    public String name() {
        return siteOptions.name;
    }

    public String description() {
        return siteOptions.description;
    }

    public final Site install(Class<? extends Module> moduleClass) {
        try {
            Module module = moduleClass.getDeclaredConstructor().newInstance();
            return install(module);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new StandardException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public final Site install(Module module) {
        Class<? extends Module> moduleClass = module.as();
        if (isInstalled(moduleClass)) {
            if (!module.as().equals(module.getClass())) {
                modules.put(moduleClass, module);
            } else {
                return this;
            }
        } else {
            modules.put(moduleClass, module);
        }

        Deque<Class<? extends Module>> dependencies = new LinkedList<>();
        dependencies.addAll(module.dependencies);

        while (!dependencies.isEmpty()) {
            Class<? extends Module> dependency = dependencies.poll();
            if (!modules.containsKey(dependency)) {
                install(dependency);
            }
        }
        return this;
    }

    private boolean isInstalled(Class<? extends Module> moduleClass) {
        return modules.containsKey(moduleClass);
    }


    @SuppressWarnings("unchecked")
    public <T extends Module> T module(Class<T> moduleClass) {
        if (!modules.containsKey(moduleClass)) {
            throw new StandardException("missing module, type={}", moduleClass);
        }
        return (T) modules.get(moduleClass);
    }

    public void start() {
        for (Map.Entry<Class<? extends Module>, Module> entry : modules.entrySet()) {
            List<Class<? extends Module>> dependencies = new ArrayList<>();
            dependencies.addAll(entry.getValue().dependencies);
            graph.add(entry.getKey(), dependencies);
        }

        graph.forEach(moduleClass -> {
            Module module = module(moduleClass);
            try {
                module.configure(this, binder(module));
            } catch (Exception e) {
                throw new StandardException(e);
            }
        });

        graph.forEach(moduleClass -> {
            Module module = module(moduleClass);
            module.startupHooks.forEach(hook -> {
                logger.info("run startup hook, site={}, module={}, hook={}", host(), module.getClass(), hook);
                hook.run();
            });
        });
    }

    private Binder<Module> binder(Module module) {
        List<Class<? extends Module>> dependencies = graph.dependencies(module.as());
        Binder<Module> binder = new Binder<>(null);
        dependencies.forEach(moduleClass -> {
            Module dependency = module(moduleClass);
            dependency.exportedTypes().forEach(type -> binder.bind(type, dependency.binder().provider(type)));
        });
        return binder;
    }

    public void stop() {
        for (final Class<? extends Module> moduleClass : Lists.reverse(Lists.newArrayList(graph))) {
            Module module = modules.get(moduleClass);
            module.shutdownHooks.forEach(hook -> {
                logger.info("run shutdown hook, site={}, module={}, hook={}", host(), module.getClass(), hook);
                hook.run();
            });
        }
    }

    public static class SiteOptions {
        public String name = "localhost";
        public String description;
        public String build = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS"));
        public Charset charset = Charsets.UTF_8;
        public Locale locale = Locale.getDefault();
    }
}
