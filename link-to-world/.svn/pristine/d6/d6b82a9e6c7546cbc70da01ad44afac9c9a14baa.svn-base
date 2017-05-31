"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.invoicedelivertype = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                if (setValue === "0") {
                    field.element.querySelector(".static-value").innerText = "挂号信";
                } else if (setValue === "1") {
                    field.element.querySelector(".static-value").innerText = "快递";
                }
                return;
            }
            if (field.element.querySelector(".static-value").innerText === "挂号信") {
                return "0";
            } else if (field.element.querySelector(".static-value").innerText === "快递") {
                return "1";
            }
        };
        field.val(value);
    }
    return field;
};
