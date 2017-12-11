package com.caej.product.service.field;

import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class PostalCodeFieldType extends AbstractFieldType<String> {
    private static final Pattern ZIP_CODE = Pattern.compile("[0-9]{6}");

    public PostalCodeFieldType() {
        super(String.class);

        FieldOption<String> message = new FieldOption<>();
        message.name = "postalCodeMessage";
        message.displayName = "PostalCode Message";
        message.javaType = String.class;
        message.type = String.class.getSimpleName();
        message.defaultValue = "请输入正确邮编";
        options.add(message);
    }

    @Override
    public String name() {
        return "PostalCode";
    }

    @Override
    public boolean validate(Object value, Context context) {
        String zipCode = (String) value;
        if (zipCode != null && !ZIP_CODE.matcher(zipCode).matches()) {
            FieldOption<String> message = option("postalCodeCodeMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, message.value(context.options)));
            return false;
        }
        return true;
    }
}
