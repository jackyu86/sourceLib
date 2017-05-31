"use strict";

/* global DynamicFieldBuilders DynamicField Handlebars createElement*/
function ProductAgeField(fieldAttribute, value) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector('#tpl_product_attribute_age').innerHTML);
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
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (value) {
        if (value) {
            field.input.value = value;
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

    field.init();

    return field;
}

function ProductNumberField(fieldAttribute, value) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector('#tpl_product_attribute_number').innerHTML);
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
        };
    }

    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (value) {
        if (value) {
            field.input.value = value;
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

    field.init();

    return field;
}

function ProductSerialField(fieldAttribute, value) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector('#tpl_product_serial').innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.input = field.element.querySelectorAll("[name='" + field.name + "']");

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
            if ($(field.input[i]).parent().hasClass("active")) {
                return field.input[i].value;
            }
        }
        return "";
    };

    $(field.element).find(".serial-product-id").each(function () {
        if ($(this).val() === $("#pdp-product-name").val()) {
            $(this).attr("checked");
            $(this).parent().addClass("active");
        } else {
            $(this).click(function () {
                window.location.href = "/product/" + $(this).val();
            });
        }
    });

    field.init();

    return field;

}

function ProductPlanPeriod(fieldAttribute, value) {

    if (fieldAttribute.options.type === "FIXED") {
        return fixedPlanPeriod(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_SELECTION") {
        return userSelectionPlanPeriod(fieldAttribute, value);
    }
    return new DynamicField(fieldAttribute, value);
}

function fixedPlanPeriod(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_plan_period_fixed").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    field.val = function () {
        return JSON.parse(field.input.value);
    };

    field.init();

    return field;
}

function userSelectionPlanPeriod(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_plan_period_selection").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    field.val = function (setValue) {
        if (setValue) {
            field.input.value = JSON.stringify(setValue);
            return;
        }
        return JSON.parse(field.input.value);
    };

    field.init();

    return field;
}

function ProductUnitField(fieldAttribute, value) {
    return DynamicFieldBuilders.unit(fieldAttribute, value, "#tpl_pdp_unit");
}

function ProductLiability(fieldAttribute, value) {

    if (fieldAttribute.options.type === "FIXED") {
        return productFixedLiability(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_SELECTION") {
        return productUserSelectionLiability(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_INPUT") {
        return productUserInputLiability(fieldAttribute, value);
    }
    return new DynamicField(fieldAttribute, value);
}

function productUserInputLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_liability_user_input").innerHTML);
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

    field.init();

    return field;
}

function productFixedLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_liability_fixed").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return true;
    };
    field.val = function (setValue) {
        if (setValue) {
            field.element.querySelector(".static-value").innerText = setValue;
        }
        return parseFloat(field.element.querySelector(".static-value").innerText);
    };
    field.init();

    return field;
}

function productUserSelectionLiability(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_liability_user_selection").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    if (field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                field.element.querySelector("select").value = setValue;
            }
            return parseFloat(field.element.querySelector("select").value);
        };
    } else {
        field.val = function (setValue) {
            if (setValue) {
                field.element.querySelector(".static-value").innerText = setValue;
            }
            return parseFloat(field.element.querySelector(".static-value").innerText);
        };
    }
    field.input = field.element.querySelector("select");

    field.handlers.optionRender = Handlebars.compile(document.querySelector("#tpl_product_liability_user_selection_option").innerHTML);
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

function ProductPlanstarttime(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_product_plan_start_time").innerHTML);
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
