
"use strict";

/* global Handlebars DynamicFieldBuilders DynamicField createElement  */
DynamicFieldBuilders.height = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector("#tpl_attribute_integer_height").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length === 0) {
                    return "notNull";
                }
            }
            if (options.Min) {
                if (parseInt(inputValue, 10) < options.Min) {
                    return "Min";
                }
            }
            if (options.Max) {
                if (parseInt(inputValue, 10) > options.Max) {
                    return "Max";
                }
            }
            return null;
        };
    }

    field.init();

    return field;
};
