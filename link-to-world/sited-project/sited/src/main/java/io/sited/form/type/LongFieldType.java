package io.sited.form.type;

import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class LongFieldType extends AbstractFieldType<Long> {
    public LongFieldType() {
        super(Long.class);

        FieldOption min = new FieldOption();
        min.name = "min";
        min.displayName = "Min";
        min.javaType = Long.class;
        min.type = Long.class.getSimpleName();
        options.add(min);

        FieldOption minMessage = new FieldOption();
        minMessage.name = "minMessage";
        minMessage.displayName = "Min Message";
        minMessage.javaType = String.class;
        minMessage.type = String.class.getSimpleName();
        minMessage.defaultValue = "Value must be smaller than {}";
        options.add(minMessage);

        FieldOption max = new FieldOption();
        max.name = "min";
        max.displayName = "Max";
        max.javaType = Long.class;
        max.type = Long.class.getSimpleName();
        options.add(max);

        FieldOption maxMessage = new FieldOption();
        maxMessage.name = "maxMessage";
        maxMessage.displayName = "Max Message";
        maxMessage.javaType = String.class;
        maxMessage.type = String.class.getSimpleName();
        maxMessage.defaultValue = "Value must be larger than {}";
        options.add(maxMessage);
    }

    @Override
    public boolean validate(Object instance, Context context) {
        if (!super.validate(instance, context)) {
            return false;
        }

        Long longValue = (Long) instance;

        FieldOption<Long> minOption = option("min");
        Long min = minOption.value(context.options);
        if (min != null && (longValue == null || longValue < min)) {
            FieldOption<String> message = option("minMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", message.value(context.options), min));
            return false;
        }

        FieldOption<Long> maxOption = option("max");
        Long max = maxOption.value(context.options);
        if (max != null && (longValue == null || longValue > max)) {
            FieldOption<String> message = option("maxMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", message.value(context.options), max));
            return false;
        }
        return true;
    }
}
