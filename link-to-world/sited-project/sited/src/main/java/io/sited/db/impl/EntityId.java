package io.sited.db.impl;

import io.sited.StandardException;
import io.sited.db.Id;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author chi
 */
public final class EntityId<T> {
    private final Field field;
    private final boolean autoGenerated;

    private EntityId(Field field, boolean autoGenerated) {
        this.field = field;
        this.autoGenerated = autoGenerated;

        AccessController.doPrivileged((PrivilegedAction<Field>) () -> {
            field.setAccessible(true);
            return field;
        });
    }

    public static <T> EntityId<T> of(Class<T> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            Id id = field.getDeclaredAnnotation(Id.class);
            if (id != null) {
                return new EntityId<>(field, id.autoGenerated());
            }
        }
        throw new StandardException("invalid entity class, missing @Id field, type={}", entityClass.getCanonicalName());
    }

    public Object get(T entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new StandardException(e);
        }
    }

    public void set(T entity, Object id) {
        try {
            field.set(entity, id);
        } catch (IllegalAccessException e) {
            throw new StandardException(e);
        }
    }

    public String name() {
        return field.getName();
    }

    public boolean isAutoGenerated() {
        return autoGenerated;
    }
}
