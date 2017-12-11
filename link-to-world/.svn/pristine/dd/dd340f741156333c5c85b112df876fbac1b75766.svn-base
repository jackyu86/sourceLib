"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.integer = function (fieldAttribute, value) {
    var field = DynamicFieldBuilders.number(fieldAttribute, value);
    field.tempVal = field.val;
    field.val = function () {
        var data = field.tempVal();
        if (field.attribute.multiple) {
            var result = [];
            for (var i = 0; i < data.length; i++) {
                result.push(parseInt(data[i], 10));
            }
            return result;
        }
        return parseInt(data, 10);
    };

    return field;
};
