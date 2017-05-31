"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.address = function (fieldAttribute, value) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector("#tpl_attribute_address").innerHTML);
    var html = render(fieldAttribute);
    field.address = {};
    field.element = createElement(html);
    field.cities = {};
    field.wards = {};
    field.ready = false;
    field.cityReady = true;
    field.wardReady = true;
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

    field.refreshCity = function () {
        var cityList = field.cities[field.address.stateId];
        if (cityList) {
            field.renderCity(cityList);
        } else {
            field.cityReady = false;
            $.getJSON("/ajax/province/" + field.address.stateId).done(function (result) {
                cityList = {};
                for (var index = 0; index < result.length; index += 1) {
                    cityList[result[index].id] = result[index];
                }
                field.renderCity(cityList);
                field.cityReady = true;
            }).fail(function (result) {
                field.cityReady = true;
            });
        }
        return field;
    };

    field.renderCity = function (cityList) {
        for (var key in cityList) {
            var city = cityList[key];
            field.citySelector.appendChild(createElement("<option value='" + city.id + "'>" + city.name + "</option>"));
            if (field.address.cityId && field.address.cityId === city.id) {
                field.citySelector.value = field.address.cityId;
                field.refreshWard();
            }
        }
    };

    field.refreshWard = function () {
        var wardList = field.wards[field.address.cityId];

        if (wardList) {
            field.renderWard(wardList);
        } else {
            field.wardReady = false;
            $.getJSON("/ajax/province/city/" + field.address.cityId).done(function (result) {
                wardList = {};
                for (var index = 0; index < result.length; index += 1) {
                    var ward = result[index];
                    wardList[ward.id] = ward;
                }
                field.renderWard(wardList);
                field.wardReady = true;

            }).fail(function (result) {

            });
        }
        return field;
    };

    field.renderWard = function (wardList) {
        for (var key in wardList) {
            var ward = wardList[key];
            field.wardSelector.appendChild(createElement("<option value='" + ward.id + "'>" + ward.name + "</option>"));
            if (field.address.ward && field.address.ward === ward.name) {
                field.wardSelector.value = ward.id;
            }
        }

    };

    if (field.editable()) {
        field.stateSelector = field.element.querySelector(".occupation-trunk");
        field.citySelector = field.element.querySelector(".occupation-branch");
        field.wardSelector = field.element.querySelector(".occupation-leaf");
        field.addressInput = field.element.querySelectorAll("input[name=" + field.name + "]");
        field.addressInput[0].onchange = function () {
            field.address.address1 = field.addressInput[0].value || null;
        };
        field.addressInput[1].onchange = function () {
            field.address.address2 = field.addressInput[1].value || null;
        };
        field.stateSelector.onchange = function () {
            var stateId = field.stateSelector.value;
            field.address.stateId = stateId;
            field.address.state = field.stateSelector.querySelector("option[value='" + stateId + "']").innerHTML;
            for (var i = field.citySelector.children.length; i > 1; i--) {
                field.citySelector.remove(i - 1);
            }
            if (stateId) {
                field.refreshCity(stateId);
            }
        };
        field.citySelector.onchange = function () {
            var cityId = field.citySelector.value;
            field.address.cityId = cityId;
            field.address.city = field.citySelector.querySelector("option[value='" + cityId + "']").innerHTML;
            for (var i = field.wardSelector.children.length; i > 1; i--) {
                field.wardSelector.remove(i - 1);
            }
            if (cityId) {
                field.refreshWard();
            }
        };
        field.wardSelector.onchange = function () {
            field.address.ward = field.wardSelector.querySelector("option[value='" + field.wardSelector.value + "']").innerHTML;
        };
        $.getJSON("/ajax/province").done(function (result) {
            for (var index = 0; index < result.length; index += 1) {
                var data = result[index];
                field.stateSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.name + "</option>"));
            }
            field.ready = true;
        }).fail(function (result) {

        });

        field.fillAddress = function (address) {
            if (!field.ready) {
                setTimeout(function () {
                    field.fillAddress(address);
                }, 100);
                return;
            }
            field.stateSelector.value = address.stateId;
            var stateChanged = field.address.stateId !== address.stateId;
            var cityChanged = field.address.cityId !== address.cityId;
            field.address = address;
            if (stateChanged) {
                field.refreshCity();
            }
            if (cityChanged) {
                field.refreshWard();
            }
            field.addressInput[0].value = address.address1 || null;
            field.addressInput[1].value = address.address2 || null;
        };

        field.val = function (setValue) {
            if (setValue) {
                field.fillAddress(setValue);
                return this;
            }
            return field.address;
        };
    } else {
        field.val = function (setValue) {
            if (setValue) {
                var address = "";
                address += setValue.state || "";
                address += " " + (setValue.city || "");
                address += " " + (setValue.ward || "");
                address += " " + (setValue.address1 || "");
                address += " " + (setValue.address2 || "");
                field.element.querySelector(".static-value").innerHTML = address;
                return this;
            }
            return field.value;
        };
    }

    field.input = field.element.querySelectorAll("[name='" + field.name + "']");

    field.init();

    return field;
};
