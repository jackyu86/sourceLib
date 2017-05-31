"use strict";

var CheckoutInsuredGroup = function (groupAttribute, value) {
    var group = null;
    if (groupAttribute.multiple) {
        group = createDynamicGroup("#tpl_checkout_information_insured_group", groupAttribute, value[0]);
    } else {
        group = createDynamicGroup("#tpl_checkout_information_insured_group", groupAttribute, value);
    }
    group.uploadValue = function (titleList, dataList, thisGroup) {
        var data = {};
        for (var i = 0; i < titleList.length; i++) {
            if (titleList[i] === "id") {
                var values = dataList[i].split(":");
                data.id = {type: values[0], number: values[1]};
            } else {
                data[titleList[i]] = dataList[i];
            }
        }
        if ($(thisGroup.element).find(".field-list").length > 0) {
            $(thisGroup.element).find(".field-list").eq(0).find("[name=insurant]").prop("checked", false).removeAttr("checked");
            $(thisGroup.element).find(".field-list").eq(0).find("[name=insurant][value=2]").prop("checked", true).attr("checked", "checked");
        } else {
            $(thisGroup.element).find("[name=insurant]").prop("checked", false).removeAttr("checked");
            $(thisGroup.element).find("[name=insurant][value=2]").prop("checked", true).attr("checked", "checked");

        }
        thisGroup.val(data);
    };

    $(group.element).find(".upload-insured").change(function (event) {
        var files = event.target.files;
        Papa.parse(files[0], {
            complete: function (result) {
                group.uploadValue(result.data[0], result.data[1], group);
                if (result.data.length >= 2) {
                    for (var i = 2; i < result.data.length; i++) {
                        var newGroup = group.addGroup();
                        group.uploadValue(result.data[0], result.data[i], newGroup);
                    }
                } else {
                    alert("文件内容错误");
                }
            },
            error: function (a, b, c, d) {
                alert("文件格式错误");
            }
        });
    });

    if (groupAttribute.multiple) {
        group.additionalGroup = function (additionalGroupAttribute, additionalValue) {
            var additionalGroup = createDynamicGroup("#tpl_checkout_information_insured_group_additional", additionalGroupAttribute, additionalValue);
            additionalGroup.index = $(group.element).find(".field-list").length - 1;
            additionalGroup.onChange(myForm.onChangeHandler).editable(myForm.editableHandler).build();
            $(additionalGroup.element).find(".btn-delete").click(function () {
                var amount = parseInt($(".customer .amount").eq(0).text(), 10) - 1;
                $(".customer .amount").text(amount);
                $(".total-unit .amount").text(amount * parseInt(myForm.group("product").field("unit").val(), 10));

                $(additionalGroup.element).remove();
                for (var i = 0; i < myForm.groups.length; i++) {
                    if (myForm.groups[i].name === additionalGroup.name && myForm.groups[i].index === additionalGroup.index) {
                        myForm.groups.splice(i, 1);
                        break;
                    }
                }
                this.getPrice(myForm.val());
            }.bind(this));
            $(group.element).find("[name='insurant'][value='1']").parent().click(function () {
                if ($(this).find("input").prop("checked") || $(this).find("input").attr("checked")) {
                    return;
                }
                $.getJSON("/ajax/user/info", function (result) {
                    additionalGroup.val(result);
                });
            });

            $(additionalGroup.element).find(".upload-insured").change(function (event) {
                var files = event.target.files;
                Papa.parse(files[0], {
                    complete: function (result) {
                        group.uploadValue(result.data[0], result.data[1], additionalGroup);
                        if (result.data.length >= 2) {
                            for (var i = 2; i < result.data.length; i++) {
                                var newGroup = group.addGroup();
                                group.uploadValue(result.data[0], result.data[i], newGroup);
                            }
                        } else {
                            alert("文件内容错误");
                        }
                    },
                    error: function (a, b, c, d) {
                        alert("文件格式错误");
                    }
                });
            });

            return additionalGroup;
        }.bind(this);
        $(group.element).find(".add-group").click(function () {
            group.addGroup();
            this.getPrice(myForm.val());
        }.bind(this));

        group.addGroup = function () {
            var amount = parseInt($(".customer .amount").eq(0).text(), 10) + 1;
            $(".customer .amount").text(amount);
            $(".total-unit .amount").text(amount * parseInt(myForm.group("product").field("unit").val(), 10));

            var newGroup = group.additionalGroup(groupAttribute, {});
            myForm.groups.push(newGroup);

            $(group.element).find(".add-group").parents(".field-list").before(newGroup.element);
            return newGroup;
        };
    }

    if (value && value.length > 1) {
        for (var i = 1; i < value.length; i++) {
            var amount = parseInt($(".customer .amount").eq(0).text(), 10) + 1;
            $(".customer .amount").text(amount);
            $(myForm.element).find(".customer .amount").text(amount);
            var newGroup = group.additionalGroup(groupAttribute, value[i]);
            myForm.groups.push(newGroup);
            var fieldList = $(group.element).find(".field-list");
            fieldList.eq(fieldList.length - 1).before(newGroup.element);
        }
    }
    return group;
};