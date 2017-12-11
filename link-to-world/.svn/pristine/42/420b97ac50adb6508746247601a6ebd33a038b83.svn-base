"use strict";

$(function () {
    var Order = {
        init: function () {
            var data = JSON.parse($(".form-container>script").text());
            var myForm = new DynamicForm(data, ".form-container", 'checkout_information_form');

            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector('#tpl_order_detail_form').innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("insurant", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector('#tpl_checkout_information_customer_group').innerHTML);
                var html = render(groupAttribute);
                group.element = createElement(html);
                return group;
            }).groupBuilder("insured", function (groupAttribute, value) {
                if (groupAttribute.multiple) {
                    var group = createDynamicGroup("#tpl_preview_multiple_group", groupAttribute, value[0]);

                    group.additionalGroup = function (additionalGroupAttribute, additionalValue) {
                        return createDynamicGroup("#tpl_preview_multiple_group_additional", additionalGroupAttribute, additionalValue);
                    };
                    if (value.length > 1) {
                        for (var index = 1; index < value.length; index += 1) {
                            var newGroup = createDynamicGroup("#tpl_preview_multiple_group_additional", groupAttribute, value[index]);
                            newGroup.index = $(group).find(".field-list").length - 1;
                            group.fieldBuilders = myForm.fieldBuilders;
                            newGroup.build();
                            myForm.groups.push(newGroup);
                            var fieldList = $(group.element).find(".field-list");
                            fieldList.eq(fieldList.length - 1).before(newGroup.element);
                        }
                    }
                    return group;
                }
                return createDynamicGroup("#tpl_preview_multiple_group", groupAttribute, value);
            }).groupBuilder("emergency-contact", function (groupAttribute, value) {
                if (groupAttribute.multiple) {
                    var group = createDynamicGroup("#tpl_preview_multiple_group", groupAttribute, value[0]);

                    group.additionalGroup = function (additionalGroupAttribute, additionalValue) {
                        return createDynamicGroup("#tpl_preview_multiple_group_additional", additionalGroupAttribute, additionalValue);
                    };
                    if (value.length > 1) {
                        for (var index = 1; index < value.length; index += 1) {
                            var newGroup = createDynamicGroup("#tpl_preview_multiple_group_additional", groupAttribute, value[index]);
                            newGroup.index = $(group).find(".field-list").length - 1;
                            group.fieldBuilders = myForm.fieldBuilders;
                            newGroup.build();
                            myForm.groups.push(newGroup);
                            var fieldList = $(group.element).find(".field-list");
                            fieldList.eq(fieldList.length - 1).before(newGroup.element);
                        }
                    }
                    return group;
                }
                return createDynamicGroup("#tpl_preview_multiple_group", groupAttribute, value);

            }).fieldBuilder('id', CheckoutIdField)
                .onInit(function () {
                    getPrice(myForm);

                }).init();

            this.myForm = myForm;

            $(".order-main").show();
            function getPrice(form) {
                var formData = form.form.value;
                $.ajax({
                    url: '/ajax/product/' + $("#product-id").val() + '/price',
                    type: 'put',
                    data: JSON.stringify(formData),
                    headers: {
                        "Content-Type": 'application/json'
                    },
                    dataType: 'json'
                }).done(function (result) {
                    $(".summary-item").find(".cost").text(result.price);
                    $(".total-price").find(".amount").text(result.sale_price);
                    $(".total-sale-price").find(".amount").text(result.total);
                }).fail(function (result) {
                    console.log(result);
                });
            }

            this.initStatus();

        },
        initStatus: function () {
            var status = $(".summary-sub-cell.status").find(".status-en").val();
            var statusName = "";
            switch (status) {
                case "DRAFT":
                    statusName = "拟定";
                    break;
                case "PAYMENT_PENDING":
                    statusName = "待支付";
                    break;
                case "PAYMENT_FAILED":
                    statusName = "支付失败";
                    break;
                case "PAYMENT_COMPLETED":
                    statusName = "支付成功";
                    break;
                case "AUDITING":
                    statusName = "核保";
                    break;
                case "VENDOR_INSURED":
                    statusName = "已承保";
                    break;
                case "VENDOR_REJECT":
                    statusName = "拒保";
                    break;
                case "DOCUMENTED":
                    statusName = "已出单";
                    break;
                case "SURRENDERING":
                    statusName = "请求退保";
                    break;
                case "SURRENDERED":
                    statusName = "已退保";
                    break;
                case "REFUND":
                    status = "已退款";
                    break;
            }
            $(".summary-sub-cell.status").text(statusName);
        }

    };
    Order.init();
});

var x = null;