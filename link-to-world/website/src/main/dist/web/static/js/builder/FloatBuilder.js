var FloatBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_float').innerHTML);
    var html = render(attribute);
    var node = {
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

var ReadOnlyFloatBuilder = function (attribute) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_float_readonly').innerHTML);
    var html = render(attribute);
    var node = {
        name: attribute.name,
        tree: [],
        element: createElement(html),
        validators: [],
        validate: function (value) {
            return true;
        }
    };

    return node;
};