/**
 * @author miller
 */
"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.legalbeneficiary = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                if (setValue === "Y" || setValue === "true") {
                    field.element.querySelector(".static-value").innerText = "是";
                } else if (setValue === "N") {
                    field.element.querySelector(".static-value").innerText = "否";
                } else if (setValue === "W") {
                    field.element.querySelector(".static-value").innerText = '与该项无关'
                }
                return;
            }
            if (field.element.querySelector(".static-value").innerText === "是") {
                return "Y";
            } else if (field.element.querySelector(".static-value").innerText === "否") {
                return "N";
            } else if (field.element.querySelector(".static-value").innerText === "与该项无关") {
                return "W";
            }
        };
        field.val(value);
    }
    return field;
};