"use strict";

$.validator.setDefaults({
    highlight: function (element) {
        $(element).closest(".form-group").addClass("has-error");
    },
    unhighlight: function (element) {
        $(element).closest(".form-group").removeClass("has-error");
    },
    errorElement: "label",
    errorClass: "text-danger validation-message",
    errorPlacement: function (error, element) {
        if (element.attr("type") === "checkbox") {
            element.parents(".form-group").append(error);
            return;
        }
        if (element.parent(".input-group").length) {
            error.insertAfter(element.parent());
        } else {
            if (element.parent(".form-group").length) {
                element.parent(".form-group").append(error);
            } else {
                element.parent().append(error);
            }
        }
    },
    success: function (label, element) {
        label.html("<img src='/static/img/icon_finish.png'>");
    }
});
$.validator.addMethod("cellphone", function (value, element, params) {
    return (/^1[3578]\d{9}$/).test(value);
}, "请输入正确格式手机号");

$.validator.addMethod("phoneOrEmail", function (value, element, params) {
    return $.validator.methods.email.call(this, value, element) || $.validator.methods.cellphone.call(this, value, element);
}, "请输入正确格式手机号或邮箱");

$.validator.addMethod("password", function (value, element, params) {
    var password = $.trim(value);
    return (/[0-9a-zA-Z]{6,}/).test(password);
}, "格式错误，请输入6位以上数字或字母");

$.validator.addMethod("identification", function (value, element, params) {
    var code = $.trim(value);

    if (!code || !(/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/).test(code)) {
        return false;
    } else if (!(/1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|81|82|91/).test(code.substr(0, 2))) {
        return false;
    } else if (code.length === 18) {
        var parity = ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
        var sum = 0;
        code = code.split("");
        for (var i = 0; i < 17; i++) {
            sum += code[i] * Math.pow(2, 17 - i);
        }
        return parity[sum % 11] === code[17];

    }
    return true;

}, "请输入正确的身份证");
