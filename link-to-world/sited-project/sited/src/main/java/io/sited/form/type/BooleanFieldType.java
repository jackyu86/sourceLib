package io.sited.form.type;

import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class BooleanFieldType extends AbstractFieldType<Boolean> {
    public BooleanFieldType() {
        super(Boolean.class);

        FieldOption trueOnly = new FieldOption();
        trueOnly.name = "trueOnly";
        trueOnly.displayName = "TrueOnly";
        trueOnly.javaType = Boolean.class;
        trueOnly.type = Boolean.class.getSimpleName();
        options.add(trueOnly);

        FieldOption trueOnlyMessage = new FieldOption();
        trueOnlyMessage.name = "trueOnlyMessage";
        trueOnlyMessage.displayName = "TrueOnly Message";
        trueOnlyMessage.javaType = String.class;
        trueOnlyMessage.type = String.class.getSimpleName();
        trueOnlyMessage.defaultValue = "Value must be be true";
        options.add(trueOnlyMessage);

        FieldOption falseOnly = new FieldOption();
        falseOnly.name = "falseOnly";
        falseOnly.displayName = "FalseOnly";
        falseOnly.javaType = Boolean.class;
        falseOnly.type = Boolean.class.getSimpleName();
        options.add(falseOnly);

        FieldOption falseOnlyMessage = new FieldOption();
        falseOnlyMessage.name = "falseOnlyMessage";
        falseOnlyMessage.displayName = "FalseOnly Message";
        falseOnlyMessage.javaType = String.class;
        falseOnlyMessage.type = String.class.getSimpleName();
        falseOnlyMessage.defaultValue = "Value must be be false";

        options.add(falseOnlyMessage);
    }

    @Override
    public boolean validate(Object value, Context context) {
        if (!super.validate(value, context)) {
            return false;
        }

        Boolean booleanValue = (Boolean) value;
        FieldOption<Boolean> trueOnlyOption = option("trueOnly");
        Boolean trueOnly = trueOnlyOption.value(context.options);
        if (Boolean.TRUE.equals(trueOnly) && !Boolean.TRUE.equals(booleanValue)) {
            FieldOption<String> message = option("trueOnlyMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.trueOnly", message.value(context.options), trueOnly));
            return false;
        }

        FieldOption<Boolean> falseOnlyOption = option("falseOnly");
        Boolean falseOnly = falseOnlyOption.value(context.options);
        if (Boolean.TRUE.equals(falseOnly) && !Boolean.FALSE.equals(booleanValue)) {
            FieldOption<String> message = option("falseOnlyMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.falseOnly", message.value(context.options), falseOnly));
            return false;
        }

        return true;
    }
}
