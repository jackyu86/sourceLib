"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.boolean = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
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
