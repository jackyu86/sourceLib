"use strict";

/* global DynamicFieldBuilders*/
DynamicFieldBuilders.email = function (fieldAttribute, value) {

    var field = DynamicFieldBuilders.string(fieldAttribute, value);
    if (field.editable()) {
        var tempValidator = field.validator;
        field.validator = function (inputValue) {
            var validateResult = tempValidator(inputValue);
            if (validateResult) {
                return validateResult;
            }
            if (!fieldAttribute.options.notNull) {
                return null;
            }
            var regex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
            // todo:message should be store in form
            if (!regex.test(inputValue)) {
                if (field.attribute.options.email !== undefined) {
                    return field.attribute.options.email;
                }
                return "邮箱格式不正确";
            }
            return null;
        };
    }

    return field;
};
