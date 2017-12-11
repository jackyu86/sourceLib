"use strict";

/* global createElement Handlebars DynamicField  */
function CheckoutIdField(fieldAttribute, value) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_checkout_information_field_id").innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (inputValue) {
        if (inputValue) {
            field.element.querySelector(".id-number").innerText = inputValue.number;
            field.element.querySelector(".id-type").innerText = field.handlers.constants.get(inputValue.type).displayName;
            return;
        }
        return field.input.value;
    };
    if (field.input) {

        field.input.addEventListener("change", function () {
            if (field.onChangeHandler) {
                field.onChangeHandler(field.val(), field);
            }
        });

    }

    field.handlers.constants = {
        constants: {},
        init: function () {
            for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
                this.constants[fieldAttribute.options.constants[ci].value] = fieldAttribute.options.constants[ci];
            }
        },
        get: function (name) {
            return this.constants[name];
        }
    };

    field.handlers.constants.init();

    field.init();

    return field;
}

var CheckoutInformationBoolean = function (fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_checkout_field_information_statement_boolean").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);

    field.validator = function (inputValue) {
        var options = field.attribute.options;
        if (options.notNull) {
            if (!inputValue || inputValue.length <= 0) {
                return "notNull";
            }
        }
        if (options.trueOnly && String(inputValue) !== "true") {
            return "trueOnly";
        }
        return null;
    };

    if (field.editable()) {
        field.input = field.element.querySelectorAll("input[name=" + field.name + "]");

        field.val = function (setValue) {
            if (setValue) {
                for (var i = 0; i < field.input.length; i++) {
                    var radio = field.input[i];
                    radio.removeAttribute("checked");
                    radio.checked = false;
                    if (radio.value === setValue) {
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
            if (field.onChangeHandler) {
                field.input[i].addEventListener("click", function () {
                    field.onChangeHandler(field.val(), field);
                });
            }
            if (field.input[i].value === value || field.input[i].value === fieldAttribute.defaultValue) {
                field.input[i].setAttribute("checked", "checked");
            }
        }
    } else {
        field.input = field.element.querySelector(".static-value");

        field.val = function (setValue) {
            if (setValue) {
                if (setValue == true) {
                    field.element.querySelector(".static-value").innerText = "是";
                } else {
                    field.element.querySelector(".static-value").innerText = "否";
                }
                return;
            }
            if (field.element.querySelector(".static-value").innerText === "是") {
                return true;
            } else {
                return false;
            }
        };
        field.val(value);
    }

    return field;
};

var CheckoutInformationString = function (fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_checkout_field_information_statement_string").innerHTML);
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
    }

    field.init();

    return field;
};

var CheckoutInformationEffectiveInsurance = function (fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_checkout_field_information_statement_effective_insurance").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.fillList = field.element.querySelector(".fill");

    field.val = function (data) {
        if (!data) {
            var result = {};
            for (var resultIndex = 1; resultIndex <= 5; resultIndex += 1) {
                result["fill" + resultIndex] = field.fill(resultIndex);
            }
            return result;
        }
        for (var dataIndex = 1; dataIndex <= 5; dataIndex += 1) {
            field.fill(dataIndex, data["fill" + dataIndex]);
        }
    };
    field.fill = function (index, data) {
        var fill = field.element.querySelector(".fill" + index);
        if (data) {
            fill.querySelector(".effective-insurance-company").value = data.company;
            fill.querySelector(".effective-insurance-amount").value = data.amount;
        } else {
            var result = {};
            result.company = fill.querySelector(".effective-insurance-company").value;
            result.amount = fill.querySelector(".effective-insurance-amount").value;
            return result;
        }
    };
    field.init();

    return field;
};
