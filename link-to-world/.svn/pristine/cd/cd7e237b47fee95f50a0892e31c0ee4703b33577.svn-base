$(function () {
    var form = $(".policy-holder-form");
    var PolicyHolder = {
        init: function () {
            $(".submit-button").click(function () {
                PolicyHolder.submit();
            });

            form.validate();
        },

        submit: function () {
            if (!form.valid()) {
                return false;
            }
            var formData = form.serializeArray();
            var data = {};
            for (var i = 0; i < formData.length; i++) {
                data[formData[i].name] = formData[i].value;
            }
            $.ajax({
                url: '/ajax/policy-holder',
                type: 'put',
                data: JSON.stringify(data),
                contentType: 'application/json'
            }).done(function () {
                window.location.href = "/account/policy-holder";
            }).fail(function () {
                $(".form-error").show();
            });
        }
    };
    PolicyHolder.init();
});