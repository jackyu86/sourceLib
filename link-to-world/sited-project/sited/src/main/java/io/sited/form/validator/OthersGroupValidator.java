package io.sited.form.validator;

import io.sited.form.Form;
import io.sited.form.GroupValidator;
import io.sited.validator.Validator;

import java.util.List;

/**
 * @author miller
 */
public class OthersGroupValidator implements GroupValidator {
    @Override
    public void validate(Form.Group group, Validator.Context context) {
        if (Boolean.TRUE.equals(group.fieldGroup.optional)) {
            return;
        }
        String path = context.path;
        group.fieldGroup.fields.forEach(field -> {
            if (Boolean.TRUE.equals(field.multiple)) {
                List<Object> list = group.listValue(field.name);
                for (int i = 0; i < list.size(); i++) {
                    context.path = path + '.' + field.name + '[' + i + ']';
                    context.options = field.options;
                    field.type.validate(list.get(i), context);
                }
            } else {
                context.path = path + '.' + field.name;
                context.options = field.options;
                field.type.validate(group.value(field.name), context);
            }
        });
    }
}
