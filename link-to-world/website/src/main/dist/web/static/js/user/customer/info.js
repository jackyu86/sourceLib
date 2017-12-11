"use strict";

$(function () {
    var Info = {
        certifications: [],
        init: function () {
            State.init().handle($("input[name=stateVal]").val(), $("input[name=cityVal]").val(), $("input[name=wardVal]").val());

            $(".info-form").validate();
            $(".submit-button").click(function () {
                $.confirm({
                    size: "large",
                    level: "primary",
                    confirm: "确认更新",
                    cancel: "取消",
                    content: $(".info-form").find("[name=fullName]").val() === undefined ? "是否确认更新用户信息" : "是否确认更新用户信息，姓名和身份证号一旦更新无法修改。",
                    callback: function () {
                        Info.update();
                    }
                });
            });
            $("input").blur(function () {
                $(this).valid();
            });
            this.limitBirthDate();
            this.initCertification();
        },
        initCertification: function () {
            $.get("/ajax/customer/" + $("#customer-id").val() + "/identifications").done(function (result) {
                this.certifications = result;
                console.log(result);
            });
            $("select[name=idType]").change(function () {
                if ($("select[name=idType]").val() == "1") {
                    $("input[name=identification]").attr("identification", "identification");
                } else {
                    $("input[name=identification]").removeAttr("identification");
                }
            });
            $("input[name=identification]").change(function () {
                if ($("select[name=idType]").val() == "1") {
                    var value = $("input[name=identification]").val();
                    if ($("input[name=identification]").valid()) {
                        this.getInfoFromId(value);
                    }
                }
            }.bind(this));
        },
        getInfoFromId: function (value) {
            $("input[name=gender]").val(parseInt(value.substr(16, 1)) % 2 === 0 ? "FEMALE" : "MALE");
            $("input[name=birthday]").val(value.substr(6, 4) + "-" + value.substr(10, 2) + "-" + value.substr(12, 2));
        },
        limitBirthDate: function () {
            var date = new Date();
            date.setYear(date.getYear() - 18);
            $("input[name='birthday']").data("date-end-date", date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate());
        },

        update: function () {
            var form = $(".info-form");
            var jsonData = {};
            if (!form.valid()) {
                $("body").scrollTop($(".validation-message").offset().top);
                return false;
            }
            var formData = form.serializeArray();
            for (var index = 0; index < formData.length; index += 1) {
                var data = formData[index];
                if (data.value) {
                    jsonData[data.name] = data.value;
                }
            }

            $.ajax({
                url: "/ajax/customer/update",
                type: "put",
                data: JSON.stringify(jsonData),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                window.location.href = "";
            }).fail(function (response) {
                var e = response.responseJSON;
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        var input = form.find('input[name=' + invalidField.path + ']');
                        $('<label class="text-danger validation-message">' + invalidField.message + '</label>').insertAfter(input);
                        input.parent().addClass('has-error');
                    }
                }
            });
        }
    };

    Info.init();
});