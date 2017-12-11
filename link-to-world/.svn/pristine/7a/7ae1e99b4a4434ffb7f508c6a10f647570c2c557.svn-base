package io.sited.form.type;

import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.form.FieldOption;
import io.sited.form.FieldType;
import io.sited.http.exception.BadRequestException;
import io.sited.util.JSON;

import java.util.List;

/**
 * @author chi
 */
public abstract class AbstractFieldType<T> implements FieldType<T> {
    protected final Class<T> javaType;
    protected final List<FieldOption> options = Lists.newArrayList();
    protected final List<String> displayAs = Lists.newArrayList();

    public AbstractFieldType(Class<T> javaType) {
        this.javaType = javaType;

        FieldOption<Boolean> notNull = new FieldOption<>();
        notNull.name = "notNull";
        notNull.displayName = "Not Null";
        notNull.javaType = Boolean.class;
        notNull.type = Boolean.class.getSimpleName();
        notNull.defaultValue = false;
        options.add(notNull);

        FieldOption<String> notNullMessage = new FieldOption<>();
        notNullMessage.name = "notNullMessage";
        notNullMessage.displayName = "Not Null Message";
        notNullMessage.javaType = String.class;
        notNullMessage.type = String.class.getSimpleName();
        notNullMessage.defaultValue = "Value must not be null";
        options.add(notNullMessage);
    }

    @Override
    public String name() {
        return javaType.getSimpleName();
    }

    @Override
    public Class<T> javaType() {
        return javaType;
    }

    @Override
    public List<FieldOption> options() {
        return options;
    }

    @Override
    public List<String> displayAs() {
        return displayAs;
    }

    @Override
    public FieldCodec<T> codec() {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T normalize(Object value) {
        if (value == null) {
            return null;
        }
        if (javaType.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return JSON.convert(value, javaType);
    }

    @SuppressWarnings("unchecked")
    protected <F> FieldOption<F> option(String optionName) {
        for (FieldOption option : options) {
            if (option.name.equals(optionName)) {
                return option;
            }
        }
        throw new StandardException("missing option, name={}", optionName);
    }

    @Override
    public boolean validate(Object instance, Context context) {
        FieldOption<Boolean> notNull = option("notNull");
        if (Boolean.TRUE.equals(notNull.value(context.options)) && instance == null) {
            FieldOption<String> message = option("notNullMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.notNull", message.value(context.options)));
            return false;
        }
        return true;
    }
}
