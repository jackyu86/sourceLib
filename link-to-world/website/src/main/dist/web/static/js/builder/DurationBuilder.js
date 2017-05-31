var DurationBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_date').innerHTML);
    var html = render(attribute);
    var node = {
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function (value) {
            var messageElement = this.element.querySelector('.attribute-message');
            if (attribute.multiple) {
                var inputList = this.element.querySelectorAll("input");
                for (var j = 0; j < inputList.length; j++) {
                    var value = inputList[j].value;
                    for (var i = 0; i < this.validators.length; i++) {
                        var validator = this.validators[i];
                        if (!validator.valid(value)) {
                            messageElement.innerText = validator.message;
                            messageElement.addClass('show');
                            return false;
                        }
                    }
                    messageElement.removeClass('show');
                }
            } else {
                for (var i = 0; i < this.validators.length; i++) {
                    var validator = this.validators[i];
                    if (!validator.valid(value)) {
                        messageElement.innerText = validator.message;
                        messageElement.addClass('show');
                        return false;
                    }
                }
            }
            messageElement.removeClass('show');
            return true;
        },
        val: function (value) {
            if (value) {
                this.element.querySelector("input").value = value;
                return;
            }
            if (attribute.multiple) {
                var inputList = this.element.querySelectorAll("input");
                var values = [];
                inputList.forEach(function (input) {
                    values.push(input.value);
                });
                return values;
            } else {
                return this.element.querySelector("input").value;
            }
        }
    };
    if (attribute.type.options && attribute.type.options.length > 0) {
        attribute.type.options.forEach(function (option) {
            if (attribute.options[option.name]) {
                node.validators.push(Form.validatorBuilders[options.name](attribute.options));
            }
        })
    }
    return node;
};

var ReadOnlyDurationBuilder = function (attribute) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_date_preview').innerHTML);
    var html = render(attribute);
    var node = {
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function (value) {
            return true;
        },
        val: function (value) {
            if (value) {
                if (attribute.multiple) {
                    var inputList = this.element.querySelectorAll("input");
                    inputList.forEach(function (input, index) {
                        input.value = value[index];
                    });
                } else {
                    node.element.querySelector("input").value = value;
                }
                return;
            }
        }
    };
    return node;
};