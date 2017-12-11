$(function () {
    $.fn.clearValidation = function () {
        var v = $(this).validate();
        $('[name]', this).each(function () {
            v.successList.push(this);
            v.showErrors();
        });
        v.resetForm();
        v.reset();
    };

    var ResetPassword = {
        init: function () {
            var form = $(".login-form");

            $(".login-button").click(function () {
                ResetPassword.reset();
            });
            form.keypress(function (e) {
                if (e.keyCode == 13) {
                    ResetPassword.reset();
                }
            });
            $("#username").change(function () {
                if (this.usernameValid()) {
                    $("#token").parent().addClass("available");
                } else {
                    $("#token").parent().removeClass("available");
                }
                var input = $("#username");
                var username = input.val();
                $.ajax({
                    url: "/ajax/user/" + username + "/check",
                    type: "get",
                    contentType: "application/json",
                    dataType: "json"
                }).done(function (response) {
                    if (!response.exist) {
                        var $errorLab = input.siblings(".text-danger.validation-message");
                        input.parent().addClass('has-error');
                        var telRegex = /1\d{10}/;
                        if (telRegex.test(username)) {
                            $errorLab.html("此手机号未被注册");
                        } else {
                            $errorLab.html("此邮箱已未被注册");
                        }
                    } else {
                        input.parent().removeClass('has-error');
                    }
                });
            }.bind(this)).keyup(function () {
                if (this.usernameValid()) {
                    $("#token").parent().addClass("available");
                } else {
                    $("#token").parent().removeClass("available");
                }
            }.bind(this));
            $('input[name="pinCode"]').change(function () {
                if (this.usernameValid()) {
                    $("#token").parent().addClass("available");
                } else {
                    $("#token").parent().removeClass("available");
                }
                var input = $('input[name="pinCode"]');
                var pinCode = input.val();
                var username = $("#username").val();
                if (pinCode && username) {
                    $.ajax({
                        url: "/ajax/user/" + username + "/pin-code/" + pinCode + "/check",
                        type: "get",
                        contentType: "application/json",
                        dataType: "json"
                    }).done(function (response) {
                        if (!response.valid) {
                            var $errorLab = input.siblings(".text-danger.validation-message");
                            input.parent().addClass('has-error');
                            $errorLab.html("请输入正确的验证码");
                        } else {
                            input.parent().removeClass('has-error');
                        }
                    });
                }
            }.bind(this)).keyup(function () {
                if (this.usernameValid()) {
                    $("#token").parent().addClass("available");
                } else {
                    $("#token").parent().removeClass("available");
                }
            }.bind(this));
            $("#change-captcha").click(function () {
                ResetPassword.changeCaptcha();
            });
            $("#send-captcha").click(function () {
                ResetPassword.sendCaptcha();
            });

            form.validate();
        },
        usernameValid: function () {
            return $("#username").valid() && $("#pinCode").valid();
        },
        changeCaptcha: function () {
            $("#change-captcha>img").attr("src", "/captcha.jpg?t=" + new Date().getTime());
        },
        sendCaptcha: function () {
            var jsonData = {};
            var username = $("#username").val();
            if (username.length < 1) {
                $(".login-form-error").show();
                return false;
            }
            if (this.wait) {
                return;
            }
            jsonData.captchaCode = $("#pinCode").val();
            jsonData.username = username;
            $.ajax({
                url: "/ajax/user/reset-password",
                type: "post",
                data: JSON.stringify(jsonData),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                $(".login-form").clearValidation();
                ResetPassword.waitCaptcha();
            }).fail(function (response) {
                var form = $(".login-form");

                var errors = form.find('.has-error');
                errors.find('.validation-message').remove();
                errors.removeClass('has-error');

                var e = response.responseJSON;
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        var input = form.find('input[name=' + invalidField.path + ']');
                        var message = input.next('.validation-message');
                        if (message.length > 0) {
                            message.text(invalidField.message);
                        } else {
                            $('<label class="text-danger validation-message">' + invalidField.message + '</label>').insertAfter(input);
                        }
                        input.parent().addClass('has-error');
                    }
                }
            });
        },
        waitCaptcha: function () {
            $("#send-captcha").attr("disabled", "disabled");
            this.wait = true;
            intervalCaptcha(60);
            function intervalCaptcha(leftTime) {
                $("#send-captcha").text(leftTime + "秒后重试发送");
                setTimeout(function () {
                    if (leftTime > 0) {
                        intervalCaptcha(leftTime - 1);
                    } else {
                        $("#send-captcha").text("重新发送验证码").attr("disabled", null);
                        this.wait = false
                    }
                }.bind(this), 1000);
            }
        },
        reset: function () {
            var form = $(".login-form"),
                $this = this;
            if (!form.valid()) {
                return false;
            }
            var formData = form.serializeArray();
            var jsonData = {};
            for (var index = 0; index < formData.length; index += 1) {
                var data = formData[index];
                jsonData[data.name] = data.value;
            }
            $.ajax({
                url: "/ajax/user/reset-password/apply",
                type: "post",
                data: JSON.stringify(jsonData),
                contentType: "application/json",
                dataType: "json"
            }).done(function (result) {
                window.location.href = "/account/reset-password-success";
            }).fail(function (response) {
                handleFailResponse(response, form);
                $this.changeCaptcha();
            });
        }
    };
    ResetPassword.init();
});