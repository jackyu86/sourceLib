/**
 * @author miller
 */
$(function() {
    var PayBook = {
        id: $("#id").val(),
        status: false,
        init: function() {
            $("#offline-form").validate();
            $("#wechat-pay-button").click(function() {
                this.toPay("WX_NATIVE", "wechat-pay");
            }.bind(this));
            $("#ali-pay-button").click(function() {
                this.toPay("ALI_WEB", "ali-pay");
            }.bind(this));
            $("#credit-pay").click(function() {
                this.creditPay();
            }.bind(this));
            $("#offline-pay-button").click(function() {
                $("#offline-pay").modal({show: true});
            }.bind(this));
            $("#submit-offline-pay").click(function() {
                if (!$("#offline-form").valid()) {
                    return false;
                }
                this.offlinePay();
            }.bind(this));
            $(".order-main").show();
            adjustFooter();
        },
        offlinePay: function() {
            var data = {};
            data.fullName = $("#fullName").val();
            data.phone = $("#phone").val();
            $.ajax({
                url: "/ajax/order/offline?id=" + this.id,
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function(result) {
                if (result.status == -1) {
                    this.loaded();
                    $.alert({
                        "size": "small",
                        "content": "请勿重复支付"
                    });
                    return;
                }
                if (result.status !== 0) {
                    this.loaded();
                    $.alert({
                        "size": "small",
                        "content": "提交支付请求失败,请重试"
                    });
                    return;
                }
                window.location.href = result.value;
            }.bind(this)).fail(function() {
                this.loaded();
                $.alert({
                    "size": "small",
                    "content": "提交失败"
                });
            }.bind(this));
            return false;
        },
        toPay: function(method, modal) {
            var data = {};
            data.method = method;
            if (this.status && method == "WX_NATIVE") {
                $("#" + modal).modal({show: true});
                return true;
            }
            this.loading();
            $.ajax({
                url: "/ajax/order/toPay?id=" + this.id,
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function(result) {
                if (result.status == -1) {
                    $.alert({
                        "size": "small",
                        "content": "请勿重复支付"
                    });
                    this.loaded();
                    return;
                }
                if (result.status !== 0) {
                    $.alert({
                        "size": "small",
                        "content": "提交支付请求失败,请重试"
                    });
                    this.loaded();
                    return;
                }
                this.status = true;
                this.id = result.paymentId;
                if (result.method === "ALI_WEB") {
                    window.location.href = result.value;
                }
                if (result.method === "WX_NATIVE") {
                    $(".qrcode").attr("src", "data:image/png;base64," + result.value);
                    $("#" + modal).modal({show: true});
                    setInterval(function() {
                        this.checkPaid();
                    }.bind(this), 10000);
                }
                this.loaded();
            }.bind(this)).fail(function() {
                this.loaded();
                $.alert({
                    "size": "small",
                    "content": "提交失败"
                });
            }.bind(this));
            return false;
        },
        creditPay: function() {
            var data = {};
            data.paymentId = this.id;
            data.password = $("#credit-password").val();
            $.ajax({
                url: "/ajax/order/pay/credit?id=" + this.id,
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(data)
            }).done(function(response) {
                if (response.result == "success") {
                    window.location.href = "/checkout/pay-finish/" + this.id;
                } else {
                    $.alert({
                        "size": "small",
                        "content": response.message
                    });
                }
            }.bind(this)).error(function(response) {
                $(".text-danger.validation-message").remove();
                var e = response.responseJSON;
                if (e.invalidFields) {
                    for (var i = 0; i < e.invalidFields.length; i++) {
                        var invalidField = e.invalidFields[i];
                        var input = $('input[name=' + invalidField.path + ']');
                        $('<label id="' + invalidField.path + '-error" class="text-danger validation-message" for="' + invalidField.path + '">' + invalidField.message + '</label>').insertAfter(input);
                        input.parent().addClass('has-error');
                    }
                }
            });
        },
        checkPaid: function() {
            if (this.id) {
                $.ajax({
                    url: "/ajax/order/" + this.id + "/status",
                    type: "GET",
                    contentType: "application/json",
                    dataType: "json"
                }).done(function(result) {
                    if (result.status === "PAYMENT_COMPLETED") {
                        window.location.href = "/checkout/pay-finish/" + this.id;
                    }
                    return true;
                }.bind(this)).error(function(error) {

                });
            }
        },
        loading: function() {
            $(".btn-pay-wechat").addClass("disabled");
            $(".btn-pay-ali").addClass("disabled");
            $(".btn-pay-offline").addClass("disabled");
        },
        loaded: function() {
            $(".btn-pay-wechat").removeClass("disabled");
            $(".btn-pay-ali").removeClass("disabled");
            $(".btn-pay-offline").removeClass("disabled");
        }
    };

    PayBook.init();
});