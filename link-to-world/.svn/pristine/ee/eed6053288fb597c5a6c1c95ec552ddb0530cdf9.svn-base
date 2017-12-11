"use strict";

/* global createDynamicGroup DynamicForm DynamicGroup Handlebars createElement CheckoutIdField*/
$(function () {
    var Form = {
        myForm: null,
        init: function () {
            Form.initForm();
            if (this.myForm) {
                this.nav = new SideNav(this.myForm, false);
            }
        },
        initForm: function () {
            var data = JSON.parse($(".form-container>script").text());
            var myForm = new DynamicForm(data, ".form-container", 'checkout_information_form');

            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector('#tpl_checkout_information_form').innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("common", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector('#tpl_preview_group').innerHTML);
                var html = render(groupAttribute);
                group.element = createElement(html);
                return group;
            }).groupBuilder("insurant", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector('#tpl_checkout_information_customer_group').innerHTML);
                var html = render(groupAttribute);
                group.element = createElement(html);
                return group;
            }).groupBuilder("information", function (groupAttribute, value) {
                var group = createDynamicGroup("#tpl_preview_information_group", groupAttribute, value);
                group.fieldBuilders["boolean"] = function (fieldAttribute, fieldValue) {
                    var render = Handlebars.compile(document.querySelector("#tpl_preview_information").innerHTML);
                    var html = render(fieldAttribute);
                    var field = new DynamicField(fieldAttribute, fieldValue);
                    field.element = createElement(html);
                    field.val = function (setValue) {
                        if (setValue) {
                            if (setValue === "true") {
                                field.element.querySelector(".static-value").innerText = "是";
                            } else {
                                field.element.querySelector(".static-value").innerText = "否";
                            }
                            return;
                        }
                        if (field.element.querySelector(".static-value").innerText === "是") {
                            return true;
                        } else {
                            return false;
                        }
                    };
                    field.init();
                    return field;
                };
                group.fieldBuilders["string"] = function (fieldAttribute, fieldValue) {
                    var render = Handlebars.compile(document.querySelector("#tpl_preview_information").innerHTML);
                    var html = render(fieldAttribute);
                    var field = new DynamicField(fieldAttribute, fieldValue);
                    field.element = createElement(html);
                    field.init();
                    return field;
                };
                group.fieldBuilders["effectiveinsurance"] = function (fieldAttribute, fieldValue) {
                    var render = Handlebars.compile(document.querySelector("#tpl_preview_information").innerHTML);
                    var html = render(fieldAttribute);
                    var field = new DynamicField(fieldAttribute, fieldValue);
                    field.element = createElement(html);
                    field.val = function (setValue) {
                        if (setValue) {
                            var html = "";
                            for (var valueIndex = 1; valueIndex <= 5; valueIndex += 1) {
                                var data = setValue["fill" + valueIndex];
                                if (data.company && data.amount) {
                                    html += "<p>" + data.company + ": " + data.amount + "万元</p>";
                                }
                            }
                            field.element.querySelector(".static-value").innerHTML = html;
                        }
                    };
                    field.init();
                    return field;
                };
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
                    var legalBeneficiaryField = myForm.group("beneficiary").field("legalBeneficiary");
                    if (legalBeneficiaryField.name === "legalBeneficiary" && legalBeneficiaryField.val() === "Y") {
                        var index = 0;
                        var beneficiaryGroup = myForm.group(legalBeneficiaryField.groupName);
                        for (; index < beneficiaryGroup.fields.length; index += 1) {
                            var beneficiaryField = beneficiaryGroup.fields[index];
                            if (beneficiaryField.name !== "legalBeneficiary" && beneficiaryField.name !== "type") {
                                beneficiaryField.hide();
                            }
                        }
                    }
                    for (var i = 0; i < myForm.groups.length; i += 1) {
                        var group = myForm.groups[i];
                        if (!group.group.optional) {
                            continue;
                        }
                        var flag = false;
                        for (var j = 0; j < group.fields.length; j += 1) {
                            var field = group.fields[j];
                            if (field.name === "address") {
                                if (field.val().province) {
                                    flag = true;
                                }
                            } else if (field.val()) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            group.element.style.display = "none";
                        }
                    }
                    getPrice(myForm);

                }).init();

            this.myForm = myForm;

            $(".order-main").show();
            adjustFooter();
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
                    if (result.price !== result.sale_price) {
                        $(".total-price").find(".amount").text(result.price).show();
                    }
                    $(".sale-price").find(".amount").text(result.sale_price);
                    $(".invoice-fee").find(".amount").text(result.invoice_fee);
                    $(".total-sale-price").find(".amount").text(result.total);
                }).fail(function (result) {
                    console.log(result);
                });
            }

            $(".btn-submit").click(function () {
                var orderId = $("#orderId").val();
                var checkoutId = $("#checkoutId").val();
                if (!myForm.validate()) {
                    return;
                }
                if (orderId) {
                    $.ajax({
                        url: '/ajax/order/' + orderId + "/save",
                        type: 'PUT',
                        contentType: 'application/json',
                        dataType: 'json'
                    }).done(function (result) {
                        window.location.href = '/checkout/pay?id=' + result.paymentId;
                    }).fail(function () {
                        alert("提交失败");
                    });

                } else {
                    $.ajax({
                        url: '/ajax/order?id=' + checkoutId,
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json'
                    }).done(function (result) {
                        window.location.href = '/checkout/pay?id=' + result.paymentId;
                    }).fail(function () {
                        alert("提交失败");
                    });

                }
            });
            $(".btn-cart").click(function () {
                var data = {};
                data.productId = $("#product-id").val();
                data.productName = $("#product-name").val();
                data.form = myForm.form.value;
                $.ajax({
                    url: '/ajax/cart',
                    type: "post",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: 'json'
                }).done(function (result) {
                    window.location.href = '/account/cart';
                }).fail(function () {
                    alert("提交失败");
                });
            });
            $(".btn-return").click(function () {
                window.location.href = "/checkout?id=" + $("#checkoutId").val();
            });
        }
    };
    Form.init();
    window.form = Form;
});
