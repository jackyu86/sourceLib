$(function () {
    var form = $(".dealer-form");

    var Dealer = {
        init: function () {
            $(".submit-button").click(function () {
                Dealer.submit();
            });

            form.validate();
        },
        submit: function () {
            $(".form-error").hide();
            if (!form.valid()) {
                return false;
            }
            var formData = form.serializeArray();
            var data = {};
            for (var i = 0; i < formData.length; i++) {
                data[formData[i].name] = formData[i].value;
            }
            $.ajax({
                url: '/ajax/dealer/user/' + data.id,
                type: 'put',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function () {
                window.location.href = "/account/dealer/user";
            }).fail(function (response) {
                handleFailResponse(response, form);
            });
        }
    };

    Dealer.init();
});