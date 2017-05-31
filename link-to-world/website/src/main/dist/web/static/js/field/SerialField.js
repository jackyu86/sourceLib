"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.serial = function (fieldAttribute, value) {

    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_serial").innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.input = field.element.querySelectorAll("[name='" + field.name + "']");

    if (fieldAttribute.editable) {
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
                if (hasClass(field.input[i].parentNode, "active")) {
                    return field.input[i].value;
                }
            }
            return "";
        };

        $(field.element).find(".serial-product-id").each(function () {
            if ($(this).val() === $("#pdp-product-name").val()) {
                $(this).attr("checked");
                $(this).parent().addClass("active");
            }
        });
    } else {
        field.input = field.element.querySelector(".static-value");

        field.val = function (setValue) {
            if (setValue) {
                $.each(field.attribute.options.products, function (index, product) {
                    if (product.productName === setValue) {
                        field.element.querySelector(".static-value").innerText = product.displayName;
                    }
                });
                return;
            }
            return field.element.querySelector(".static-value").innerText;
        };

    }
    field.init();

    return field;

};
