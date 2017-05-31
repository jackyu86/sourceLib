DynamicFieldBuilders["age"] = function (fieldAttribute, value) {

    var render = Handlebars.compile(document.querySelector('#tpl_attribute_age').innerHTML);
    var html = render(fieldAttribute);
    var field = new DynamicField(fieldAttribute, value);
    field.element = createElement(html);
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length === 0) {
                    return "notNull";
                }
            }
            if (options.Min) {
                if (parseInt(inputValue) < options.Min) {
                    return "Min";
                }
            }
            if (options.Max) {
                if (parseInt(inputValue) > options.Max) {
                    return "Max";
                }
            }
            return null;
        };
    }
    field.input = field.element.querySelector("input[name=" + field.name + "]");
    field.val = function (value) {
        if (value) {
            field.input.value = value;
            if (field.element.querySelector(".static-value")) {
                field.element.querySelector(".static-value").innerText = value + "周岁";
            }
            return;
        }
        return field.input.value;
    };

    field.refreshFieldValues = function () {

    };

    if (field.input) {

        field.input.addEventListener("change", function () {
            if (field.onChangeHandler) {
                field.onChangeHandler(field.val(), field);
            }
        });
    }

    field.init();

    return field;
};
