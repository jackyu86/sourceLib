"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement*/
DynamicFieldBuilders.effectiveinsurance = function (fieldAttribute, value) {
    var templateId = "#tpl_attribute_liability_effective_insurance";
    if (DynamicFieldTemplate[fieldAttribute.name]) {
        templateId = DynamicFieldTemplate[fieldAttribute.name];
    }
    var render = Handlebars.compile(document.querySelector(templateId).innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);


    field.init();

    return field;
};
