"use strict";

$(function () {
    var Processor = {
        counter: 0,
        orders: {},
        processor: null,
        refreshProcessor: null,
        processingOrders: 0,
        orderProcessing: ["处理中", "处理中.", "处理中..", "处理中..."],
        paymentProcessing: ["订单处理中", "订单处理中.", "订单处理中..", "订单处理中..."],
        confirmProcessing: ["已核保，承保中", "已核保，承保中.", "已核保，承保中..", "已核保，承保中..."],
        init: function () {
            $(".status").each(function (index, target) {
                this.order($(target).data("id"));
            }.bind(this));
            this.processing();
            this.refresh();
            $(".order-main").show();
        },
        processing: function () {
            this.processor = setInterval(function () {
                this.counter = (this.counter + 1) % 4;
                $(".processing").text(this.paymentProcessing[this.counter]);
                if (this.processingOrders === 0) {
                    $(".processing").slideUp();
                    clearInterval(this.processor);
                }
            }.bind(this), 1000);
        },
        finish: function (html, id) {
            $(".status-" + id).html(html);
            clearInterval(this.orders[id].processor);
            this.processingOrders -= 1;
        },
        confirming: function (order) {
            order.status = "confirm";
            $(".status-3").html("<span class='text'>" + this.confirmProcessing[order.counter] + "</span>");
        },
        refresh: function () {
            // get API
            this.refreshProcessor = setInterval(function () {
                $.getJSON("/ajax/order/payment/" + $("#paymentId").val()).done(function (result) {
                    var finish = true;
                    var oI = 0;
                    var oII = 0;
                    for (; oI < result.items.length; oI += 1) {
                        for (; oII < result.items[oI].items.length; oII += 1) {
                            var orderItem = result.items[oI].items[oII];
                            if (orderItem.orderStatus === "PAYMENT_PENDING") {
                                this.finish("<span class='text-primary'>待支付</span>", orderItem.id);
                                finish = false;
                            } else if (orderItem.orderStatus === "PAYMENT_FAILED") {
                                $(".btn-repay").show();
                                this.finish("<span class='text-danger'>支付失败</span>", orderItem.id);
                            } else if (orderItem.orderStatus === "AUDITING") {
                                this.confirming(orderItem.id);
                                finish = false;
                            } else if (orderItem.orderStatus === "VENDOR_INSURED") {
                                this.finish("已承保", orderItem.id);
                            } else if (orderItem.orderStatus === "VENDOR_REJECT") {
                                this.finish("承保失败", orderItem.id);
                            }
                        }
                    }
                    if (finish) {
                        clearInterval(this.refreshProcessor);
                    }
                }.bind(this)).fail(function (response) {
                    console.log(response);
                });
            }.bind(this), 1000);
        },
        order: function (id) {
            var order = {
                id: id,
                status: "processing",
                counter: 0,
                processor: setInterval(function () {
                    order.counter = (order.counter + 1) % 4;
                    var text = order.status === "processing" ? this.orderProcessing[order.counter] : this.confirmProcessing[order.counter];
                    $(".status-" + id).find(".text").text(text);
                }.bind(this), 1000)

            };
            this.processingOrders += 1;
            this.orders[id] = order;
        }

    };

    Processor.init();
});