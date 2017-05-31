package io.sited.form.type;

import io.sited.form.EnumConstant;
import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

import java.util.List;

/**
 * @author chi
 */
public class EnumFieldType extends AbstractFieldType<String> {
    private final List<EnumConstant> enumConstants;

    public EnumFieldType(List<EnumConstant> enumConstants) {
        super(String.class);
        this.enumConstants = enumConstants;


        FieldOption requiredMessage = new FieldOption();
        requiredMessage.name = "enumMessage";
        requiredMessage.displayName = "Enum Message";
        requiredMessage.javaType = String.class;
        requiredMessage.type = String.class.getSimpleName();

        StringBuilder b = new StringBuilder("Value must be in [");
        for (int i = 0; i < enumConstants.size(); i++) {
            if (i != 0) {
                b.append(',');
            }
            b.append(enumConstants.get(i).value);
        }
        b.append("], but value={}");
        requiredMessage.defaultValue = b.toString();
        options.add(requiredMessage);
    }


    @Override
    public boolean validate(Object instance, Context context) {
        if (!super.validate(instance, context)) {
            return false;
        }

        String enumValue = (String) instance;
        if (enumValue == null) {
            return true;
        }

        for (EnumConstant enumConstant : enumConstants) {
            if (enumValue.equals(enumConstant.value)) {
                return true;
            }
        }

        FieldOption<String> message = option("enumMessage");
        context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.enum", message.value(context.options), enumValue));
        return false;
    }
}
