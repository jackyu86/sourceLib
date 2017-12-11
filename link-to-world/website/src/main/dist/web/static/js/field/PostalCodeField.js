"use strict";

/* global DynamicFieldBuilders*/
DynamicFieldBuilders.postalcode = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.string(fieldAttribute, value);
    if (field.editable()) {
        field.element.querySelector("input").setAttribute("maxlength", 6);
    }

    return field;
};
