"use strict";

$(function () {
    var Login = {
        init: function () {
            var form = $(".login-form");
            $(".login-button").click(function () {
                Login.login();
            });
            $("#change-captcha").click(function () {
                Login.changeCaptcha();
            });
            form.keypress(function (event) {
                if (event.keyCode === 13) {
                    Login.login();
                }
            });
            form.validate();
        },
        changeCaptcha: function () {
            $("#change-captcha>img").attr("src", "/captcha.jpg?t=" + new Date().getTime());
        },
        login: function () {
            var form = $(".login-form");
            if (!form.valid()) {
                return false;
            }
            this.loading();
            $(".login-form-error").hide();
            var formData = form.serializeArray();
            var jsonData = {};
            for (var index = 0; index < formData.length; index += 1) {
                var data = formData[index];
                jsonData[data.name] = data.value;
            }
            $.ajax({
                url: "/ajax/user/login",
                type: "post",
                data: JSON.stringify(jsonData),
                headers: {"Content-Type": "application/json"},
                dataType: "json"
            }).done(function (response) {
                if (response && response.fromURL) {
                    window.location.href = response.fromURL;
                } else {
                    window.location.href = "/account/info";
                }
            }).fail(function (response) {
                this.loaded();
                var e = response.responseJSON;
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        var input = form.find('input[name=' + invalidField.path + ']');
                        if ($("#" + invalidField.path + "-error").length > 0) {
                            $("#" + invalidField.path + "-error").remove();
                        }
                        $('<label id="' + invalidField.path + '-error" class="text-danger validation-message" for="' + invalidField.path + '">' + invalidField.message + '</label>').insertAfter(input);
                        input.parent().addClass('has-error');
                    }
                }
            }.bind(this));
            return false;
        },
        loading: function () {
            $(".login-button").addClass("loading");
        },
        loaded: function () {
            $(".login-button").removeClass("loading");
        }
    };
    Login.init();
});