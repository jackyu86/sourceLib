var StringBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_string').innerHTML);
    var html = render(attribute);

    var node = {
        attribute: attribute,
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function () {
            var value = this.element.querySelector("input").value;
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
                this.element.querySelector("input").value = value;
                return;
            }

            return this.element.querySelector("input").value;
        }
    };

    if (attribute.type.options &&attribute.type.options.length>0) {
        attribute.type.options.forEach(function (option) {
            if (attribute.options[option.name]) {
                node.validators.push(Form.validatorBuilders[options.name](attribute.options));
            }
        })
    }    return node;
};
var MultipleStringBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_string').innerHTML);
    var html = render(attribute);

    var node = {
        attribute: attribute,
        name: attribute.name,
        element: createElement(html),
        validators: [],
        validate: function () {
            var inputList = this.element.querySelectorAll("input");
            for (var j = 0; j < inputList.length; j++) {
                var value = inputList[j].value;
                var messageElement = inputList[j].parentElement.querySelector('.attribute-message');
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
            return true;
        },
        val: function () {
            var inputList = this.element.querySelectorAll("input");
            var values = [];
            inputList.forEach(function (input) {
                values.push(input.value);
            });
            return values;
        }
    };
    var plusButtons = node.element.querySelectorAll(".fa-plus");
    plusButtons.forEach(function (button) {
        button.onclick = function () {
            addInput();
        };
    });
    plusButtons[plusButtons.length - 1].addClass("show");

    function addInput() {
        var template = Handlebars.compile(document.getElementById("tpl_attribute_string_additional").innerHTML);
        var element = createElement(template(node.attribute));
        element.querySelector(".fa-plus").onclick = function () {
            addInput();
        };
        element.querySelector(".fa-minus").onclick = function () {
            removeInput(element);
        };
        node.element.querySelector(".string_input_group_wrap").appendChild(element);
        var plusButtons = node.element.querySelectorAll(".fa-plus");
        plusButtons.forEach(function (button) {
            button.removeClass("show");
        });
        plusButtons[plusButtons.length - 1].addClass("show");
    }

    function removeInput(element) {
        element.remove();
        var plusButtons = node.element.querySelectorAll(".fa-plus");
        plusButtons.forEach(function (button) {
            button.removeClass("show");
        });
        plusButtons[plusButtons.length - 1].addClass("show");
    }

    if (attribute.type.options &&attribute.type.options.length>0) {
        attribute.type.options.forEach(function (option) {
            if (attribute.options[option.name]) {
                node.validators.push(Form.validatorBuilders[options.name](attribute.options));
            }
        })
    }
    return node;
};

var ReadOnlyStringBuilder = function (attribute) {
};