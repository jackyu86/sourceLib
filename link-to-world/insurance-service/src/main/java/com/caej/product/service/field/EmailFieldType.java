package com.caej.product.service.field;

import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class EmailFieldType extends AbstractFieldType<String> {
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

    public EmailFieldType() {
        super(String.class);

        FieldOption<String> message = new FieldOption<>();
        message.name = "email.message";
        message.displayName = "Email Message";
        message.javaType = String.class;
        message.type = String.class.getSimpleName();
        message.defaultValue = "请输入邮箱";
        options.add(message);
    }

    @Override
    public String name() {
        return "Email";
    }

    @Override
    public boolean validate(Object value, Context context) {
        String email = (String) value;
        if (email != null && !EMAIL.matcher(email).matches()) {
            FieldOption<String> message = option("email.message");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, message.value(context.options)));
            return false;
        }
        return true;
    }
}
