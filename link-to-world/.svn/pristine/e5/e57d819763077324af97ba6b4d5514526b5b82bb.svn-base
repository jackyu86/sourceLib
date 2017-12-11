package com.caej.product.service.field;

import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class PhoneFieldType extends AbstractFieldType<String> {
    private static final Pattern PHONE = Pattern.compile("^1[3578]{1}\\d{9}$");

    public PhoneFieldType() {
        super(String.class);

        FieldOption<String> message = new FieldOption<>();
        message.name = "phone.message";
        message.displayName = "Phone Message";
        message.javaType = String.class;
        message.type = String.class.getSimpleName();
        message.defaultValue = "请输入手机号码";
        options.add(message);
    }

    @Override
    public boolean validate(Object value, Context context) {
        String phone = (String) value;
        if (phone != null && !PHONE.matcher(phone).matches()) {
            FieldOption<String> message = option("phone.message");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, message.value(context.options)));
            return false;
        }
        return true;
    }

    @Override
    public String name() {
        return "Phone";
    }
}
