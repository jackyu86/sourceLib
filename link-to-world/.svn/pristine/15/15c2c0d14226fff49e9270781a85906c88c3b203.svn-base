$(function () {
    var Password = {
        changePay: false,
        init: function () {
            $(".login-button").click(function () {
                Password.change();
            });
            $(".btn-change-pay-password").click(function () {
                this.changePay = true;
            }.bind(this));
            $(".btn-change-password").click(function () {
                this.changePay = false;
            }.bind(this));
            $(".close").click(function () {
                $("#change-form").show();
                $("#success-form").hide();
                $("#change-password").find(".login-form")[0].reset();
            });
            $.validator.addMethod("password", function (value, element, params) {
                var password = $.trim(value);
                return (/[0-9a-zA-Z]{6,}/).test(password);
            }, "格式错误");

        },
        change: function () {
            var form = $("#change-form");
            if (!form.valid()) {
                return false;
            }
            var formData = $("#change-form").serializeArray();
            var data = {};
            for (var index = 0; index < formData.length; index += 1) {
                data[formData[index].name] = formData[index].value;
            }
            if (this.changePay) {
                this.changePayPassword(data);
            } else {
                this.changePassword(data);
            }

            return false;
        },
        changePassword: function (data) {
            var url = data.oldPassword === undefined ? "/ajax/anonym/self/password" : "/ajax/user/self/password";
            $.ajax({
                url: url,
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                $("#change-form").hide();
                $("#success-form").show();
                $("#change-password").find(".login-form")[0].reset();
            }).fail(function (result) {
                handleFailResponse(result, $("#change-form"));
            });
        },
        changePayPassword: function (data) {
            $.ajax({
                url: "/ajax/dealer/self/pay-password",
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                $("#change-form").hide();
                $("#success-form").show();
                $("#change-password").find(".login-form")[0].reset();
            }).fail(function (result) {
                var e = result.responseJSON;
                if (e.invalidFields && e.invalidFields.length) {
                    for (var fieldIndex = 0; fieldIndex < e.invalidFields.length; fieldIndex += 1) {
                        var invalidField = e.invalidFields[fieldIndex];
                        var $invalidError = $("#" + invalidField.path + "-invalid");
                        $invalidError.text(invalidField.message);
                        $invalidError.show();
                    }
                } else {
                    $.alert({
                        "size": "small",
                        "content": "更新失败"
                    });
                }
            });
        }
    };
    Password.init();
});