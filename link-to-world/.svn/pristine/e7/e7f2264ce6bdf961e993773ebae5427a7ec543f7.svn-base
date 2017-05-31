var EnumSelectBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_enum_select').innerHTML);
    var html = render(attribute);

    var node = {
        attribute: attribute,
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function () {
            var value = this.element.querySelector("select").value;
            var messageElement = this.element.querySelector('.attribute-message');
            for (var i = 0; i < this.validators.length; i++) {
                var validator = this.validators[i];
                if (!validator.valid(value)) {
                    messageElement.innerText = validator.message;
                    messageElement.addClass('show');
                    return false;
                }
            }
            messageElement.removeClass('show');
            return true;
        },
        val: function (value) {
            if (value) {
                this.element.querySelector("select").value = value;
                return;
            }
            return this.element.querySelector("select").value;
        }
    };

    attribute.constraints.forEach(function (constraint) {
        node.validators.push(Form.validatorBuilders[constraint.validator.toLowerCase()](constraint));
    });
    return node;
};
var EnumOptionBuilder = function (attribute, Form) {

};
var EnumRadioBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_radio').innerHTML);
    var html = render(attribute);
    var node = {
        attribute: attribute,
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function () {
            var value = this.element.querySelector("select").value;
            var messageElement = this.element.querySelector('.attribute-message');
            for (var i = 0; i < this.validators.length; i++) {
                var validator = this.validators[i];
                if (!validator.valid(value)) {
                    messageElement.innerText = validator.message;
                    messageElement.addClass('show');
                    return false;
                }
            }
            messageElement.removeClass('show');
            return true;
        },
        val: function () {
            var elements = this.element.querySelector("input[name=attribute-" + attribute.name + "]");
            for (var i = 0; i < elements.length; i++) {
                // if (elements[i].prop)
            }
        }
    };

    attribute.constraints.forEach(function (constraint) {
        node.validators.push(Form.validatorBuilders[constraint.validator.toLowerCase()](constraint));
    });
    return node;
};
