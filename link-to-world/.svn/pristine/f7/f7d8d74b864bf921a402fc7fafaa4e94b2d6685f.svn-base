package io.sited.validator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.util.Types;
import io.sited.validator.Validator;
import io.sited.validator.ValidatorModule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class ValidatorBuilder {
    private static final Validator NONE = new NoneValidator();
    private final Class<?> type;
    private final ValidatorModule validatorConfig;

    public ValidatorBuilder(Class<?> type, ValidatorModule validatorConfig) {
        this.type = type;
        this.validatorConfig = validatorConfig;
    }

    public Validator build() {
        if (isValueClass(type)) {
            return NONE;
        }
        return createObjectValidator(null, type).orElse(NONE);
    }

    private Optional<Validator> createObjectValidator(String parent, Class<?> type) {
        Map<Field, List<Validator>> validators = Maps.newHashMap();
        for (Field field : type.getDeclaredFields()) {
            List<Validator> children = createFieldValidator(parent, field);
            if (!children.isEmpty()) {
                validators.put(field, children);
            }
        }
        if (validators.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ObjectValidator(parent, validators));
    }

    private List<Validator> createFieldValidator(String parent, Field field) {
        List<Validator> validators = Lists.newArrayList();
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            Optional<Validator> validator = validatorConfig.get(annotation.annotationType());
            if (validator.isPresent()) {
                validators.add(new FieldValidator(field, validator.get(), options(annotation)));
            }
        }
        Type fieldType = field.getGenericType();
        if (Types.isGenericList(fieldType)) {
            Class<?> valueClass = Types.listValueClass(fieldType);
            if (!isValueClass(valueClass)) {
                Optional<Validator> child = createObjectValidator(null, valueClass);
                child.ifPresent(validator -> validators.add(new ListValidator(parent == null ? field.getName() : parent + '.' + field.getName(), validator)));
            }
        } else if (Types.isMap(fieldType)) {
            Class<?> valueClass = Types.mapValueClass(fieldType);
            if (!isValueClass(valueClass)) {
                Optional<Validator> child = createObjectValidator(null, valueClass);
                child.ifPresent(validator -> validators.add(new MapValidator(parent == null ? field.getName() : parent + '.' + field.getName(), validator)));
            }
        } else if (fieldType instanceof Class && !isValueClass((Class) fieldType)) {
            Optional<Validator> child = createObjectValidator(parent == null ? field.getName() : parent + '.' + field.getName(), (Class) fieldType);
            child.ifPresent(validators::add);
        }
        return validators;
    }

    private boolean isValueClass(Class<?> fieldClass) {
        return String.class.equals(fieldClass)
            || Integer.class.equals(fieldClass)
            || Boolean.class.equals(fieldClass)
            || Long.class.equals(fieldClass)
            || Double.class.equals(fieldClass)
            || BigDecimal.class.equals(fieldClass)
            || LocalDate.class.equals(fieldClass)
            || LocalDateTime.class.equals(fieldClass)
            || ZonedDateTime.class.equals(fieldClass)
            || Instant.class.equals(fieldClass)
            || Enum.class.isAssignableFrom(fieldClass)
            || "org.bson.types.ObjectId".equals(fieldClass.getCanonicalName());
    }

    private Map<String, Object> options(Annotation annotation) {
        Map<String, Object> params = Maps.newHashMap();
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            try {
                params.put(method.getName(), method.invoke(annotation));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new StandardException("failed to invoke method, class={}, method={}", annotation.getClass(), method.getName(), e);
            }
        }
        return params;
    }

    private static class NoneValidator implements Validator {
        @Override
        public boolean validate(Object instance, Context context) {
            return true;
        }
    }
}
