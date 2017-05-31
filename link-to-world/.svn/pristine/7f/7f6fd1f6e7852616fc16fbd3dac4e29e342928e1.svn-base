$(function () {
    var $loginError = $(".login-form-error");

    var Index = {
        init: function () {
            $.validator.setDefaults({
                highlight: function (element) {
                    $(element).closest('.form-group').addClass('has-error');
                },
                unhighlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-error');
                    $loginError.hide();
                },
                errorElement: 'label',
                errorClass: 'text-danger',
                errorPlacement: function (error, element) {
                    $loginError.text(error[0].innerHTML);
                    $loginError.show();
                }
            });
            $("#change-captcha").click(function () {
                Index.changeCaptcha();
            });
        }, changeCaptcha: function () {
            $("#change-captcha>img").attr("src", "/captcha.jpg?t=" + new Date().getTime());
        }
    };
    Index.init();

    var Login = {
        init: function () {
            var form = $(".login-form");
            $(".login-button").click(function () {
                Login.login();
            });
            form.keypress(function (event) {
                if (event.keyCode === 13) {
                    Login.login();
                }
            });
            form.validate();
        },
        login: function () {
            var form = $(".login-form");
            if (!form.valid()) {
                return false;
            }
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
                var e = response.responseJSON;
                var message = "";
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        message += invalidField.message;
                    }
                }
                $loginError.text(message);
                $loginError.show();
            });
            return false;
        }
    };
    Login.init();
});

$.validator.addMethod("phoneOrEmail", function (value, element, params) {
    return $.validator.methods.email.call(this, value, element) || $.validator.methods.cellphone.call(this, value, element);
}, "请输入手机号码或电子邮箱");