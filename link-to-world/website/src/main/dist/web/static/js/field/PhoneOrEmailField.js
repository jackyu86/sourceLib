"use strict";

/* global DynamicFieldBuilders*/
DynamicFieldBuilders.phoneoremail = function (fieldAttribute, value) {

    var field = DynamicFieldBuilders.string(fieldAttribute, value);
    if (field.editable()) {
        var tempValidator = field.validator;
        field.validator = function (inputValue) {
            var validateResult = tempValidator(inputValue);
            if (validateResult) {
                return validateResult;
            }
            var emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
            var phoneRegex = /^1[3578]\d{9}$/;
            // todo:message should be store in form
            if (!emailRegex.test(inputValue) && !phoneRegex.test(inputValue)) {
                if (field.attribute.options.phoneOrEmail !== undefined) {
                    return field.attribute.options.phoneOrEmail;
                }
                return "请输入正确格式手机号或邮箱";
            }
            return null;
        };
    }

    return field;
};
