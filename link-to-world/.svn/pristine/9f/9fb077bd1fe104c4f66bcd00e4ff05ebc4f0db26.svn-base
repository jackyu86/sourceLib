"use strict";

/* global createElement createDynamicField  DynamicFieldBuilders Handlebars*/
DynamicFieldBuilders.traveldest = function (fieldAttribute, value) {
    if (fieldAttribute.multiple) {
        return DynamicFieldBuilders.multipleTravelDestination(fieldAttribute, value);
    }
    return DynamicFieldBuilders.singleTravelDestination(fieldAttribute, value);
};

DynamicFieldBuilders.multipleTravelDestination = function (fieldAttribute, value) {
    var field = createDynamicField("#tpl_attribute_travel_destination_select", fieldAttribute, value);
    field.input = field.element.querySelector("select[name=" + field.name + "]");
    if (fieldAttribute.editable) {
        field.val = function (data) {
            var inputs = this.element.querySelectorAll("select");

            if (data) {
                if (data.length) {
                    for (var index = 0; index < data.length; index += 1) {
                        if (inputs[index]) {
                            inputs[index].value = data[index];
                        } else {
                            field.addSelector(data[index]);
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
                result.push({code: inputs[i].value, name: field.handlers.countryDic.get(inputs[i].value)});
            }
            return result;
        };
    } else {
        field.val = function (data) {
            var name = "";
            if (data) {
                if (data.length) {
                    for (var index = 0; index < data.length; index += 1) {
                        if (name) {
                            name += ", ";
                        }
                        name += field.handlers.countryDic.get(data[index]);
                    }
                    this.element.querySelector(".static-value").innerText = name;

                } else {
                    this.element.querySelector(".static-value").innerText = field.handlers.countryDic.get(data);
                }
                field.element.querySelector("input").value = JSON.stringify(data);
                return this;
            }
            return JSON.parse(field.element.querySelector("input").value);
        };

    }

    field.handlers.countryDic = {
        dict: {},
        init: function () {
            for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
                this.dict[fieldAttribute.options.constants[ci].value] = fieldAttribute.options.constants[ci].displayName;
            }
        },
        get: function (name) {
            return this.dict[name];
        }
    };
    field.handlers.countryDic.init();

    var plusButtons = field.element.querySelectorAll(".fa-plus");
    for (var index = 0; index < plusButtons.length; index += 1) {
        plusButtons[index].onclick = function () {
            field.addSelector();
        };
    }
    plusButtons[plusButtons.length - 1].addClass("show");

    field.addSelector = function (setValue) {
        var template = Handlebars.compile(document.getElementById("tpl_attribute_travel_destination_select_additional").innerHTML);
        var element = createElement(template(field.attribute));
        element.querySelector(".fa-plus").onclick = function () {
            field.addSelector();
        };
        element.querySelector(".fa-minus").onclick = function () {
            field.removeInput(element);
        };
        if (setValue) {
            element.querySelector("select").value = setValue;
        }
        if (field.onChangeHandler) {
            element.querySelector("select").addEventListener("change", function () {
                if (field.onChangeHandler && field.onChangeHandler.length > 0) {
                    for (var i = 0; i < field.onChangeHandler.length; i++) {
                        field.onChangeHandler[i](field.val(), field);
                    }
                }
            });
        }
        field.element.querySelector(".string_input_group_wrap").appendChild(element);
        var additionalButtons = field.element.querySelectorAll(".fa-plus");
        for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
            additionalButtons[additionalIndex].removeClass("show");
        }
        additionalButtons[additionalButtons.length - 1].addClass("show");
        field.triggerChangeHandler();
    };

    field.removeInput = function (element) {
        element.remove();
        var additionalButtons = field.element.querySelectorAll(".fa-plus");
        for (var additionalIndex = 0; additionalIndex < additionalButtons.length; additionalIndex += 1) {
            additionalButtons[additionalIndex].removeClass("show");
        }
        additionalButtons[additionalButtons.length - 1].addClass("show");
        field.triggerChangeHandler();
    };

    field.init();
    return field;
};
DynamicFieldBuilders.singleTravelDestination = function (fieldAttribute, value) {
    var field = createDynamicField("#tpl_attribute_travel_destination_select", fieldAttribute, value);
    field.input = field.element.querySelector("select[name=" + field.name + "]");
    if (fieldAttribute.editable) {
        field.val = function (data) {
            var input = this.element.querySelector("select");
            if (data) {
                input.value = data.code;
                return this;
            }
            return {code: input.value, name: field.handlers.countryDic.get(input.value)};
        };
    } else {
        field.val = function (data) {
            if (data) {
                this.element.querySelector(".static-value").innerText = data.name;
                field.element.querySelector("input").value = JSON.stringify(data);
                return this;
            }
            return JSON.parse(field.element.querySelector("input").value);
        };

    }

    field.handlers.countryDic = {
        dict: {},
        init: function () {
            for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
                this.dict[fieldAttribute.options.constants[ci].value] = fieldAttribute.options.constants[ci].displayName;
            }
        },
        get: function (name) {
            return this.dict[name];
        }
    };
    field.handlers.countryDic.init();

    field.init();
    return field;
};
