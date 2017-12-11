"use strict";

/* global Handlebars */
Handlebars.registerHelper("Range", function (context, options) {
    if (context.min === context.max) {
        return context.min;
    }
    return context.min + " - " + context.max;
});

Handlebars.registerHelper("AgeRange", function (context, options) {
    var min = translateAgeRange(String(context.min));
    if (!context.max) {
        return min.num + min.unit + "以上";
    }

    var max = translateAgeRange(String(context.max));
    if (min.unit === max.unit) {
        return min.num + "-" + max.num + max.unit;
    }
    return min.num + min.unit + "-" + max.num + max.unit;
});

Handlebars.registerHelper("Duration", function (context, options) {
    if (context) {
        return context.replace("y", "年").replace("Y", "年")
            .replace("m", "个月").replace("M", "个月")
            .replace("d", "天").replace("D", "天");
    }
    return "";
});

var HANDLEBARS_AGE_DICTIONARY = {h: "小时", d: "天", m: "个月", y: "周岁"};

function translateAgeRange(str) {
    var index = str.search(/\D/);
    if (index > -1) {
        var num = str.substr(0, index);
        var unit = HANDLEBARS_AGE_DICTIONARY[str.substr(index)];
        return {
            num: num,
            unit: unit
        };
    }
    return {
        num: str,
        unit: HANDLEBARS_AGE_DICTIONARY.y
    };
}

Handlebars.registerHelper("equal", function (v1, v2, options) {
    if (v1 === v2) {
        return options.fn(this);
    }
    return options.inverse(this);
});

Handlebars.registerHelper("in", function (v1, v2, options) {
    var index = 0;
    for (; index < v2.length; index++) {
        if (v1 === v2[index]) {
            return options.fn(this);
        }
    }
    return options.inverse(this);
});

Handlebars.registerHelper('unequal', function (v1, v2, options) {
    if (v1 != v2) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

Handlebars.registerHelper("JSON", function (object, options) {
    if (object) {
        return JSON.stringify(object);
    }
    return "{}";
});

Handlebars.registerHelper("DateFormat", function (dateValue, format) {
    var date = new Date(dateValue);
    return format.replace("yyyy", date.getFullYear()).replace("yy", date.getFullYear().toString().substring(2))
        .replace("MM", date.getMonth() + 1).replace("dd", date.getDate())
        .replace("hh", date.getHours()).replace("mm", date.getMinutes())
        .replace("ss", date.getSeconds()).replace("n", date.getMilliseconds());
});

Handlebars.registerHelper("PercentFormat", function (num) {
    return parseNumber(num) / 100 + "%";
});

Handlebars.registerHelper("Number", function (value) {
    return parseNumber(value);
});

function parseNumber(value) {
    var number = parseFloat(value);
    if (number >= 10000) {
        var high = parseInt(number / 10000, 10);
        var numberStr = number.toString();
        for (var i = numberStr.length - 1; i >= 0; i--) {
            if (numberStr.substring(i, i + 1) !== "0") {
                if (i <= numberStr.length - 5) {
                    return high + "万";
                }
                return high + "." + numberStr.substring(numberStr.length - 4, i + 1) + "万";
            }
        }
    }
    return value;
}

Handlebars.registerHelper("liabilityFont", function (name, options) {
    if (name.length > 20) {
        return "smallest";
    } else if (name.length > 18) {
        return "smaller";
    } else if (name.length > 16) {
        return "small";
    }
    return "";
});

Handlebars.registerHelper("liabilityName", function (name, options) {
    var halfLength = name.length / 2;
    var index = parseInt(halfLength, 10);
    if (name.length <= 22) {
        return name;
    }
    if (halfLength > index) {
        index += 1;
    }
    return name.substr(0, index) + "<br>" + name.substr(index);
});

Handlebars.registerHelper("exist", function (context, options) {
    var index = 0;
    if (!context) {
        options.inverse(this);
        return;
    }
    if (context.length > 0) {
        for (; index < context.length; index += 1) {
            if (context[index].show) {
                return options.fn(this);
            }
        }
    }
    return options.inverse(this);
});