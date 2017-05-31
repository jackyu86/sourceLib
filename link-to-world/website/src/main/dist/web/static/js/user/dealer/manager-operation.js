"use strict";

$(function () {
    var Manager = {
        template: Handlebars.compile($("#tpl-manager-operation").html()),
        dealerId: "",
        init: function () {
            var dealerId = $("#dealerId").val(),
                $this = this;
            $this.dealerId = dealerId;
            $.ajax({
                url: "/ajax/dealer/" + dealerId + "/credit",
                type: "get",
                dataType: 'json'
            }).done(function (result) {
                $this.render(result);
            }).fail(function () {
                alert("暂时无法获取数据");
            });
        },
        render: function (result) {
            var $this = this;
            $("#manager-operation").html(this.template(result));
            $(".updateQuota").click(function () {
                $this.updateQuota();
            });
            $(".resetQuota").click(function () {
                $this.resetQuota();
            });
            $(".updateQuotaStatus").click(function () {
                $this.updateQuotaStatus();
            });
        },
        updateQuota: function () {
            var data = {};
            var input = $("input[name='quota']");
            data.id = this.dealerId;
            data.quota = input.val();
            input.parent().removeClass("has-error").find("label").remove();
            $.ajax({
                url: "/ajax/dealer/" + data.id + "/update/quota",
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).success(function () {
                $.prompt({
                    "size": "small",
                    "content": "设定成功",
                    callback: function () {
                        window.location.reload();
                    }
                });
            }).error(function (response) {
                var e = response.responseJSON;
                if (e.invalidFields) {
                    var invalidField = e.invalidFields[0];
                    input.parent().append('<label class="text-danger validation-message">' + invalidField.message + '</label>');
                    input.parent().addClass("has-error");
                }
            });
        },
        resetQuota: function () {
            var data = {};
            var input = $("input[name='quota']");
            data.id = this.dealerId;
            input.parent().removeClass("has-error").find("label").remove();
            $.ajax({
                url: "/ajax/dealer/" + data.id + "/reset/quota",
                type: "put",
                contentType: "application/json",
                dataType: "json"
            }).success(function () {
                $.prompt({
                    "size": "small",
                    "content": "回销成功",
                    callback: function () {
                        window.location.reload();
                    }
                });
            }).error(function (response) {
                var e = response.responseJSON;
                if (e.invalidFields) {
                    var invalidField = e.invalidFields[0];
                    input.parent().append('<label class="text-danger validation-message">' + invalidField.message + '</label>');
                    input.parent().addClass("has-error");
                }
            });
        },
        updateQuotaStatus: function () {
            var data = {};
            var input = $("input[name='quota']");
            data.id = this.dealerId;
            input.parent().removeClass("has-error").find("label").remove();
            $.ajax({
                url: "/ajax/dealer/" + data.id + "/quota/status",
                type: "put",
                contentType: "application/json",
                dataType: "json"
            }).success(function () {
                $.prompt({
                    "size": "small",
                    "content": "操作成功",
                    callback: function () {
                        window.location.reload();
                    }
                });
            }).error(function (response) {
                var e = response.responseJSON;
                if (e.invalidFields) {
                    var invalidField = e.invalidFields[0];
                    input.parent().append('<label class="text-danger validation-message">' + invalidField.message + '</label>');
                    input.parent().addClass("has-error");
                }
            });
        }
    };

    Manager.init();
});

Handlebars.registerHelper('notAllowedNull', function (obj, options) {
    if (obj === undefined) {
        return options.fn(this);
    }
    return options.inverse(this);
});

Handlebars.registerHelper('isActive', function (status, options) {
    if (status == 'ACTIVE') {
        return options.fn(this);
    }
    return options.inverse(this);
});

Handlebars.registerHelper('translateStatus', function (status, options) {
    if (status == 'ACTIVE') {
        return "正常使用";
    }
    return "账号锁定";
});

Handlebars.registerHelper('sub', function (first, second, options) {
    return first - second;
});