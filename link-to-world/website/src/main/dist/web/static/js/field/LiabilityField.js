"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.liability = function (fieldAttribute, value) {

    if (fieldAttribute.options.type === "FIXED") {
        return fixedLiability(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_SELECTION") {
        return userSelectionLiability(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_INPUT") {
        return userInputLiability(fieldAttribute, value);
    }
    return new DynamicField(fieldAttribute, value);
};

function userInputLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_liability_user_input").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    if (!field.editable()) {
        return fixedLiability(fieldAttribute, value);
    }
    field.element = createElement(html);
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
        if (options.max) {
            if (inputValue.length > options.max) {
                return "max";
            }
        }
        return null;
    };

    field.refreshFieldValues = function (options) {
        field.validator = function (inputValue) {
            if (options.min && inputValue < options.min) {
                return "min";
            }
            if (options.max && inputValue > options.max) {
                return "max";
            }
            return null;
        };

    };

    field.init();

    return field;
}

function fixedLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_liability_fixed").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    field.val = function (setValue) {
        if (setValue) {
            if (field.element.querySelector(".static-value")) {
                field.element.querySelector(".static-value").innerText = setValue;
            }
            field.element.querySelector("input").value = setValue;
        }
        return field.element.querySelector("input").value;
    };

    field.init();

    return field;
}

function userSelectionLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_liability_user_selection").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    if (!field.editable()) {
        return fixedLiability(fieldAttribute, value);
    }
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    if (field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                if (field.element.querySelector(".static-value")) {
                    field.element.querySelector(".static-value").innerText = setValue;
                }
                field.element.querySelector("select").value = setValue;
            }
            return parseFloat(field.element.querySelector("select").value);
        };
    } else {
        field.val = function (setValue) {
            if (setValue) {
                if (field.element.querySelector(".static-value")) {
                    field.element.querySelector(".static-value").innerText = setValue;
                }
                field.element.querySelector(".static-value").innerText = setValue;
            }
            return parseFloat(field.element.querySelector(".static-value").innerText);
        };
    }
    field.input = field.element.querySelector("select");
    field.handlers.optionRender = Handlebars.compile(document.querySelector("#tpl_attribute_liability_user_selection_option").innerHTML);
    field.refreshFieldValues = function (options) {
        var oldValue = this.val();
        var selector = field.element.querySelector("select");
        while (selector.firstChild) {
            selector.removeChild(selector.firstChild);
        }
        for (var oi = 0; oi < options.length; oi += 1) {
            var node = createElement(field.handlers.optionRender(options[oi]));
            selector.appendChild(node);
            if (parseInt(node.value, 10) === oldValue) {
                selector.value = oldValue;
            }
        }
    };
    field.getRule = function (rule, ruleValue) {
        var index = 0;
        for (; index < rule.length; index += 1) {
            if (rule[index].value === ruleValue) {
                return rule[index];
            }
        }
        return {};
    };

    field.init();

    return field;
}