$(function () {
    var PolicyHolder = {
        init: function () {
            $(".delete-btn").click(function () {
                var id = $(this).data("id");
                $.confirm({
                    "size": "small",
                    "level": "danger",
                    "confirm": "删除",
                    "content": "是否确定删除这个常用投保人",
                    "callback": function () {
                        PolicyHolder.delete(id);
                    }
                });
            });

            $(".create-btn").click(function () {
                var policyHoldersSize = $("#policyHoldersSize").val();
                if (policyHoldersSize >= 8) {
                    $.alert({
                        "content": "常用投保人最多只能添加8个"
                    });
                    return;
                }
                window.location.href = "/account/policy-holder/create";
            })
        },

        delete: function (id) {
            $.ajax({
                url: '/ajax/policy-holder/' + id,
                type: 'delete',
                contentType: 'application/json'
            }).done(function () {
                window.location.href = "/account/policy-holder";
            });
        }
    };
    PolicyHolder.init();
});