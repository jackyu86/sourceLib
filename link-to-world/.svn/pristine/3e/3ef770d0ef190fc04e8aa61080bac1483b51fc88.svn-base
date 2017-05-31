"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.gender = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                if (setValue === "F") {
                    field.element.querySelector(".static-value").innerText = "女";
                } else if (setValue === "M") {
                    field.element.querySelector(".static-value").innerText = "男";
                } else if (setValue === "N") {
                    field.element.querySelector(".static-value").innerText = '不确定'
                }
                return;
            }
            if (field.element.querySelector(".static-value").innerText === "女") {
                return "F";
            } else if (field.element.querySelector(".static-value").innerText === "男") {
                return "M";
            } else if (field.element.querySelector(".static-value").innerText === "不确定") {
                return "N";
            }
        };
        field.val(value);
    }
    return field;
};
