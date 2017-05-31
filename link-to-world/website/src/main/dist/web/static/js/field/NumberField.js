"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.number = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector("#tpl_attribute_integer").innerHTML);
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

    if (fieldAttribute.multiple) {
        field.val = function (data) {
            var inputs = this.element.querySelectorAll("input");

            if (data) {
                for (var i = 0; i < inputs.length; i += 1) {
                    inputs[i] = data;
                }

                if (!this.attribute.editable) {
                    this.element.querySelector(".static-value").innerText = value;
                }
                return;
            }
            var result = [];
            for (var i = 0; i < inputs.length; i += 1) {
                result.push(inputs[i].value);
            }
            return result;
        };

        var plusButtons = field.element.querySelectorAll(".fa-plus");
        for (var index = 0; index < plusButtons.length; index += 1) {
            plusButtons[index].onclick = function () {
                field.handlers.addInput();
            };
        }
        plusButtons[plusButtons.length - 1].addClass("show");

        field.handlers.addInput = function () {
            var template = Handlebars.compile(document.getElementById("tpl_attribute_integer_additional").innerHTML);
            var element = createElement(template(field.attribute));
            element.querySelector(".fa-plus").onclick = function () {
                field.handlers.addInput();
            };
            element.querySelector(".fa-minus").onclick = function () {
                field.handlers.removeInput(element);
            };
            field.element.querySelector(".string_input_group_wrap").appendChild(element);
            var additionalButtons = field.element.querySelectorAll(".fa-plus");
            for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
                additionalButtons[additionalIndex].removeClass("show");
            }
            additionalButtons[additionalButtons.length - 1].addClass("show");
        };

        field.handlers.removeInput = function (element) {
            element.remove();
            var additionalButtons = field.element.querySelectorAll(".fa-plus");
            for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
                additionalButtons[additionalIndex].removeClass("show");
            }
            additionalButtons[additionalButtons.length - 1].addClass("show");
        };
    }

    field.init();

    return field;
};
