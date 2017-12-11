"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.planperiod = function (fieldAttribute, value) {

    if (fieldAttribute.options.type === "FIXED") {
        return fixedPlanPeriod(fieldAttribute, value);
    } else if (fieldAttribute.options.type === "USER_SELECTION") {
        return userSelectionPlanPeriod(fieldAttribute, value);
    }
    return new DynamicField(fieldAttribute, value);
};

function fixedPlanPeriod(fieldAttribute, value) {
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_plan_period_fixed").innerHTML);
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
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_plan_period_selection").innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    field.validator = function () {
        return null;
    };
    if (field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                field.input.value = JSON.stringify(setValue);
                return;
            }
            return JSON.parse(field.input.value);
        };
    } else {
        field.val = function (setValue) {
            if (setValue) {
                field.element.querySelector(".static-value").innerText = setValue.displayName;
                field.element.querySelector(".static-value").setAttribute("data", JSON.stringify(setValue));
                return;
            }
            return JSON.parse(field.element.querySelector(".static-value").getAttribute("data"));
        };
    }

    field.onChange(function (data) {
        document.querySelector(".total-period").innerText = data.displayName;
    });

    field.init();

    return field;
}