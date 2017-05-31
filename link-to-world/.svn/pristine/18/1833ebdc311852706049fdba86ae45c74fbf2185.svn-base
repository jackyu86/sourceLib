package io.sited;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class Binder<K> implements BeanFactory {
    private final Logger logger = LoggerFactory.getLogger(Binder.class);

    private final Map<Type, Provider<?, ?>> bindings = Maps.newHashMap();
    private final Binder<K> parent;
    private final List<Type> exportedTypes = Lists.newArrayList();

    public Binder(Binder<K> parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    public <T> Provider<T, K> provider(Type type) {
        if (!bindings.containsKey(type)) {
            if (parent == null) {
                throw new StandardException("missing binding, type={}", type);
            }
            return parent.provider(type);
        }
        return (Provider<T, K>) bindings.get(type);
    }

    public <T> void bind(Type type, Provider<T, K> provider) {
        if (contains(type)) {
            logger.info("override binding {}, {}->{}", type, provider.getClass().getCanonicalName(),
                bindings.get(type).getClass().getCanonicalName());
        }
        bindings.put(type, provider);
    }

    @Override
    public <T> T bind(Class<T> type) {
        T instance = create(type);
        bind(type, context -> instance);
        return instance;
    }

    @Override
    public <T> T bind(Type type, T instance) {
        bind(type, context -> instance);
        return instance;
    }

    @Override
    public <T> T require(Class<T> type) {
        return require((Type) type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T require(Type type) {
        if (!bindings.containsKey(type)) {
            if (parent == null) {
                throw new StandardException("missing binding, type={}", type);
            }
            return parent.require(type);
        }
        return (T) bindings.get(type).get(null);
    }

    @Override
    public boolean contains(Type type) {
        return bindings.containsKey(type);
    }

    @Override
    public void export(Type type) {
        if (!bindings.containsKey(type)) {
            throw new StandardException("missing binding, type={}", type);
        }

        if (parent == null) {
            throw new StandardException("missing parent binder");
        }

        parent.bindings.put(type, provider(type));
        exportedTypes.add(type);
    }

    @Override
    public List<Type> exportedTypes() {
        return exportedTypes;
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Type type) {
        if (!(type instanceof Class)) {
            throw new StandardException("invalid instance type, type={}", type);
        }

        Class<T> instanceClass = (Class<T>) type;

        if (instanceClass.isInterface() || Modifier.isAbstract(instanceClass.getModifiers())) {
            throw new StandardException("invalid instance type, type={}", type);
        }

        try {
            T instance = construct(instanceClass);
            inject(instance);
            return instance;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | RuntimeException e) {
            throw new Error("failed to build bean, instanceClass=" + type + ", error=" + e.getMessage(), e);
        }
    }

    private <T> T construct(Class<T> instanceClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> targetConstructor = null;

        for (Constructor<?> constructor : instanceClass.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (targetConstructor != null)
                    throw new StandardException("only one constructor can have @Inject, previous={}, current={}", targetConstructor, constructor);
                targetConstructor = constructor;
            }
        }
        try {
            if (targetConstructor == null) targetConstructor = instanceClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new StandardException("default constructor is required, class={}", instanceClass, e);
        }

        Object[] params = lookupParams(targetConstructor);

        return instanceClass.cast(targetConstructor.newInstance(params));
    }

    private <T> void inject(T instance) throws IllegalAccessException, InvocationTargetException {
        Class visitorType = instance.getClass();
        while (!visitorType.equals(Object.class)) {
            for (Field field : visitorType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    makeAccessible(field);

                    field.set(instance, lookupValue(field));
                }
            }

            for (Method method : visitorType.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inject.class)) {
                    makeAccessible(method);

                    Object[] params = lookupParams(method);
                    method.invoke(instance, params);
                }
            }
            visitorType = visitorType.getSuperclass();
        }
    }

    @SuppressWarnings("unchecked")
    private Object lookupValue(Field field) {
        return require(field.getGenericType());
    }

    @SuppressWarnings("unchecked")
    private Object[] lookupParams(Executable method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = require(paramTypes[i]);
        }
        return params;
    }

    private void makeAccessible(Field field) {
        AccessController.doPrivileged((PrivilegedAction<Field>) () -> {
            field.setAccessible(true);
            return field;
        });
    }

    private void makeAccessible(Method method) {
        AccessController.doPrivileged((PrivilegedAction<Method>) () -> {
            method.setAccessible(true);
            return method;
        });
    }
}
