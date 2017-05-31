"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.string = function (fieldAttribute, value) {
    var templateId = "#tpl_attribute_string";
    if (DynamicFieldTemplate[fieldAttribute.name]) {
        templateId = DynamicFieldTemplate[fieldAttribute.name];
    }
    var render = Handlebars.compile(document.querySelector(templateId).innerHTML);
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
            if (options.Min) {
                if (inputValue.length < options.Min) {
                    return "Min";
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

    if (field.multiple) {

        field.val = function (data) {
            var inputs = this.element.querySelectorAll("input");

            if (data) {
                if (data.length) {
                    for (var index = 0; index < data.length; index += 1) {
                        if (inputs[index]) {
                            inputs[index].value = data[index];
                        } else {
                            addInput(data[index]);
                        }
                    }
                } else {
                    inputs[0].value = data;
                }
                if (!this.attribute.editable) {
                    this.element.querySelectorAll(".static-value")[0].innerText = data;
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
                addInput();
            };
        }
        plusButtons[plusButtons.length - 1].addClass("show");

        function addInput(value) {
            var template = Handlebars.compile(document.getElementById("tpl_attribute_string_additional").innerHTML);
            var element = createElement(template(field.attribute));
            element.querySelector(".fa-plus").onclick = function () {
                addInput();
            };
            element.querySelector(".fa-minus").onclick = function () {
                removeInput(element);
            };
            if (value) {
                element.querySelector("input").value = value;
            }
            field.element.querySelector(".string_input_group_wrap").appendChild(element);
            var additionalButtons = field.element.querySelectorAll(".fa-plus");
            for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
                additionalButtons[additionalIndex].removeClass("show");
            }
            additionalButtons[additionalButtons.length - 1].addClass("show");
        }

        function removeInput(element) {
            element.remove();
            var additionalButtons = field.element.querySelectorAll(".fa-plus");
            for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
                additionalButtons[additionalIndex].removeClass("show");
            }
            additionalButtons[additionalButtons.length - 1].addClass("show");
        }
    }

    field.init();

    return field;
};
