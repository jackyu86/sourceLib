package io.sited.db.impl;

import io.sited.StandardException;
import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

/**
 * @author chi
 */
public class EntityValidator {
    private final Logger logger = LoggerFactory.getLogger(EntityValidator.class);
    private final Class<?> entityClass;

    public EntityValidator(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void validate() {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new StandardException("entity missing @Entity annotation, class={}", entityClass);
        }

        boolean hasId = false;
        for (java.lang.reflect.Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                hasId = true;
                validateId(field);
            } else {
                validateField(field);
            }
        }

        if (entityClass.getDeclaredMethods().length > 0) {
            throw new StandardException("entity can't have methods, type={}", entityClass);
        }

        if (!hasId) {
            throw new StandardException("entity missing @Id field, type={}", entityClass.getCanonicalName());
        }
    }

    private void validateField(java.lang.reflect.Field field) {
        if (!field.isAnnotationPresent(Field.class)) {
            logger.warn("entity field should use @Field, type={}, field={}", field.getDeclaringClass().getCanonicalName(), field.getName());
        }

        if (!isValidFieldModifier(field)) {
            throw new StandardException("entity field must be public, type={}, field={}", entityClass.getCanonicalName(), field.getName());
        }
    }

    private void validateId(java.lang.reflect.Field field) {
        if (!isValidFieldModifier(field)) {
            throw new StandardException("entity id field must be public, type={}, field={}", entityClass.getCanonicalName(), field.getName());
        }
    }

    private boolean isValidFieldModifier(java.lang.reflect.Field field) {
        return Modifier.isPublic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers());
    }
}
