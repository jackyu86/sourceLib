$(function () {
    var SettlementList = {
        page: {
            page: 1,
            limit: 10,
            totalPages: 1,
            pages: [{no: 1, active: "active"}]
        },
        filterData: {
            page: 1,
            limit: 10
        },
        template: Handlebars.compile($("#tpl-settlement-item").html()),
        pagination: Handlebars.compile($("#tpl-pagination").html()),
        init: function () {
            $(".pagination-wrap").on("click", ".pagination-item", function () {
                if ($(this).hasClass("pagination-prev")) {
                    SettlementList.page.page -= 1;
                } else if ($(this).hasClass("pagination-next")) {
                    SettlementList.page.page += 1;
                } else {
                    SettlementList.page.page = $(this).text();
                }
                SettlementList.search();
            }).on("click", ".btn-jump", function () {
                SettlementList.page.page = $("#jump-page-number").val();
                SettlementList.search();
            });
            var $search = $(".search-button");
            $search.click(function () {
                SettlementList.fillFilter();
            });
            $search.click();
        },
        fillFilter: function () {
            var formData = $(".filter-bar").serializeArray();
            for (var i = 0; i < formData.length; i++) {
                if (formData[i].value) {
                    SettlementList.filterData[formData[i].name] = formData[i].value;
                } else {
                    SettlementList.filterData[formData[i].name] = undefined;
                }
            }
            SettlementList.search();

        },
        search: function () {
            SettlementList.filterData.page = SettlementList.page.page;
            SettlementList.filterData.limit = SettlementList.page.limit;
            var data = JSON.stringify(SettlementList.filterData);
            $.ajax({
                url: '/ajax/dealer/settlement',
                type: 'put',
                data: data,
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (result) {
                SettlementList.render(result);
                SettlementList.renderPage(result);
            }).fail(function () {
                $.alert({size: "small", content: "暂时无法获取数据"});
            });
        },
        render: function (result) {
            var html = "";
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += SettlementList.template(data);
            }
            var table = $(".dealer-table");
            table.find("tbody").html(html);

        },
        renderPage: function (result) {
            SettlementList.page.pages = [];
            SettlementList.page.totalPages = parseInt(result.total / result.limit);
            if (SettlementList.page.totalPages * result.limit < result.total || result.total === 0) {
                SettlementList.page.totalPages += 1;
            }
            var start = result.page > 3 ? result.page - 2 : 1;
            var end = start + 4 > SettlementList.page.totalPages ? SettlementList.page.totalPages : start + 4;
            for (var i = 1; i <= end; i++) {
                SettlementList.page.pages.push({no: i, active: i == result.page ? "active" : ""});
            }
            var html = SettlementList.pagination(SettlementList.page);
            $(".pagination-wrap").html(html);
        }
    };

    SettlementList.init();
});

Handlebars.registerHelper('translateStatus', function (context, options) {
    if (context == "DRAFT")
        return "拟定";
    else if (context == "PAYMENT_PENDING")
        return "待支付";
    else if (context == "PAYMENT_FAILED")
        return "支付失败";
    else if (context == "PAYMENT_COMPLETED")
        return "支付成功";
    else if (context == "AUDITING")
        return "核保";
    else if (context == "VENDOR_INSURED")
        return "已承保";
    else if (context == "VENDOR_REJECT")
        return "拒保";
    else if (context == "DOCUMENTED")
        return "已出单";
    else if (context == "SURRENDERING")
        return "请求退保";
    else if (context == "SURRENDERED")
        return "已退保";
    else if (context == "REFUND")
        return "已退款";
});