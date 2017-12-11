package io.sited.form;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class Form {
    public final Map<Class<?>, Object> context = Maps.newHashMap();
    public final List<FieldGroup> groups;
    public final Map<String, Object> value;
    public final Map<String, GroupValidator> validators = Maps.newHashMap();
    public final GroupValidator defaultValidator = new DefaultGroupValidator();

    public Form(List<FieldGroup> groups, Map<String, Object> value) {
        this(groups, value, null);
    }

    public Form(List<FieldGroup> groups, Map<String, Object> value, Map<Class<?>, Object> context) {
        this.groups = groups;
        this.value = defaultValue();
        this.value.putAll(value);
        if (context != null) {
            this.context.putAll(context);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> defaultValue() {
        Map<String, Object> value = Maps.newHashMap();
        for (FieldGroup fieldGroup : groups) {
            if (Boolean.TRUE.equals(fieldGroup.multiple)) {
                Map<String, Object> groupValue = Maps.newHashMap();
                fieldGroup.fields.forEach(field -> groupValue.put(field.name, defaultValue(fieldGroup.name, field, field.defaultValue)));
                value.put(fieldGroup.name, Lists.newArrayList(groupValue));
            } else {
                Map<String, Object> groupValue = Maps.newHashMap();
                fieldGroup.fields.forEach(field -> groupValue.put(field.name, defaultValue(fieldGroup.name, field, field.defaultValue)));
                value.put(fieldGroup.name, groupValue);
            }
        }
        return value;
    }

    public Form setValidator(String groupName, GroupValidator validator) {
        validators.put(groupName, validator);
        return this;
    }

    private Object defaultValue(String group, Field field, Object value) {
        if (Boolean.TRUE.equals(field.multiple)) {
            return normalizeList(group, field, value);
        } else {
            return normalize(group, field, value);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> normalizeList(String group, Field field, Object value) {
        try {
            if (value == null) {
                return null;
            }
            List<T> list = Lists.newArrayList();
            for (T item : (List<T>) value) {
                list.add(normalize(field, item));
            }
            return list;
        } catch (Throwable e) {
            throw new BadRequestException(group + '.' + field.name, "form.error.type", e);
        }
    }

    private static <T> T normalize(String group, Field field, Object value) {
        try {
            if (value == null) {
                return null;
            }
            return normalize(field, value);
        } catch (Throwable e) {
            throw new BadRequestException(group + '.' + field.name, "form.error.type", e);
        }
    }

    private static <T> T normalize(Field field, Object value) {
        FieldType.FieldCodec<?> codec = field.type.codec();
        if (codec != null && value instanceof String) {
            return (T) codec.decode((String) value);
        }
        return (T) field.type.normalize(value);
    }

    @SuppressWarnings("unchecked")
    public <T> T context(Class<T> type) {
        return (T) context.get(type);
    }

    public void validate() {
        Validator.Context context = new Validator.Context();
        context.root = this;
        context.invalidFields = Lists.newArrayList();

        for (FieldGroup fieldGroup : groups) {
            if (Boolean.TRUE.equals(fieldGroup.multiple)) {
                ListGroup listGroup = listGroup(fieldGroup.name);
                for (int i = 0; i < listGroup.size(); i++) {
                    context.path = fieldGroup.name + '[' + i + ']';
                    context.current = listGroup.get(i);
                    validateGroup(listGroup.get(i), context);
                }
            } else {
                Group group = group(fieldGroup.name);
                context.path = fieldGroup.name;
                context.current = group;
                validateGroup(group, context);
            }
        }

        if (!context.invalidFields.isEmpty()) {
            throw new BadRequestException(context.invalidFields);
        }
    }

    @SuppressWarnings("unchecked")
    public ListGroup listGroup(String name) {
        FieldGroup fieldGroup = fieldGroup(name);
        if (!Boolean.TRUE.equals(fieldGroup.multiple)) {
            throw new StandardException("group must be multiple, name={}", name);
        }
        List<Map<String, Object>> values = (List<Map<String, Object>>) value.get(name);
        return new ListGroup(fieldGroup, values);
    }

    private void validateGroup(Group group, Validator.Context context) {
        GroupValidator validator = validators.get(group.fieldGroup.name);
        if (validator == null) {
            validator = defaultValidator;
        }
        validator.validate(group, context);
    }

    @SuppressWarnings("unchecked")
    public Group group(String name) {
        FieldGroup fieldGroup = fieldGroup(name);
        if (Boolean.TRUE.equals(fieldGroup.multiple)) {
            throw new StandardException("group must be not multiple, name={}", name);
        }
        Map<String, Object> values = (Map<String, Object>) value.get(name);
        return new Group(fieldGroup, values);
    }

    public FieldGroup fieldGroup(String name) {
        for (FieldGroup group : groups) {
            if (group.name.equals(name)) {
                return group;
            }
        }
        throw new StandardException("missing group, name={}", name);
    }

    public boolean hasGroup(String name) {
        for (FieldGroup group : groups) {
            if (group.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static class Group {
        public final FieldGroup fieldGroup;
        public final Map<String, Object> values;

        public Group(FieldGroup fieldGroup, Map<String, Object> values) {
            this.fieldGroup = fieldGroup;
            this.values = values;
        }

        public <T> T value(String name) {
            Optional<Field> fieldOptional = fieldGroup.field(name);
            if (!fieldOptional.isPresent()) {
                throw new StandardException("missing field, name={}", name);
            }
            Field field = fieldOptional.get();
            if (Boolean.TRUE.equals(field.multiple)) {
                throw new StandardException("field is array type, use listValue(), name={}", name);
            }
            Object value = values != null && values.containsKey(name) ? values.get(name) : field.defaultValue;
            return normalize(fieldGroup.name, field, value);
        }

        public <T> List<T> listValue(String name) {
            Optional<Field> fieldOptional = fieldGroup.field(name);
            if (!fieldOptional.isPresent()) {
                throw new StandardException("missing field, name={}", name);
            }
            Field field = fieldOptional.get();
            if (!Boolean.TRUE.equals(field.multiple)) {
                throw new StandardException("field is not array type, use value(), name={}", name);
            }
            Object value = values != null && values.containsKey(name) ? values.get(name) : field.defaultValue;
            return normalizeList(fieldGroup.name, field, value);
        }
    }

    public static class ListGroup implements Iterable<Group> {
        public final FieldGroup fieldGroup;
        public final List<Map<String, Object>> values;

        public ListGroup(FieldGroup fieldGroup, List<Map<String, Object>> values) {
            this.fieldGroup = fieldGroup;
            this.values = values;
        }

        public boolean isEmpty() {
            return values == null || values.isEmpty();
        }

        public int size() {
            return values == null ? 0 : values.size();
        }

        public Group get(int index) {
            return new Group(fieldGroup, values.get(index));
        }

        @Override
        public Iterator<Group> iterator() {
            if (values.isEmpty()) {
                return Collections.<Group>emptyList().iterator();
            }
            return values.stream().map(value -> new Group(fieldGroup, value)).collect(Collectors.toList()).iterator();
        }
    }

    static class DefaultGroupValidator implements GroupValidator {
        @Override
        public void validate(Group group, Validator.Context context) {
            if (Boolean.TRUE.equals(group.fieldGroup.optional) && group.values.isEmpty()) {
                return;
            }
            String path = context.path;
            group.fieldGroup.fields.forEach(field -> {
                if (Boolean.TRUE.equals(field.multiple)) {
                    List<Object> list = group.listValue(field.name);
                    for (int i = 0; i < list.size(); i++) {
                        context.path = path + '.' + field.name + '[' + i + ']';
                        context.options = field.options;
                        field.type.validate(list.get(i), context);
                    }
                } else {
                    context.path = path + '.' + field.name;
                    context.options = field.options;
                    field.type.validate(group.value(field.name), context);
                }
            });
        }
    }

}
