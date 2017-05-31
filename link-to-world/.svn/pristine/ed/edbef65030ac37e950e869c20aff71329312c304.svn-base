$(function () {
    var Form = {
        init: function () {
            Form.initForm();
        },
        initForm: function () {
            var myForm = new DynamicInformList(data.list, ".question-list");
            myForm.fieldBuilder("boolean", function (fieldAttribute) {
                var render = Handlebars.compile(document.querySelector('#tpl_statement_boolean_radio').innerHTML);
                if (fieldAttribute.displayAs) {
                    if (fieldAttribute.displayAs == "checkbox") {
                        render = Handlebars.compile(document.querySelector('#tpl_statement_boolean_checkbox').innerHTML);
                    } else if (fieldAttribute.displayAs == "selection") {
                        render = Handlebars.compile(document.querySelector('#tpl_statement_boolean_selection').innerHTML);
                    }
                }
                var html = render(fieldAttribute);
                var field = new DynamicField(fieldAttribute);
                field.element = createElement(html);

                field.val = function () {
                    var radioList = field.element.querySelectorAll("input");
                    for (var i = 0; i < radioList.length; i++) {
                        if (radioList[i].checked) {
                            return radioList[i].value;
                        }
                    }
                };
                return field;
            }).fieldBuilder("integer", function (fieldAttribute) {
                var render = Handlebars.compile(document.querySelector('#tpl_statement_number').innerHTML);
                var html = render(fieldAttribute);
                var field = new DynamicField(fieldAttribute);
                field.element = createElement(html);

                field.validator = function (inputValue) {
                    console.log(inputValue);
                    var options = field.attribute.options;
                    if (options.notNull) {
                        if (!inputValue || inputValue.length <= 0) {
                            return "notNull";
                        }
                    }
                    if (options.min) {
                        if (parseInt(inputValue, 10) < options.min) {
                            return "min";
                        }
                    }
                    if (options.max) {
                        if (parseInt(inputValue) > options.max) {
                            return "max";
                        }
                    }
                    return null;
                };

                return field;
            }).fieldBuilder("string", function (fieldAttribute) {
                var render = Handlebars.compile(document.querySelector('#tpl_statment_string').innerHTML);
                var html = render(fieldAttribute);
                var field = new DynamicField(fieldAttribute);
                field.element = createElement(html);
                return field;
            }).build();

            $(".btn-submit").click(function () {
                if (!myForm.validate()) {
                    return;
                }
                alert("正常");
                console.log(myForm.val());
            })
        }
    };

    Form.init();
});

var DynamicInformList = function (list, container) {

    this.name = list.name;
    this.list = list;
    this.fields = [];
    this.messages = [];
    this.fieldMap = {};

    this.onChangeHandler = null;

    this.fieldBuilders = {};

    this.fieldBuilder = function (name, builder) {
        this.fieldBuilders[name] = builder;
        return this;
    };

    this.validate = function () {
        for (var i = 0; i < this.messages.length; i++) {
            var message = this.messages[i];
            if (!message.validate()) {
                return false;
            }
        }
        return true;
    };

    this.val = function () {
        var data = {};
        for (var i = 0; i < this.messages.length; i++) {
            var message = this.messages[i];
            data[message.name] = message.val();
        }
        return data;
    };

    this.build = function () {
        var messageRender = Handlebars.compile(document.getElementById("tpl_statement_message").innerHTML);
        if (!container.appendChild) {
            container = document.querySelector(container);
        }
        container.innerHTML = "";
        for (var i = 0; i < this.list.length; i++) {
            var messageAttribute = this.list[i];
            var messageElement = createElement(messageRender(messageAttribute));
            var field = messageAttribute.field;
            var fieldBuilder = this.fieldBuilders[field.type.name.toLowerCase()];
            if (!fieldBuilder) {
                fieldBuilder = DynamicFieldBuilders[field.type.name.toLowerCase()];
            }
            if (!fieldBuilder) {
                console.log(field.type.name + " builder not found");
                continue;
            }
            var fieldNode = fieldBuilder(field);
            fieldNode.init();
            this.fieldMap[fieldNode.name] = fieldNode;

            console.log(fieldNode.element);
            messageElement.innerHTML = messageElement.innerHTML.replace("{}", "<span class='MESSAGE_CONTAINER'></span>");
            messageElement.querySelector(".MESSAGE_CONTAINER").appendChild(fieldNode.element);
            container.appendChild(messageElement);
            var message = new DynamicInformMessage(messageElement, fieldNode, "text-danger");
            this.messages.push(message);
        }
    }
};

var DynamicInformMessage = function (element, field, errorClass) {
    this.name = field.name;
    this.element = element;
    this.field = field;
    this.errorClass = errorClass;

    this.validate = function () {
        if (this.element.querySelector("." + errorClass)) {
            this.element.querySelector("." + errorClass).remove();
        }
        if (!field.validate()) {
            this.element.appendChild(createElement("<span class='" + errorClass + "'>" + field.errorMessage + "</span>"));
            return false;
        } else {
            return true;
        }
    };

    this.val = function () {
        return this.field.val();
    }
};

var data = {
    list: [
        {
            message: '<p>曾患A病{}</p>',
            field: {
                name: "isQuestionA",
                type: {
                    name: "Boolean"
                },
                options: {},
                displayAs: "Radio"
            }
        },
        {
            message: '<p>曾患B病{}</p>',
            field: {
                name: "isQuestionB",
                type: {
                    name: "Boolean"
                },
                options: {},
                displayAs: "Radio"
            }
        },
        {
            message: '<p>针对这个你想收益{}万元</p>',
            field: {
                name: "benefit",
                type: {
                    name: "Integer"
                },
                options: {required: true, min: 1000, max: 10000}
            }
        }
    ]
};