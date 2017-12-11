package io.sited.form.type;

import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class IntegerFieldType extends AbstractFieldType<Integer> {
    public IntegerFieldType() {
        super(Integer.class);

        FieldOption min = new FieldOption();
        min.name = "min";
        min.displayName = "Min";
        min.javaType = Integer.class;
        min.type = Integer.class.getSimpleName();
        options.add(min);

        FieldOption minMessage = new FieldOption();
        minMessage.name = "minMessage";
        minMessage.displayName = "Min Message";
        minMessage.javaType = String.class;
        minMessage.type = String.class.getSimpleName();
        minMessage.defaultValue = "Value must be smaller than {}";
        options.add(minMessage);

        FieldOption max = new FieldOption();
        max.name = "max";
        max.displayName = "Max";
        max.javaType = Integer.class;
        max.type = Integer.class.getSimpleName();
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

        Integer intValue = (Integer) instance;

        FieldOption<Integer> minOption = option("min");
        Integer min = minOption.value(context.options);
        if (min != null && (intValue == null || intValue < min)) {
            FieldOption<String> message = option("minMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", message.value(context.options)));
            return false;
        }

        FieldOption<Integer> maxOption = option("max");
        Integer max = maxOption.value(context.options);
        if (max != null && (intValue == null || intValue > max)) {
            FieldOption<String> message = option("maxMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", message.value(context.options)));
            return false;
        }
        return true;
    }
}
