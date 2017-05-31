"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.delivertype = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                if (setValue === "1") {
                    field.element.querySelector(".static-value").innerText = "部门发送";
                } else if (setValue === "2") {
                    field.element.querySelector(".static-value").innerText = "邮寄";
                } else if (setValue === "3") {
                    field.element.querySelector(".static-value").innerText = '上门递送'
                } else if (setValue === "4") {
                    field.element.querySelector(".static-value").innerText = '银行柜台领取'
                } else if (setValue === "5") {
                    field.element.querySelector(".static-value").innerText = '电子保单'
                } else if (setValue === "6") {
                    field.element.querySelector(".static-value").innerText = '纸质保单'
                }
                return;
            }
            if (field.element.querySelector(".static-value").innerText === "部门发送") {
                return "1";
            } else if (field.element.querySelector(".static-value").innerText === "邮寄") {
                return "2";
            } else if (field.element.querySelector(".static-value").innerText === "上门递送") {
                return "3";
            } else if (field.element.querySelector(".static-value").innerText === "银行柜台领取") {
                return "4";
            } else if (field.element.querySelector(".static-value").innerText === "电子保单") {
                return "5";
            } else if (field.element.querySelector(".static-value").innerText === "纸质保单") {
                return "6";
            }
        };
        field.val(value);
    }
    return field;
};
