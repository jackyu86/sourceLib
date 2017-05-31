/**
 * @author miller
 */
"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.marriagetype = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                switch (setValue) {
                    case"1":
                        field.element.querySelector(".static-value").innerText = "已婚";
                        break;
                    case"2":
                        field.element.querySelector(".static-value").innerText = "未婚";
                        break;
                    case"3":
                        field.element.querySelector(".static-value").innerText = "离婚";
                        break;
                    case"4":
                        field.element.querySelector(".static-value").innerText = "丧偶";
                        break;
                    case"5":
                        field.element.querySelector(".static-value").innerText = "其他";
                        break;
                    default:
                        break;
                }
                return;
            }
            var text = field.element.querySelector(".static-value").innerText;
            switch (text) {
                case "已婚":
                    return "1";
                case "未婚":
                    return "2";
                case "离婚":
                    return "3";
                case "丧偶":
                    return "4";
                case "其他":
                    return "5";
                default:
                    return;
            }
        };
        field.val(value);
    }
    return field;
};