package io.sited.form.type;

import com.google.common.base.Strings;
import io.sited.form.FieldOption;
import io.sited.http.exception.BadRequestException;

import java.util.regex.Pattern;

/**
 * @author chi
 */
public class StringFieldType extends AbstractFieldType<String> {
    public StringFieldType() {
        super(String.class);

        FieldOption pattern = new FieldOption();
        pattern.displayName = "Pattern";
        pattern.name = "pattern";
        pattern.type = String.class.getSimpleName();
        pattern.javaType = String.class;
        options.add(pattern);

        FieldOption patternMessage = new FieldOption();
        patternMessage.displayName = "Pattern Message";
        patternMessage.name = "patternMessage";
        patternMessage.type = String.class.getSimpleName();
        patternMessage.javaType = String.class;
        patternMessage.defaultValue = "Value must match pattern, pattern={}";
        options.add(patternMessage);

        FieldOption notEmpty = new FieldOption();
        notEmpty.displayName = "Not Empty";
        notEmpty.name = "notEmpty";
        notEmpty.type = Boolean.class.getSimpleName();
        notEmpty.javaType = Boolean.class;
        options.add(notEmpty);

        FieldOption notEmptyMessage = new FieldOption();
        notEmptyMessage.displayName = "Not Empty Message";
        notEmptyMessage.name = "notEmptyMessage";
        notEmptyMessage.type = String.class.getSimpleName();
        notEmptyMessage.javaType = String.class;
        notEmptyMessage.defaultValue = "Value must not empty";
        options.add(notEmptyMessage);
    }

    @Override
    public boolean validate(Object instance, Context context) {
        if (!super.validate(instance, context)) {
            return false;
        }

        String stringValue = (String) instance;
        FieldOption<Boolean> notEmptyOption = option("notEmpty");

        if (Boolean.TRUE.equals(notEmptyOption.value(context.options)) && Strings.isNullOrEmpty(stringValue)) {
            FieldOption<String> message = option("notEmptyMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.notEmpty", message.value(context.options)));
            return false;
        }

        FieldOption<String> regexOption = option("pattern");
        String regex = regexOption.value(context.options);

        if (regex != null && !Pattern.compile(regex).matcher(stringValue).matches()) {
            FieldOption<String> message = option("patternMessage");
            context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.pattern", message.value(context.options), regex));
            return false;
        }
        return true;
    }
}
