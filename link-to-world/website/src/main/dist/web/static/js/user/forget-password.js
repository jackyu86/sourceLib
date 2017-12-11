"use strict";

$(function () {
    var ForgetPassword = {
        init: function () {
            var form = $(".login-form");

            $(".login-button").click(function () {
                ForgetPassword.reset();
            });
            form.keypress(function (event) {
                if (event.keyCode === 13) {
                    ForgetPassword.reset();
                }
            });
            form.validate();
        },

        reset: function () {
            var form = $(".login-form");
            if (!form.valid()) {
                return false;
            }
            var formData = form.serializeArray();
            var jsonData = {};
            for (var index = 0; index < formData.length; index += 1) {
                var data = formData[index];
                jsonData[data.name] = data.value;
            }
            var telRegex = /1\d{10}/;
            if (telRegex.test(jsonData.username)) {
                jsonData.phone = jsonData.username;
            } else {
                jsonData.email = jsonData.username;
            }
            $.ajax({
                url: "/ajax/user/reset-password",
                type: "post",
                data: JSON.stringify(jsonData),
                contentType: "application/json"
            }).done(function () {
                window.location.href = "/account/reset-password";
            }).fail(function () {
                $(".login-form-error").show();
                ForgetPassword.changeCaptcha();
            });
            return false;
        }
    };
    ForgetPassword.init();
});