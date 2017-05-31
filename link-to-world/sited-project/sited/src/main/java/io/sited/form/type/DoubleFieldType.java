package io.sited.form.type;

import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class DoubleFieldType extends AbstractFieldType<Double> {
    public DoubleFieldType() {
        super(Double.class);

        FieldOption min = new FieldOption();
        min.name = "min";
        min.displayName = "Min";
        min.javaType = Double.class;
        min.type = Double.class.getSimpleName();
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
        max.javaType = Double.class;
        max.type = Double.class.getSimpleName();
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

        Double doubleValue = (Double) instance;

        FieldOption<Double> minOption = option("min");
        Double min = minOption.value(context.options);
        if (min != null && (doubleValue == null || doubleValue < min)) {
            FieldOption<String> message = option("minMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", message.value(context.options), min));
            return false;
        }

        FieldOption<Double> maxOption = option("max");
        Double max = maxOption.value(context.options);
        if (max != null && (doubleValue == null || doubleValue > max)) {
            FieldOption<String> message = option("maxMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", message.value(context.options), max));
            return false;
        }
        return true;
    }
}
