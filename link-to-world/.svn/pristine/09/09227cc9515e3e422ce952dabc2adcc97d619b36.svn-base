"use strict";

/* global DynamicFieldBuilders*/
DynamicFieldBuilders.phone = function (fieldAttribute, value) {

    var field = DynamicFieldBuilders.string(fieldAttribute, value);
    if (field.editable()) {
        var tempValidator = field.validator;
        field.element.querySelector("input").setAttribute("maxlength", 11);

        field.validator = function (inputValue) {
            var validateResult = tempValidator(inputValue);
            if (validateResult) {
                return validateResult;
            }
            var regex = /^1[3578]\d{9}$/;
            // todo:message should be store in form
            if (!regex.test(inputValue)) {
                if (field.attribute.options.phone !== undefined) {
                    return field.attribute.options.phone;
                }
                return "手机号码格式不正确";
            }
            return null;
        };
    }

    return field;
};
