"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.localdatetime = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector("#tpl_attribute_date_time").innerHTML);
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
