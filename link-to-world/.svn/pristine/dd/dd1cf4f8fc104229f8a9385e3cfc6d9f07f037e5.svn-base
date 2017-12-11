"use strict";

$(function() {
    var Cart = {
        init: function() {
            var total = 0;
            $("[name=selected-item][checked]").each(function() {
                total += parseFloat($(this).data("price"));
            });
            $("#total-price").text(total.toFixed(2));
            $("tbody .checkbox").click(function() {
                requestAnimationFrame(function() {
                    var checked = $("[name=selected-item][checked]");
                    var checkedCount = checked.length;
                    $(".selected-count").text(checkedCount);
                    var allBox = $("[name='selected-all']");
                    if (checkedCount === $("[name=selected-item]").length) {
                        allBox.prop("checked", true);
                        allBox.attr("checked", "checked");
                    } else {
                        allBox.prop("checked", false);
                        allBox.attr("checked", null);
                    }
                    var total = 0;
                    checked.each(function() {
                        total += parseFloat($(this).data("price"));
                    });
                    $("#total-price").text(total.toFixed(2));
                });
            });
            $("[name='selected-all']").parent().click(function() {
                requestAnimationFrame(function() {
                    if ($("[name='selected-all']").attr("checked")) {
                        $("[name=selected-item]").each(function() {
                            if (!$(this).attr("checked")) {
                                $(this).parent().find(".checkbox-label").click();
                            }
                        });
                    } else {
                        $("[name=selected-item]").each(function() {
                            if ($(this).attr("checked")) {
                                $(this).parent().find(".checkbox-label").click();
                            }
                        });
                    }
                });
            });
            $(".cart-item-delete").click(function() {
                var itemId = $(this).parents("tr").data("id");
                $.confirm({
                    "size": "small",
                    "level": "danger",
                    "confirm": "删除",
                    "content": "是否从购物车删除",
                    callback: function() {
                        Cart.delete(itemId);
                    }
                });
            });
            $(".cart-submit-button").click(function() {
                Cart.submit();
            });

        },
        delete: function(id) {
            var data = {itemIds: []};
            data.itemIds.push(id);
            $.ajax({
                url: "/ajax/cart/delete-item",
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function() {
                window.location.reload();
            }).fail(function() {
                $.alert({
                    "size": "small",
                    "content": "删除失败"
                });
            });
        },
        submit: function() {
            var data = {};
            var cartItemIds = [];
            $("input[name='selected-item']").each(function() {
                var self = $(this);
                if (self.prop("checked"))
                    cartItemIds.push(self.val());
            });
            data.cartItemIds = cartItemIds;
            if (cartItemIds.length == 0) {
                alert("请选择产品");
                return false;
            }
            $.ajax({
                url: "/ajax/order/from-cart",
                type: "post",
                data: JSON.stringify(data),
                contentType: 'application/json',
                dataType: 'json'
            }).done(function(result) {
                window.location.href = '/checkout/pay?id=' + result.paymentId;
            }).fail(function(result) {
                if (result.invalidFields) {
                    alert(result.invalidFields.name + "核保出错, 请修改");
                } else {
                    alert("提交失败");
                }
            });
        }
    };

    Cart.init();
});