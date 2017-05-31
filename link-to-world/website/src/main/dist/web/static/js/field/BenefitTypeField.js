"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.benefittype = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.enum(fieldAttribute, value);
    if (!field.editable()) {
        for (var ci = 0; ci < fieldAttribute.options.constants.length; ci += 1) {
            if (fieldAttribute.options.constants[ci].value === value || fieldAttribute.options.constants[ci].value === fieldAttribute.defaultValue ) {
                field.element.querySelector(".static-value").innerText = fieldAttribute.options.constants[ci].displayName;
            }
        }
    }
    return field;
};
