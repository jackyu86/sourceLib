$(function () {
    var DealerUser = {
        page: {
            page: 1,
            limit: 10,
            totalPages: 1,
            pages: [{no: 1, active: "active"}]
        },
        template: Handlebars.compile($("#tpl-dealer-user-item").html()),
        pagination: Handlebars.compile($("#tpl-pagination").html()),
        init: function () {
            $(".pagination-wrap").on("click", ".pagination-item", function () {
                if ($(this).hasClass("pagination-prev")) {
                    DealerUser.page.page -= 1;
                } else if ($(this).hasClass("pagination-next")) {
                    DealerUser.page.page += 1;
                } else {
                    DealerUser.page.page = $(this).text();
                }
                DealerUser.search();
            });
            DealerUser.search();
        },
        search: function () {
            $.ajax({
                url: '/ajax/dealer/user',
                type: 'put',
                data: JSON.stringify(DealerUser.page),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (result) {
                DealerUser.render(result);
                DealerUser.renderPage(result);
            }).fail(function () {
                alert("暂时无法获取数据");
            });
        },
        render: function (result) {
            var html = "";
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += DealerUser.template(data);
            }
            var table = $(".dealer-table");
            table.find("tbody").html(html);

            table.find("tr").each(function () {
                $(this).find(".btn-dealer-remove").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "danger",
                        "confirm": "删除",
                        "content": "是否确认删除这个出单员",
                        callback: function () {
                            DealerUser.delete(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-refuse").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "danger",
                        "confirm": "拒绝",
                        "content": "是否确认拒绝这个分销商的审核",
                        callback: function () {
                            DealerUser.delete(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-block").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "danger",
                        "confirm": "冻结",
                        "content": "是否确定冻结这个出单员",
                        callback: function () {
                            DealerUser.block(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-active").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "success",
                        "confirm": "解冻",
                        "content": "是否确定解冻这个出单员",
                        callback: function () {
                            DealerUser.unblock(id);
                        }
                    });
                });
                $(this).find(".btn-dealer-pass").click(function () {
                    var id = $(this).data("id");
                    $.confirm({
                        "size": "small",
                        "level": "success",
                        "confirm": "通过",
                        "content": "是否确定通过这个出单员的审核",
                        callback: function () {
                            DealerUser.unblock(id);
                        }
                    });
                });
                $(this).find(".btn-reset-pay-password").click(function () {
                    $("#dealer-user-id").val($(this).data("id"));
                });
                $(".pay-password-reset-button").click(function () {
                    DealerUser.resetPayPassword($("#dealer-user-id").val());
                });
            });
        },
        renderPage: function (result) {
            DealerUser.page.pages = [];
            DealerUser.page.totalPages = parseInt(result.total / result.limit);
            if (DealerUser.page.totalPages * result.limit < result.total) {
                DealerUser.page.totalPages += 1;
            }
            var start = result.page > 3 ? result - 2 : 1;
            var end = start + 4 > DealerUser.page.totalPages ? DealerUser.page.totalPages : start + 4;
            for (var i = 1; i <= end; i++) {
                DealerUser.page.pages.push({no: i, active: i == result.page ? "active" : ""});
            }
            var html = DealerUser.pagination(DealerUser.page);
            $(".pagination-wrap").html(html);
        },
        block: function (id) {
            $.ajax({
                url: "/ajax/dealer/user/" + id + "/block",
                type: 'PUT',
                dataType: 'json'
            }).done(function (result) {
                DealerUser.search();
            }).fail(function (result) {
                console.log(result);
            });
        },
        unblock: function (id) {
            $.ajax({
                url: "/ajax/dealer/user/" + id + "/unblock",
                type: 'PUT',
                dataType: 'json'
            }).done(function (result) {
                DealerUser.search();
            }).fail(function (result) {
                console.log(result);
            });
        },
        delete: function (id) {
            $.ajax({
                url: "/ajax/dealer/user/" + id,
                type: 'DELETE',
                dataType: 'json'
            }).done(function (result) {
                //todo: anything else?
                DealerUser.search();
            }).fail(function (result) {
                console.log(result);
            });
        },
        resetPayPassword: function (id) {
            var form = $("#change-form");
            if (!form.valid()) {
                return false;
            }
            var formData = form.serializeArray();
            var data = {};
            for (var index = 0; index < formData.length; index += 1) {
                data[formData[index].name] = formData[index].value;
            }
            $.ajax({
                url: "/ajax/dealer/user/" + id + "/self/pay-password",
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function () {
                $("#change-form").hide();
                $("#success-form").show();
            }).fail(function (result) {
                var e = result.responseJSON;
                if (e.invalidFields && e.invalidFields.length) {
                    for (var fieldIndex = 0; fieldIndex < e.invalidFields.length; fieldIndex += 1) {
                        var invalidField = e.invalidFields[fieldIndex];
                        var $invalidError = $("#" + invalidField.path + "-invalid");
                        $invalidError.text(invalidField.message);
                        $invalidError.show();
                    }
                } else {
                    $.alert({
                        "size": "small",
                        "content": "更新失败"
                    });
                }
            });
        }
    };

    DealerUser.init();
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
