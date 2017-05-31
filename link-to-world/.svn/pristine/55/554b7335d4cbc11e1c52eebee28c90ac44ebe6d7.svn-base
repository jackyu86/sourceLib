"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.agree = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector("#tpl_attribute_agree").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function (inputValue) {
        if (!field.element.querySelector("input").checked) {
            if (!inputValue || inputValue.length <= 0) {
                return "必须选中";
            }
        }
        return null;
    };

    field.val = function () {
        return "";
    };
    field.init();

    return field;
};
