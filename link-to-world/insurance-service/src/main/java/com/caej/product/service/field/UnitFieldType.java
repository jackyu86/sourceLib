package com.caej.product.service.field;

import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class UnitFieldType extends AbstractFieldType<Integer> {
    public UnitFieldType() {
        super(Integer.class);

        FieldOption min = new FieldOption();
        min.name = "min";
        min.displayName = "Min";
        min.javaType = Integer.class;
        min.type = Integer.class.getSimpleName();
        options.add(min);

        FieldOption minMessage = new FieldOption();
        minMessage.name = "min.message";
        minMessage.displayName = "Min Message";
        minMessage.javaType = String.class;
        minMessage.type = String.class.getSimpleName();
        minMessage.defaultValue = "最小份数{}";
        options.add(minMessage);

        FieldOption max = new FieldOption();
        max.name = "max";
        max.displayName = "Max";
        max.javaType = Integer.class;
        max.type = Integer.class.getSimpleName();
        options.add(max);

        FieldOption maxMessage = new FieldOption();
        maxMessage.name = "max.message";
        maxMessage.displayName = "Max Message";
        maxMessage.javaType = String.class;
        maxMessage.type = String.class.getSimpleName();
        maxMessage.defaultValue = "最大份数{}";
        options.add(maxMessage);
    }

    @Override
    public String name() {
        return "Unit";
    }

    @Override
    public boolean validate(Object value, Context context) {
        if (!super.validate(value, context)) {
            return false;
        }

        Integer intValue = (Integer) value;

        FieldOption<Integer> minOption = option("min");
        Integer min = minOption.value(context.options);
        if (min != null && (intValue == null || intValue < min)) {
            FieldOption<String> message = option("min.message");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.min", message.value(context.options)));
            return false;
        }

        FieldOption<Integer> maxOption = option("max");
        Integer max = maxOption.value(context.options);
        if (max != null && (intValue == null || intValue > max)) {
            FieldOption<String> message = option("max.message");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.max", message.value(context.options)));
            return false;
        }
        return true;
    }
}
