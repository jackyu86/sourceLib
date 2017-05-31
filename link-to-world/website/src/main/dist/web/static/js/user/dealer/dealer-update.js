$(function () {
    var form = $(".dealer-form"),
        dealerId = $("#dealerId").val(),
        Dealer = {
        init: function () {
            if ($("input[name=stateVal]").val() !== undefined) {
                State.init().handle($("input[name=stateVal]").val(), $("input[name=cityVal]").val(), $("input[name=wardVal]").val());
            }
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
                url: '/ajax/dealer/' + data.id,
                type: 'put',
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json'
            }).success(function () {
                window.location.href = "/account/dealer";
            }).error(function (response) {
                handleFailResponse(response, form);
            });
        }
    };

    Dealer.init();
});