$(function () {
    var form = $(".dealer-form");
    var Dealer = {
        init: function () {
            State.init();
            $(".submit-button").click(function () {
                Dealer.submit();
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
                url: '/ajax/dealer',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function () {
                window.location.href = "/account/dealer";
            }).fail(function (response) {
                handleFailResponse(response, form);
            });
        }
    };

    Dealer.init();
});