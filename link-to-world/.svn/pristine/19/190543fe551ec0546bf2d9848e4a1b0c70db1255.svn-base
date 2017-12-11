$(function () {
    var DealerList = {
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
        template: Handlebars.compile($("#tpl-dealer-item").html()),
        pagination: Handlebars.compile($("#tpl-pagination").html()),
        init: function () {
            $(".pagination-wrap").on("click", ".pagination-item", function () {
                if ($(this).hasClass("pagination-prev")) {
                    DealerList.page.page -= 1;
                } else if ($(this).hasClass("pagination-next")) {
                    DealerList.page.page += 1;
                } else {
                    DealerList.page.page = $(this).text();
                }
                DealerList.search();
            }).on("click", ".btn-jump", function () {
                DealerList.page.page = $("#jump-page-number").val();
                DealerList.search();
            });
            $(".search-button").click(function () {
                DealerList.fillFilter();
            });
            DealerList.search();
        },
        fillFilter: function () {
            var formData = $(".filter-bar").serializeArray();
            for (var i = 0; i < formData.length; i++) {
                if (formData[i].value) {
                    DealerList.filterData[formData[i].name] = formData[i].value;
                } else {
                    DealerList.filterData[formData[i].name] = undefined;
                }
            }
            DealerList.search();

        },
        search: function () {
            DealerList.filterData.page = DealerList.page.page;
            DealerList.filterData.limit = DealerList.page.limit;
            $.ajax({
                url: '/ajax/dealer',
                type: 'put',
                data: JSON.stringify(DealerList.filterData),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (result) {
                DealerList.render(result);
                DealerList.renderPage(result);
            }).fail(function () {
                alert("暂时无法获取数据");
            });
        },
        render: function (result) {
            var html = "";
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += DealerList.template(data);
            }
            var table = $(".dealer-table");
            table.find("tbody").html(html);

            table.find("tr").each(function () {
                $(this).find(".btn-dealer-refuse").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "danger",
                        "confirm": "拒绝",
                        "content": "是否确认拒绝这个分销商的审核",
                        callback: function () {
                            DealerList.delete(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-block").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "danger",
                        "confirm": "冻结",
                        "content": "是否确定冻结这个分销商",
                        callback: function () {
                            DealerList.block(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-active").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "success",
                        "confirm": "解冻",
                        "content": "是否确定解冻这个分销商",
                        callback: function () {
                            DealerList.unblock(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-pass").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "success",
                        "confirm": "通过",
                        "content": "是否确定通过这个分销商的审核",
                        callback: function () {
                            DealerList.unblock(id);
                        }
                    });
                });
            });
        },
        renderPage: function (result) {
            DealerList.page.pages = [];
            DealerList.page.totalPages = parseInt(result.total / result.limit);
            if (DealerList.page.totalPages * result.limit < result.total) {
                DealerList.page.totalPages += 1;
            }
            var start = result.page > 3 ? result.page - 2 : 1;
            var end = start + 4 > DealerList.page.totalPages ? DealerList.page.totalPages : start + 4;
            for (var i = 1; i <= end; i++) {
                DealerList.page.pages.push({no: i, active: i == result.page ? "active" : ""});
            }
            var html = DealerList.pagination(DealerList.page);
            $(".pagination-wrap").html(html);
        },
        block: function (id) {
            $.ajax({
                url: "/ajax/dealer/" + id + "/block",
                type: 'PUT',
                dataType: 'json'
            }).done(function (result) {
                //todo: anything else?
                DealerList.search();
            }).fail(function (result) {
                console.log(result);
            });
        },
        unblock: function (id) {
            $.ajax({
                url: "/ajax/dealer/" + id + "/unblock",
                type: 'PUT',
                dataType: 'json'
            }).done(function (result) {
                //todo: anything else?
                DealerList.search();
            }).fail(function (result) {
                console.log(result);
            });
        },
        delete: function (id) {
            $.ajax({
                url: "/ajax/dealer/" + id,
                type: 'delete',
                dataType: 'json'
            }).done(function (result) {
                //todo: anything else?
                DealerList.search();
            }).fail(function (result) {
                console.log(result);
            });
        }
    };

    DealerList.init();
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

Handlebars.registerHelper('translateLevel', function (context, options) {
    if (context == "LEVEL2") {
        return " 二级";
    } else if (context == "LEVEL3") {
        return "三级";
    } else if (context == "LEVEL4") {
        return "四级";
    }
});
