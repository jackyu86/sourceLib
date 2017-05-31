"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.relation = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        field.val = function (setValue) {
            if (setValue) {
                var code = String(setValue);
                switch (code) {
                    case "0":
                        field.element.querySelector(".static-value").innerText = "无关或不确定";
                        break;
                    case"1":
                        field.element.querySelector(".static-value").innerText = "配偶";
                        break;
                    case"2":
                        field.element.querySelector(".static-value").innerText = "子女";
                        break;
                    case"3":
                        field.element.querySelector(".static-value").innerText = "父母";
                        break;
                    case"4":
                        field.element.querySelector(".static-value").innerText = "亲属";
                        break;
                    case"5":
                        field.element.querySelector(".static-value").innerText = "本人";
                        break;
                    case"6":
                        field.element.querySelector(".static-value").innerText = "其他";
                        break;
                    case"7":
                        field.element.querySelector(".static-value").innerText = "雇佣关系";
                        break;
                    case"8":
                        field.element.querySelector(".static-value").innerText = "祖父母、外祖父母";
                        break;
                    case"9":
                        field.element.querySelector(".static-value").innerText = "孙子女、外孙子女";
                        break;
                    default:
                        break;
                }
                return;
            }
            var text = field.element.querySelector(".static-value").innerText;
            switch (text) {
                case "无关或不确定":
                    return "0";
                case "配偶":
                    return "1";
                case "子女":
                    return "2";
                case "父母":
                    return "3";
                case "亲属":
                    return "4";
                case "本人":
                    return "5";
                case "其他":
                    return "6";
                case "雇佣关系":
                    return "7";
                case "祖父母、外祖父母":
                    return "8";
                case "孙子女、外孙子女":
                    return "9";
                default:
                    return;
            }
        };
        field.val(value);

    }

    return field;
};
