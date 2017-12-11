"use strict";

$(function () {
    var PDP = {
        Form: null,
        init: function () {
            this.initEvent();
            this.initQNAPagination();
            this.initData();
        },
        initEvent: function () {
            $(".nav-tabs a").click(function (event) {
                event.preventDefault();
                $(event.currentTarget).tab("show");
            });
            $(window).unbind("scroll").scroll(function () {
                if ($("body").scrollTop() > $(".detail-section").offset().top) {
                    $(".detail-section").addClass("fixed");
                } else {
                    $(".detail-section").removeClass("fixed");
                }
            })
        },
        initQNAPagination: function () {
            var pageSize = 5;
            var questions = $(".qna-list").find(".qna");
            var pages = parseInt(questions.length / pageSize, 10);
            if (pages * pageSize === questions.length) {
                pages = pages - 1;
            }
            var html = "";
            if (pages > 0) {
                for (var i = 0; i <= pages; i++) {
                    html += "<span class='qna-page' data-page='" + i + "'>" + (i + 1) + "</span>";
                }
                $(".qna-pagination").html(html);
                $(".qna-page").click(function (event) {
                    $(".qna-page").removeClass("active");
                    $(event.currentTarget).addClass("active");
                    var page = parseInt($(event.currentTarget).data("page"));
                    questions.each(function () {
                        $(this).hide();
                        if ($(this).index() / pageSize >= page && $(this).index() / pageSize < page + 1) {
                            $(this).show();
                        }
                    });
                }).eq(0).click();
            }

        },
        initData: function () {
            var formData = JSON.parse($(".content > script").text());
            var myForm = new DynamicForm(formData, $(".content")[0]);
            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector('#tpl_pdp_form').innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("common", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector('#tpl_pdp_group').innerHTML);
                var html = render(groupAttribute);
                group.element = createElement(html);
                return group;
            }).groupBuilder("liability", function (groupAttribute, value) {
                return createDynamicGroup("#tpl_attribute_group_liability", groupAttribute, value);
            }).fieldTemplate("birthDate", "#tpl_pdp_date")
                .fieldTemplate("payment.select", "#tpl_pdp_payment_select")
                .fieldBuilder("serial", ProductSerialField)
                .fieldBuilder("age", ProductAgeField)
                .fieldBuilder("unit", ProductUnitField)
                .fieldBuilder("integer", ProductNumberField)
                .fieldBuilder("planperiod", ProductPlanPeriod)
                .fieldBuilder("planstarttime", ProductPlanstarttime)
                .fieldBuilder("liability", ProductLiability)
                .onInit(function () {
                    getPrice(myForm);
                    for (var index = 0; index < myForm.group("liability").fields.length; index += 1) {
                        var field = myForm.group("liability").fields[index];
                        $("#" + field.name + "-value").text(field.val());
                    }

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
                $("#" + node.name + "-value").text(input);

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
                var formData = form.val();
                $.ajax({
                    url: '/ajax/product/' + $("#pdp-product-id").val() + '/price',
                    type: 'put',
                    data: JSON.stringify(formData),
                    headers: {
                        "Content-Type": 'application/json'
                    },
                    dataType: 'json'
                }).done(function (result) {
                    $(".product-price-container").text(result.price);
                    $(".product-sale-price-container").text(result.sale_price);
                }).fail(function (result) {
                    console.log(result);
                });
            }

            myForm.init();

            $(".submit-button").click(function () {
                var data = {};
                data.productId = $("#pdp-product-id").val();
                data.value = myForm.val();
                $.ajax({
                    url: '/ajax/checkout',
                    type: 'post',
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: 'json'
                }).done(function (result) {
                    window.location.href = '/checkout?id=' + result.checkoutId;
                }).fail(function () {
                    alert("提交失败");
                });
            });
            var fields = myForm.group("liability").fields;
            for (var j = 0; j < fields.length; j++) {
                fields[j].onChange(function (value, field) {
                    $("#" + field.name + "-value").text(parseNumber(value));
                });
            }
            var productRule = $(".product-rule");
            if (productRule.length > 0 && productRule.text()) {
                if (productRule.data("funcname") === "cascade") {
                    myForm.ruleCascade(JSON.parse(productRule.text()));
                } else if (productRule.data("funcname") === "heavySick") {
                    this.heavySickRule = function () {
                        myForm.rule("heavySick", JSON.parse(productRule.text()));
                    };
                }
            }
        }
    };
    PDP.init();
});
