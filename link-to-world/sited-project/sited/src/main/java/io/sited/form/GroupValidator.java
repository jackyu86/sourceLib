package io.sited.form;

import io.sited.validator.Validator;

/**
 * @author chi
 */
public interface GroupValidator {
    void validate(Form.Group group, Validator.Context context);
}
