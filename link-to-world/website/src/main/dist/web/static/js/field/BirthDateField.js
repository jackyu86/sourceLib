"use strict";

/* global CommonUtils DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.birthdate = function (fieldAttribute, value) {
    var templateId = "#tpl_attribute_date";
    if (DynamicFieldTemplate[fieldAttribute.name]) {
        templateId = DynamicFieldTemplate[fieldAttribute.name];
    }
    var render = Handlebars.compile(document.querySelector(templateId).innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.NotNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "NotNull";
                }
            }
            return null;
        };
    }
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (setValue) {
        if (setValue) {
            if (field.element.querySelector(".static-value")) {
                if (setValue.indexOf("T") >= 0) {
                    field.element.querySelector(".static-value").innerText = setValue.substr(0, setValue.indexOf("T"));
                } else {
                    field.element.querySelector(".static-value").innerText = setValue;
                }
            }
            field.input.value = setValue;
            if (field.onChangeHandler) {
                if (field.onChangeHandler.length > 0) {
                    for (var i = 0; i < field.onChangeHandler.length; i++) {
                        field.onChangeHandler[i](field.val(), field);
                    }
                }
            }
            return;
        }
        return field.input.value;
    };

    field.getRule = function (rules) {
        if (!field.val()) {
            return null;
        }
        var age = CommonUtils.dateToAge(field.val());
        for (var index = 0; index < rules.length; index += 1) {
            if (age >= rules[index].min && age < rules[index].max) {
                return rules[index];
            }
        }
        return null;
    };

    field.refreshRule = function (rule) {
        var limitation = {};
        if (rule.min) {
            limitation.startDate = CommonUtils.ageToDate(rule.min);
        }
        if (rule.max) {
            limitation.endDate = CommonUtils.ageToDate(rule.max);
        }
        if (rule.startDate) {
            limitation.startDate = rule.startDate;
        }
        if (rule.endDate) {
            limitation.startDate = rule.endDate;
        }
        field.element.querySelector("input[name=" + field.name + "]").setAttribute("data-date-start-date", limitation.startDate);
        field.element.querySelector("input[name=" + field.name + "]").setAttribute("data-date-end-date", limitation.endDate);
    };

    field.init();

    if (field.input) {
        $(field.input).on("changeDate", function () {
            console.log("changeDate");
            if (this.onChangeHandler) {
                if (this.onChangeHandler.length > 0) {
                    for (var i = 0; i < this.onChangeHandler.length; i++) {
                        this.onChangeHandler[i](field.val(), field);
                    }
                }
            }
        }.bind(field));
    }

    return field;
};
