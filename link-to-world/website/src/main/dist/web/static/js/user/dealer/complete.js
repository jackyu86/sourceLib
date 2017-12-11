$(function () {
    var CompleteInfo = {
        init: function () {
            var form = $(".login-form");

            $(".login-button").click(function () {
                CompleteInfo.complete();
            });
            form.keypress(function (e) {
                if (e.keyCode == 13) {
                    CompleteInfo.complete();
                }
            });
            form.validate();
        },
        complete: function () {
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
            $.ajax({
                url: '/ajax/dealer/complete',
                type: 'put',
                data: JSON.stringify(jsonData),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function () {
                window.location.href = "/account/info";
            }).fail(function () {
                $(".login-form-error").show();
            });
        }
    };
    CompleteInfo.init();
});