"use strict";

$(function () {
    var InviteList = {
        template: Handlebars.compile($("#tpl-invite-item").html()),
        init: function () {
            $.ajax({
                url: "/ajax/customer/invite?" + $("#invite-link").text().split('?')[1],
                type: "put",
                dataType: 'json'
            }).done(function (result) {
                InviteList.render(result);
            }).fail(function () {
                alert("暂时无法获取数据");
            });

            $("#show-table").click(function () {
                $(".table-wrap").show();
            })
        },
        render: function (result) {
            var html = "";
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += InviteList.template(data);
            }
            var table = $(".invite-table");
            table.find("tbody").html(html);
            $("#invite-count").text(result.total);
        }
    };

    InviteList.init();
});

Handlebars.registerHelper('translateStatus', function (context, options) {
    if (context == "ACTIVE") {
        return "正常";
    } else if (context == "AUDITING") {
        return "审核中";
    } else if (context == "INACTIVE") {
        return "冻结";
    }
});