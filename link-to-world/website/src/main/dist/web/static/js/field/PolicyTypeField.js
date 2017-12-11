"use strict";

/* global DynamicFieldBuilders */
DynamicFieldBuilders.policytype = function (fieldAttribute, value) {
    return DynamicFieldBuilders.enum(fieldAttribute, value);
};
