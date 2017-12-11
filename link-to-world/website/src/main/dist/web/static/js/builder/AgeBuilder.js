var AgeBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_age').innerHTML);
    var html = render(attribute);
    var node = {
        attribute: attribute,
        name: attribute.name,
        tree: [],
        element: createElement(html),
        validators: [],
        validate: function (value) {
            var messageElement = this.element.querySelector('.attribute-message');
            for (var i = 0; i < this.validators.length; i++) {
                var validator = this.validators[i];
                if (!validator.valid(value)) {
                    messageElement.innerText = validator.message;
                    addClass(messageElement, 'show');
                    return false;
                }
            }
            removeClass(messageElement, 'show');
            return true;
        },
        val: function (value) {
            if (value) {
                node.element.querySelector("input[type=number]").value = value;
                return;
            }
            return node.element.querySelector("input[type=number]").value;
        }
    };

    node.validators.push(Form.validatorBuilders["agerange"](attribute.options));
    return node;
};

var ReadOnlyAgeBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_age_readonly').innerHTML);
    var html = render(attribute);
    var node = {
        defaultValue: attribute.defaultValue,
        name: attribute.name,
        tree: [],
        element: createElement(html),
        validators: [],
        validate: function (value) {
            return true;
        },
        val: function () {
            return this.defaultValue;
        }
    };

    if (attribute.constraints) {
        attribute.constraints.forEach(function (constraint) {
            if (constraint.validator == "Age") {

            }
            node.validators.push(Form.validatorBuilders[constraint.validator.toLowerCase()](constraint));
        });
    }
    return node;
};

var AgeRangeValidatorBuilder = function (options) {
    return {
        min: options.Min,
        max: options.Max,
        message: "年龄范围为" + options.Min + "至" + options.Max,
        valid: function (value) {
            return value >= this.min && value <= this.max;
        }
    };
};
