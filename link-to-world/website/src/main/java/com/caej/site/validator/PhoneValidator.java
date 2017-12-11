package com.caej.site.validator;

import com.google.common.base.Strings;
import io.sited.http.exception.BadRequestException;
import io.sited.validator.Validator;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class PhoneValidator implements Validator {
    private static final Pattern PHONE = Pattern.compile("^1[3578]{1}\\d{9}$");

    @Override
    public boolean validate(Object instance, Context context) {
        String phone = (String) instance;
        if (Strings.isNullOrEmpty(phone) || !PHONE.matcher(phone).matches()) {
            context.invalidFields.add(BadRequestException.InvalidField.of("phone", "validator.invalidPhone", "请输入手机号码"));
            return false;
        }
        return true;
    }
}
