var IntegerBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_integer').innerHTML);
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

    if (attribute.constraints) {
        attribute.constraints.forEach(function (constraint) {
            node.validators.push(Form.validatorBuilders[constraint.validator.toLowerCase()](constraint));
        });
    }
    return node;
};

var ReadOnlyIntegerBuilder = function (attribute) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_integer_readonly').innerHTML);
    var html = render(attribute);
    var node = {
        name: attribute.name,
        tree: [],
        element: createElement(html),
        validators: [],
        validate: function (value) {
            return true;
        },
        val: function () {
            return attribute.defaultValue;
        }
    };

    return node;
};