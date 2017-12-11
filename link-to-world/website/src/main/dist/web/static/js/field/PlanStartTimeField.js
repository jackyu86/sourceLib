"use strict";

/* global createElement DynamicField DynamicFieldBuilders Handlebars*/
DynamicFieldBuilders.planstarttime = function (fieldAttribute, value) {
    if (fieldAttribute.options.type === "DEFAULT") {
        return DynamicFieldBuilders.planstarttimeDefault(fieldAttribute, value);
    }
    return DynamicFieldBuilders.planstarttimeUserInput(fieldAttribute, value);
};

DynamicFieldBuilders.planstarttimeUserInput = function (fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_plan_start_time_input").innerHTML);
    fieldAttribute.currentStartDate = new Date();
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "notNull";
                }
            }
            return null;
        };
    }
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (setValue) {
        if (setValue) {
            if (field.element.querySelector(".static-value")) {
                field.element.querySelector(".static-value").innerText = setValue;
            }
            field.input.value = setValue;
            return;
        }
        return field.input.value;
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

DynamicFieldBuilders.planstarttimeDefault = function (fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_plan_start_time_default").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "notNull";
                }
            }
            return null;
        };
    }
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (setValue) {
        if (setValue) {
            if (field.element.querySelector(".static-value")) {
                field.element.querySelector(".static-value").innerText = setValue;
            }
            field.input.value = setValue;
            return;
        }
        return field.input.value;
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
