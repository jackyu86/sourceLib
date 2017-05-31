"use strict";

/* global createDynamicGroup createElement DynamicForm Handlebars ProductAgeField ProductLiability ProductNumberField ProductPlanPeriod*/
var PLP = {
    forms: {},
    init: function (id) {
        var product = $("#product-" + id);
        var formData = JSON.parse(product.find(".content > script").html());
        var myForm = new DynamicForm(formData, product.find(".attribute-wrap")[0]);
        myForm.formBuilder(function (form) {
            var render = Handlebars.compile(document.querySelector("#tpl_plp_form").innerHTML);
            var html = render(form);
            return createElement(html);
        }).groupBuilder("common", function (groupAttribute, value) {
            return createDynamicGroup("#tpl_plp_group", groupAttribute, value);
        }).groupBuilder("liability", function (groupAttribute, value) {
            return createDynamicGroup("#tpl_attribute_group_liability", groupAttribute, value);
        }).fieldTemplate("planpayment.select", "#tpl_product_payment_select")
            .fieldBuilder("liability", ProductLiability)
            .fieldBuilder("age", ProductAgeField)
            .fieldBuilder("integer", ProductNumberField)
            .fieldBuilder("planperiod", ProductPlanPeriod)
            .fieldBuilder("planstarttime", ProductPlanstarttime)
            .onInit(function () {
                getPrice(myForm);
            }).onChange(function (input, node) {
            if (this.heavySickRule) {
                if (node.name === "birthDate") {
                    this.heavySickRule();
                } else if (node.attribute.type.toLowerCase() === "liability") {
                    for (var index = 0; index < myForm.group(node.groupName).fields.length; index += 1) {
                        var field = myForm.group(node.groupName).fields[index];
                        if (field.attribute.type.toLowerCase() === "liability") {
                            field.val(input);
                        }
                    }
                }
            }
            if (myForm.validate()) {
                getPrice(myForm);
            }
        }.bind(this)).withRule("heavySick", function (data) {
            for (var key in data) {
                var groupFieldName = key.split(".");
                var field = myForm.group(groupFieldName[0]).field(groupFieldName[1]);
                var rules = data[key];
                var rule = field.getRule(rules);
                if (rule && rule.rule) {
                    for (var targetKey in rule.rule) {
                        var targetGroupFieldName = targetKey.split(".");
                        var targetRule = rule.rule[targetKey];
                        var targetField = myForm.group(targetGroupFieldName[0]).field(targetGroupFieldName[1]);
                        console.log(targetField.name);
                        targetField.refreshFieldValues(targetRule);
                    }
                }
            }
        });

        function getPrice(form) {
            $.ajax({
                url: "/ajax/product/" + product.find(".product-id-container").val() + "/price",
                type: "put",
                data: JSON.stringify(form.val()),
                headers: {"Content-Type": "application/json"},
                dataType: "json"
            }).done(function (result) {
                product.find(".product-price-container").text(result.price);
                product.find(".product-sale-price-container").text(result.sale_price);
            }).fail(function (result) {
                console.log(result);
            });
        }

        myForm.init();

        this.forms[id] = myForm;

        product.find(".buy").click(function () {
            var data = {};
            data.productId = product.find(".product-id-container").val();
            data.value = myForm.val();
            $.ajax({
                url: "/ajax/checkout",
                type: "post",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "json"
            }).done(function (result) {
                window.location.href = "/checkout?id=" + result.checkoutId;
            }).fail(function () {
                $.alert({
                    size: "small",
                    content: "提交失败"
                });
            });
        });

        product.find(".header-link a").click(function (event) {
            window.location.href = $(event.currentTarget).prop("href") + "?" + PLP.serialize(myForm.group("liability").val());
            return false;
        });

        var productRule = product.find(".product-rule");
        if (productRule.length > 0 && productRule.text()) {
            if (productRule.data("funcname") === "cascade") {
                myForm.ruleCascade(JSON.parse(productRule.text()));
            } else if (productRule.data("funcname") === "heavySick") {
                this.heavySickRule = function () {
                    myForm.rule("heavySick", JSON.parse(productRule.text()));
                };
            }

        }

    },
    serialize: function (json) {
        var str = "";
        for (var key in json) {
            if (str !== "") {
                str += "&";
            }
            str += "liability." + key + "=" + json[key];
        }
        return str;
    },
    form: function (id) {
        return this.forms[id];
    }

};
