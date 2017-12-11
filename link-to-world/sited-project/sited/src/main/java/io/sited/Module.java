package io.sited;

import com.google.common.collect.Lists;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chi
 */
public abstract class Module implements BeanFactory {
    final List<Class<? extends Module>> dependencies = Lists.newArrayList();
    final List<Runnable> shutdownHooks = Lists.newArrayList();
    final List<Runnable> startupHooks = Lists.newArrayList();

    private final String name;
    private final String description;
    private final Class<? extends Module> as;
    private Binder<Module> binder;
    private Site site;

    protected Module() {
        this(Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    protected Module(List<Class<? extends Module>> dependencies) {
        if (getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo module = getClass().getDeclaredAnnotation(ModuleInfo.class);

            name = module.name();
            description = module.description();

            as = isOverrideModule() ? (Class<? extends Module>) getClass().getSuperclass() : getClass();

            if (module.require().length > 0) {
                this.dependencies.addAll(Arrays.asList(module.require()));
            }
        } else {
            name = getClass().toString();
            description = "";
            as = getClass();
        }
        this.dependencies.addAll(dependencies);
    }

    private boolean isOverrideModule() {
        Class<?> superclass = getClass().getSuperclass();
        return Module.class.isAssignableFrom(superclass) && !Modifier.isAbstract(superclass.getModifiers());
    }

    @SuppressWarnings("unchecked")
    void configure(Site site, Binder<Module> binder) throws Exception {
        if (this.binder != null) {
            throw new StandardException("module configured, module={}", getClass().getCanonicalName());
        }

        this.site = site;
        this.binder = new Binder<>(binder);
        bind(Site.class, site);

        configure();
    }

    protected abstract void configure() throws Exception;

    public <T> T require(Type type) {
        return binder.<T>provider(type).get(this);
    }

    public <T> T require(Class<T> type) {
        return require((Type) type);
    }

    public <T> T bind(Class<T> type) {
        return binder.bind(type);
    }

    public <T> T bind(Type type, T instance) {
        return binder.bind(type, instance);
    }

    public <T> void bind(Type type, Provider<T, Module> provider) {
        binder.bind(type, provider);
    }

    @Override
    public <T> T bind(T instance) {
        return binder.bind(instance);
    }

    @Override
    public void export(Type type) {
        binder.export(type);
    }

    @Override
    public boolean contains(Type type) {
        return binder.contains(type);
    }

    @Override
    public List<Type> exportedTypes() {
        return binder.exportedTypes();
    }

    protected Site site() {
        return site;
    }

    protected Binder<Module> binder() {
        return binder;
    }

    protected File dir() {
        return site.dir();
    }

    protected File dir(String folder) {
        return new File(dir(), folder);
    }


    protected void onShutdown(Runnable shutdownHook) {
        shutdownHooks.add(shutdownHook);
    }

    protected void onStartup(Runnable startupHook) {
        startupHooks.add(startupHook);
    }

    @SuppressWarnings("unchecked")
    protected <T> T options(Class<T> optionType) {
        return site.options(name(), optionType);
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Class<? extends Module> as() {
        return as;
    }
}
