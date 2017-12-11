$(function() {
    var Order = {
        form: $("#search-form"),
        pager: {
            page: 1,
            pages: [],
            limit: 10,
            totalPages: 1,
            goto: function(page) {
                if (!page) {
                    return;
                }
                Order.form.find("[name=page]").val(page);
                Order.search();
            },
            prev: function() {
                Order.form.find("[name=page]").val(parseInt(Order.form.find("[name=page]").val()) - 1);
                Order.search();
            },
            next: function() {
                Order.form.find("[name=page]").val(parseInt(Order.form.find("[name=page]").val()) + 1);
                Order.search();
            },
            init: function() {
                $(document).on("click", ".pagination-item", function() {
                    Order.pager.goto($(this).data("value"));
                });
                $(document).on("click", ".btn-goto", function() {
                    Order.pager.goto($("#goto-page").val());
                });
                $(document).on("click", ".pagination-prev", function() {
                    Order.pager.prev();
                });
                $(document).on("click", ".pagination-next", function() {
                    Order.pager.next();
                });
            }
        },
        listPanel: $(".order-list-panel"),
        page: $(".order-list-panel").data("page"),
        limit: $(".order-list-panel").data("limit"),
        total: $(".order-list-panel").data("total"),
        init: function() {
            $(".order-delete-button").click(function() {
                Order.deleteConfirm($(this).data("id"));
            });
            $(".search-button").click(function() {
                Order.search();
            });
            $(".surrender-btn").click(function() {
                $.confirm({
                    content: "是否确认退保",
                    callback: function() {
                        var data = {
                            orderId: $(this).data("id")
                        };
                        $.ajax({
                            url: "/ajax/underwriting/discharge",
                            type: "PUT",
                            contentType: "application/json",
                            dataType: "json",
                            data: JSON.stringify(data)
                        }).done(function() {
                            window.location.reload();
                        })
                    }.bind(this)
                });
            });
            $(".pay-btn").click(function() {
                var id = $(this).data("id");
                var data = {};
                data.ids = [];
                data.ids.push(id);
                $.ajax({
                    url: "/ajax/order/continue",
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(data)
                }).done(function(result) {
                    window.location.href = "/checkout/pay?id=" + result.paymentId;
                })
            });
            $("#search-type").click(function(event) {
                $(".search-wrap").find("input").attr("name", $(event.currentTarget).val());
            });
            $(".status-name").each(function(index, node) {
                $(node).text(this.statusName($(node).data("status")));
            }.bind(this));
            Order.renderPager();
            Order.pager.init();
        },
        statusName: function(status) {
            switch (status) {
                case "DRAFT":
                    return "拟定";
                case "PAYMENT_PENDING":
                    return "待支付";
                case "PAYMENT_FAILED":
                    return "支付失败";
                case "PAYMENT_COMPLETED":
                    return "支付成功";
                case "AUDITING":
                    return "核保";
                case "VENDOR_INSURED":
                    return "已承保";
                case "VENDOR_REJECT":
                    return "拒保";
                case "DOCUMENTED":
                    return "已出单";
                case "SURRENDERING":
                    return "请求退保";
                case "SURRENDERED":
                    return "已退保";
                case "REFUND":
                    return "已退款";
                case "SURRENDER_FAILED":
                    return "退款失败";
                default:
                    return "";
            }
        },
        deleteConfirm: function(id) {
            $.confirm({
                "size": "small",
                "level": "danger",
                "confirm": "删除",
                "content": "是否确认删除这个订单",
                callback: function() {
                    Order.delete(id);
                }
            });
        },
        delete: function(id) {
            $.ajax({
                url: "/ajax/order/" + id,
                method: "delete"
            }).done(function() {
                window.location.href = "";
            }).fail(function(result) {
                console.log(result);
            });
        },
        search: function() {
            var data = Order.form.serialize();
            window.location.href = "?" + data;
            // console.log(data);
        },
        pagination: Handlebars.compile($("#tpl-pagination").html()),
        renderPager: function() {
            Order.pager.pages = [];
            Order.pager.totalPages = parseInt(Order.total / Order.limit);
            if (Order.pager.totalPages * Order.limit < Order.total) {
                Order.pager.totalPages += 1;
            }
            var start = Order.page > 3 ? Order.page - 2 : 1;
            var end = start + 4 > Order.pager.totalPages ? Order.pager.totalPages : start + 4;
            for (var i = 1; i <= end; i++) {
                Order.pager.pages.push({no: i, active: i == Order.page ? "active" : ""});
            }
            var html = Order.pagination(Order.pager);
            $(".pagination-wrap").html(html);
        }

    };
    Order.init();
});