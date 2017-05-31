"use strict";

$(function () {
    var CreditTracking = {
        template: Handlebars.compile($("#tpl-credit-tracking-item").html()),
        init: function () {
            var dealerId = $("#dealerId").val();
            $.ajax({
                url: "/ajax/dealer/" + dealerId + "/credit/tracking",
                type: "get",
                dataType: 'json'
            }).done(function (result) {
                CreditTracking.render(result);
            }).fail(function () {
                alert("暂时无法获取数据");
            });
        },
        render: function (result) {
            var html = "";
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += CreditTracking.template(data);
            }
            var table = $(".credit-tracking-table");
            table.find("tbody").html(html);
        }
    };

    CreditTracking.init();
});

Handlebars.registerHelper('translateType', function (context) {
    if (context == "CHECKOUT") {
        return "支付";
    } else if (context == "INIT") {
        return "创建";
    } else if (context == "UPDATE") {
        return "更新";
    } else if (context == "RESET") {
        return "回销";
    }
});