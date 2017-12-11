"use strict";

$(function () {
    var form = $(".login-form");

    var Register = {
        init: function () {
            $(".login-button").click(function () {
                Register.register();
            });
            form.keypress(function (event) {
                if (event.keyCode === 13) {
                    Register.register();
                }
            });
            $("#send-captcha").click(function () {
                Register.sendCaptcha();
            });
            $("#term").siblings("label").click(function () {
                if (!$("#term").prop("checked")) {
                    $("#term-notice").hide();
                }
            });
            $("input[name='parentDealerId']").change(function () {
                var parentDealerId = $(this).val();
                if (parentDealerId) {
                    $.ajax({
                        url: "/ajax/dealer/" + parentDealerId + "/check",
                        type: "get",
                        contentType: "application/json",
                        dataType: "json"
                    }).done(function (response) {
                        if (response.errorMsg) {
                            $(this).siblings(".text-danger.validation-message").text(response.errorMsg).show();
                            $(this).parents(".form-group").addClass('has-error');
                        } else {
                            $(this).siblings(".text-danger.validation-message").hide();
                            $(this).parents(".form-group").removeClass('has-error');
                        }
                    }.bind(this));
                } else {
                    $(this).siblings(".text-danger.validation-message").hide();
                    $(this).parents(".form-group").removeClass('has-error');
                }
            });
            $("#username").change(function () {
                var input = $("#username");
                var username = input.val();
                $("#cellphone").val(username);
                if (username) {
                    $.ajax({
                        url: "/ajax/user/" + username + "/check",
                        type: "get",
                        contentType: "application/json",
                        dataType: "json"
                    }).done(function (response) {
                        if (response.exist) {
                            var $errorLab = input.siblings(".text-danger.validation-message");
                            input.parents(".form-group").addClass('has-error');
                            var telRegex = /1\d{10}/;
                            if (telRegex.test(username)) {
                                $errorLab.html("此手机号已被注册");
                            } else {
                                $errorLab.html("此邮箱已被注册");
                            }
                        } else {
                            input.parents(".form-group").removeClass('has-error');
                        }
                    });
                }
            }.bind(this));

            $('input[name="pinCode"]').change(function () {
                var input = form.find('input[name="pinCode"]');
                var pinCode = input.val();
                var username = $("#username").val();
                if (pinCode && username) {
                    $.ajax({
                        url: "/ajax/user/" + username + "/pin-code/" + pinCode + "/check",
                        type: "get",
                        contentType: "application/json",
                        dataType: "json"
                    }).done(function (response) {
                        var $errorLab = input.siblings(".text-danger.validation-message");
                        if (!response.valid) {
                            input.parents(".form-group").addClass('has-error');
                            $errorLab.html("请输入正确的验证码");
                        } else {
                            input.parents(".form-group").removeClass('has-error');
                        }
                    });
                }
            }.bind(this));

            $("#password").keyup(function (event) {
                var text = $(event.currentTarget).val();
                if (text.length >= 8) {
                    if (/[^\da-zA-Z]/.test(text)) {
                        $("#password-safety").removeClass().addClass("high");
                    } else if (/[^\d]/.test(text)) {
                        $("#password-safety").removeClass().addClass("middle");
                    } else {
                        $("#password-safety").removeClass().addClass("low");
                    }
                } else if (text.length >= 6) {
                    $("#password-safety").removeClass().addClass("low");
                } else {
                    $("#password-safety").removeClass();
                }
            });

            var type = $("input[name=dealer]");
            type.change(function (event) {
                var $this = $(event.currentTarget);
                if ($this.val() === "true") {
                    $(".dealer-form-group").show();
                } else {
                    $(".dealer-form-group").hide();
                }
                $("#user-protocol").toggleClass("hidden");
                $("#dealer-protocol").toggleClass("hidden");

            });
            State.init();

            if ($("input[name=dealer][checked]").val() === "true") {
                $(".dealer-form-group").show();
            }
            this.initValidation();
        },
        initValidation: function () {
            form.validate({
                messages: {
                    "re-password": {
                        equalTo: "两次输入的密码不一致"
                    },
                    term: {
                        required: "请阅读并确认协议内容"
                    }
                }
            });
        },
        enableCaptcha: function () {
            $(".captcha-wrap").addClass("available");
        },
        disableCaptcha: function () {
            $(".captcha-wrap").removeClass("available");
        },
        sendCaptcha: function () {
            if ($("#send-captcha").attr("disabled") === "disabled") {
                return false;
            }
            if (!$("#username").valid()) {
                return false;
            }
            var input = form.find('input[name="pinCode"]');
            var $errorLab = input.siblings(".text-danger.validation-message");
            if ($errorLab.length > 0) {
                $errorLab.remove();
                input.parents(".form-group").removeClass('has-error');
            }
            var data = {};
            var telRegex = /1\d{10}/;
            var username = $("#username").val();
            if (telRegex.test(username)) {
                data.phone = username;
            } else {
                data.email = username;
            }
            $.ajax({
                url: "/ajax/pin-code/send",
                type: "post",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                Register.waitCaptcha();
            }).fail(function (response) {
                handleFailResponse(response, form);
            });
            return false;
        },
        waitCaptcha: function () {
            $("#send-captcha").attr("disabled", "disabled");
            intervalCaptcha(60);
            function intervalCaptcha(leftTime) {
                $("#send-captcha").text("重新发送" + leftTime + "s");
                setTimeout(function () {
                    if (leftTime > 0) {
                        intervalCaptcha(leftTime - 1);
                    } else {
                        $("#send-captcha").text("重新发送").attr("disabled", null);
                    }
                }, 1000);
            }
        },
        register: function () {
            if (!form.valid()) {
                $("html,body").scrollTop($(".has-error .validation-message").eq(0).offset().top);
                return false;
            }
            if (!$("#term").prop("checked")) {
                $("#term-notice").show();
                return false;
            }
            this.loading();
            var formData = form.serializeArray();
            var jsonData = {};
            for (var index = 0; index < formData.length; index += 1) {
                var data = formData[index];
                if (data.value) {
                    jsonData[data.name] = data.value;
                } else {
                    jsonData[data.name] = null;
                }
            }
            if (String($("[name='dealer']:checked").val()) === "true") {
                $.ajax({
                    url: "/ajax/dealer/register",
                    type: "post",
                    data: JSON.stringify(jsonData),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function () {
                    $("#register-title").text("注册成功");
                    $(".type-selector").remove();
                    $("#register-form").hide();
                    $("#success-form").show();
                    this.startCountDown("/account/login");
                }.bind(this)).fail(function (response) {
                    this.loaded();
                    handleFailResponse(response, form);
                }.bind(this));
            } else {
                $.ajax({
                    url: "/ajax/user/register",
                    type: "post",
                    data: JSON.stringify(jsonData),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function (response) {
                    $("#register-title").text("注册成功");
                    $(".type-selector").remove();
                    $("#register-form").hide();
                    $("#success-form").show();
                    $(".go-to").text("");
                    if (response && response.fromURL) {
                        $("#btn-go-to").attr("href", response.fromURL).text("立即跳转");
                        this.startCountDown(response.fromURL);
                    } else {
                        $("#btn-go-to").attr("href", "/account/info").text("立即跳转");
                        this.startCountDown("/account/info")
                    }
                }.bind(this)).fail(function (response) {
                    this.loaded();
                    handleFailResponse(response, form);
                }.bind(this));
            }
            return false;
        },
        startCountDown: function (url) {
            var time = 3;
            setInterval(function () {
                time -= 1;
                if (time < 0) {
                    return;
                }
                $(".count-down").text(time);
                if (time === 0) {
                    window.location.href = url;
                }
            }.bind(this), 1000);
        },
        loading: function () {
            $(".login-button").addClass("loading");
        },
        loaded: function () {
            $(".login-button").removeClass("loading");
        }
    };
    Register.init();
});