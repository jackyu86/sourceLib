"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.enum = function (fieldAttribute, value) {
    if (this.globalCount) {
        this.globalCount += 1;
    } else {
        this.globalCount = 1;
    }
    if (!fieldAttribute.editable) {
        return DynamicFieldBuilders.string(fieldAttribute, value);
    }
    if (fieldAttribute.displayAs === "selection") {
        return DynamicFieldBuilders.enumSelection(fieldAttribute, value);
    } else if (fieldAttribute.displayAs === "checkbox") {
        return DynamicFieldBuilders.enumCheckbox(fieldAttribute, value, this.globalCount);
    }
    return DynamicFieldBuilders.enumRadio(fieldAttribute, value, this.globalCount);

};

DynamicFieldBuilders.enumSelection = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector("#tpl_attribute_enum_select").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.input = field.element.querySelector("select[name=" + field.name + "]");
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

    field.handlers.constants = {
        constants: {},
        init: function () {
            for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
                this.constants[fieldAttribute.options.constants[ci].value] = fieldAttribute.options.constants;
            }
        },
        get: function (name) {
            return this.constants[name];
        }
    };

    field.handlers.constants.init();

    field.init();

    return field;
};
DynamicFieldBuilders.enumRadio = function (fieldAttribute, value, globalCount) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_enum_radio").innerHTML);
    fieldAttribute.globalIndex = globalCount;
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
            return null;
        };

        field.input = field.element.querySelectorAll("input[name=" + field.name + "]");

        field.val = function (setValue) {
            if (setValue) {
                for (var i = 0; i < field.input.length; i++) {
                    var radio = field.input[i];
                    radio.removeAttribute("checked");
                    radio.checked = false;
                    if (String(radio.value) === String(setValue)) {
                        radio.setAttribute("checked", "checked");
                        radio.checked = true;
                    }
                }
                return;
            }
            for (var i = 0; i < field.input.length; i += 1) {
                if (field.input[i].checked || field.input[i].getAttribute("checked") === "checked") {
                    return field.input[i].value;
                }
            }
        };

        for (var i = 0; i < field.input.length; i += 1) {
            var input = field.input[i];
            if (field.onChangeHandler) {
                input.parentNode.addEventListener("change", function (event) {
                    for (var eventIndex = 0; eventIndex < field.onChangeHandler.length; eventIndex += 1) {
                        field.onChangeHandler[eventIndex](event.currentTarget.querySelector("input").value, field);
                    }
                });
            }
            if (input.value === value || input.value === fieldAttribute.defaultValue) {
                input.setAttribute("checked", "checked");
            }
        }
    } else {
        field.input = field.element.querySelector(".static-value");

        field.val = function (setValue) {
            if (setValue) {
                field.element.querySelector(".static-value").innerText = field.handlers.constants.get(setValue).displayName;
                return;
            }
            return field.element.querySelector(".static-value").innerText;
        };
        field.element.querySelector(".static-value").innerText = value;
    }

    field.handlers.constants = {
        constants: {},
        init: function () {
            for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
                this.constants[fieldAttribute.options.constants[ci].value] = fieldAttribute.options.constants;
            }
        },
        get: function (name) {
            return this.constants[name];
        }
    };

    field.handlers.constants.init();

    return field;
};
