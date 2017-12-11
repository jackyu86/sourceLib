"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.id = function (fieldAttribute, value) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_id").innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.typeSelector = field.element.querySelector("select[name=idType]");
    field.input.setAttribute("maxlength", getIdTypeLength(field.typeSelector.value));
    if (field.typeSelector) {
        field.typeSelector.addEventListener("change", function () {
            field.input.setAttribute("maxlength", getIdTypeLength(field.typeSelector.value));
        });
    }
    field.validator = function (inputValue) {
        var options = field.attribute.options;
        if (options.notNull) {
            if (!inputValue || !inputValue.number || inputValue.number.length === 0) {
                return "notNull";
            }
        }
        return null;
    };
    field.val = function (inputValue) {
        if (inputValue && inputValue.type) {
            field.element.querySelector("select").value = getIdTypeId(inputValue.type);
            field.input.value = inputValue.number;
        }
        return {
            "type": field.element.querySelector("select").value,
            "number": field.input.value
        };
    };
    if (field.input) {
        field.input.addEventListener("change", function () {
            if (field.onChangeHandler) {
                if (field.onChangeHandler.length>0) {
                    for (var i =0;i<field.onChangeHandler.length;i++) {
                        field.onChangeHandler[i](field.val(), field);
                    }
                } else {
                    field.onChangeHandler(field.val(), field);
                }
            }
        });

    }

    field.init();

    return field;
};
