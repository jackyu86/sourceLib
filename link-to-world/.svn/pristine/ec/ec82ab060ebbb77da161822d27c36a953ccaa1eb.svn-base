"use strict";

$(function () {
    var Info = {
        init: function () {
            $(".submit-button").click(function () {
                Info.update();
            });
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
                url: "/ajax/dealer/update",
                type: "put",
                data: JSON.stringify(jsonData),
                contentType: "application/json",
                dataType: 'json'
            }).done(function () {
                window.location.href = "/account/info";
            }).fail(function (response) {
                var e = response.responseJSON;
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        var input = form.find('input[name=' + invalidField.path + ']');
                        $('<label id="email-error" class="text-danger validation-message" for="email">' + invalidField.message + '</label>').insertAfter(input);
                        input.parent().addClass('has-error');
                    }
                }
            });
        }
    };

    Info.init();
});
