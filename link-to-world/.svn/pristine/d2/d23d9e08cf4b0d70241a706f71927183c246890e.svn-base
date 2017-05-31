"use strict";

/* global createElement DynamicField DynamicFieldBuilders Handlebars*/
DynamicFieldBuilders.unit = function (fieldAttribute, value, templatePath) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_unit").innerHTML);
    if (templatePath) {
        render = Handlebars.compile(document.querySelector(templatePath).innerHTML);
    }
    var html = render(fieldAttribute);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "notNull";
                }
            }
            if (options.min) {
                if (inputValue.length < options.min) {
                    return "min";
                }
            }
            if (options.Max) {
                if (inputValue.length > options.Max) {
                    return "Max";
                }
            }
            return null;
        };
    }

    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.input.addEventListener("change", function () {
        if (field.input.value < 1) {
            field.input.value = 1;
        }
    });
    field.val = function (setValue) {
        if (setValue) {
            if (!field.editable()) {
                field.element.querySelector(".static-value").innerText = setValue;
            }
            field.input.value = setValue;
            return;
        }
        return field.input.value;
    };

    field.init();

    return field;
};