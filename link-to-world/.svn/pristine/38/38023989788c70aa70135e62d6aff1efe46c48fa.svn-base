"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.city = function (fieldAttribute, value) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_city").innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.tree = {};
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "notNull";
                }
            }
            return null;
        };
    }

    field.provinceSelector = field.element.querySelector(".occupation-trunk");
    field.citySelector = field.element.querySelector(".occupation-branch");
    // field.leafSelector = field.element.querySelector(".occupation-leaf");
    field.provinceSelector.onchange = function () {
        var chunk = field.provinceSelector.value;
        for (var i = field.citySelector.children.length; i > 1; i--) {
            field.citySelector.remove(i - 1);
        }
        if (!chunk) {
            return false;
        }
        if (field.tree[chunk]) {
            for (var key in field.tree[chunk]) {
                var data = field.tree[chunk][key];
                field.citySelector.appendChild(createElement("<option value='" + data.id + "'>" + data.name + "</option>"));
            }
        } else {
            $.getJSON("/ajax/province/" + chunk).done(function (result) {
                field.tree[chunk] = {};
                for (var index = 0; index < result.length; index += 1) {
                    var city = result[index];
                    field.tree[chunk][city.id] = city;
                    field.citySelector.appendChild(createElement("<option value='" + city.id + "'>" + city.name + "</option>"));
                }
            }).fail(function (result) {

            });
        }
    };
    // field.citySelector.onchange = function () {
    //     var chunk = field.provinceSelector.value;
    //     var branch = field.citySelector.value;
    //     for (var i = field.leafSelector.children.length; i > 1; i--) {
    //         field.leafSelector.remove(i - 1);
    //     }
    //     if (!branch) {
    //         return;
    //     }
    //     if (field.tree[chunk][branch]) {
    //         for (var key in field.tree[chunk][branch]) {
    //             var data = field.tree[chunk][branch][key];
    //             field.leafSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
    //         }
    //     } else {
    //         $.getJSON("/ajax/job/" + branch + "/children").done(function (result) {
    //             field.tree[chunk][branch] = {};
    //             result.forEach(function (data) {
    //                 field.tree[chunk][branch][data.id] = data;
    //                 field.leafSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
    //             });
    //         }).fail(function (result) {
    //
    //         });
    //     }
    // };
    $.getJSON("/ajax/province").done(function (result) {
        for (var index = 0; index < result.length; index += 1) {
            var province = result[index];
            field.provinceSelector.appendChild(createElement("<option value='" + province.id + "'>" + province.name + "</option>"));
        }
    }).fail(function (result) {

    });

    field.val = function () {
        return {
            province: field.provinceSelector.value,
            city: field.citySelector.value
        };
    };

    field.init();

    return field;
};
