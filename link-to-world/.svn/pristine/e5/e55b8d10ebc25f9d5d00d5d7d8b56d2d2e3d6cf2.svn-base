"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.double = function (fieldAttribute, value) {

    var field = DynamicFieldBuilders.number(fieldAttribute, value);
    field.tempVal = field.val;
    field.val = function () {
        var data = field.tempVal();
        if (field.attribute.multiple) {
            var result = [];
            for (var i = 0; i < data.length; i++) {
                result.push(parseFloat(data[i]));
            }
            return result;
        }
        return parseFloat(data);
    };

    return field;
};
