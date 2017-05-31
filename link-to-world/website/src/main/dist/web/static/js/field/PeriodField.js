"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.period = function (fieldAttribute, value) {
    return DynamicFieldBuilders.enum(fieldAttribute, value);
};
