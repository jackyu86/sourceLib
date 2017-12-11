"use strict";

var DynamicFieldBuilders = {};
var DynamicFieldTemplate = {};
var DynamicGroupBuilders = {
    "common": function (groupAttribute, value) {
        var group = new DynamicGroup(groupAttribute, value);
        var render = Handlebars.compile(document.querySelector("#tpl_attribute_group").innerHTML);
        var html = render(groupAttribute);
        group.element = createElement(html);
        return group;
    }
};

var DynamicForm = function (form, container) {

    console.log(form);

    var _form = this;
    this.container = container;
    this.form = form;
    this.groups = [];
    this.groupMap = {};

    this.handlers = {};

    this.ruleHandlers = {};
    this.ruleData = {};
    this.ruleFieldValue = {};

    this.fieldBuilders = {};
    this.groupBuilders = {};

    this.onChangeHandler = null;
    this.onInitHandler = null;
    this.editableHandler = null;

    this.render = function (form) {
        var render = Handlebars.compile(document.querySelector("#tpl_attribute_form").innerHTML);
        var html = render(form);
        return createElement(html);
    };

    this.builders = {};

    this.group = function (name) {
        var groups = [];
        for (var index = 0; index < _form.groups.length; index += 1) {
            if (_form.groups[index].name === name) {
                groups.push(_form.groups[index]);
            }
        }
        if (groups.length > 1) {
            return groups;
        }
        return groups[0];
    };

    this.validate = function () {
        var flag = true;
        for (var i = 0; i < _form.groups.length; i++) {
            var group = _form.groups[i];
            if (!group.validate()) {
                flag = false;
            }
        }
        return flag;
    };

    this.val = function (values) {
        if (values) {
            for (var groupName in values) {
                if (this.group(groupName)) {
                    this.group(groupName).val(values[groupName]);
                }
            }
            return;
        }
        var data = {};
        for (var i = 0; i < this.groups.length; i++) {
            var group = this.groups[i];
            if (group.disabled) {
                continue;
            }
            if (group.group.multiple) {
                if (!data[group.name]) {
                    data[group.name] = [];
                }
                data[group.name][group.index] = group.val();
            } else {
                data[group.name] = group.val();
            }
        }
        return data;
    };

    this.fieldBuilder = function (name, builder) {
        this.fieldBuilders[name] = builder;
        return this;
    };

    this.fieldTemplate = function (name, templateId) {
        DynamicFieldTemplate[name] = templateId;
        return this;
    };

    this.groupBuilder = function (name, builder) {
        this.groupBuilders[name] = builder;
        return this;
    };

    this.formBuilder = function (builder) {
        this.render = builder;
        return this;
    };

    this.onChange = function (handler) {
        this.onChangeHandler = handler;
        if (this.groups) {
            for (var groupIndex = 0; groupIndex < this.groups.length; groupIndex += 1) {
                this.groups[groupIndex].onChange(handler);
            }
        }
        return this;
    };

    this.onInit = function (handler) {
        this.onInitHandler = handler;
        return this;
    };

    this.editable = function (handler) {
        this.editableHandler = handler;
        return this;
    };

    this.init = function () {

        this.element = this.render(this.form);
        for (var i = 0; i < this.form.groups.length; i++) {
            var group = this.form.groups[i];
            var groupBuilder = this.groupBuilders[group.name];
            if (!groupBuilder) {
                groupBuilder = this.groupBuilders["common"];
            }
            if (!groupBuilder) {
                console.log(group.name + " group builder not found");
                groupBuilder = DynamicGroupBuilders["common"];
            }
            var groupNode = groupBuilder(group, this.form.value[group.name]);
            if (group.hidden) {
                groupNode.element.style.display = "none";
            }
            for (var key in this.fieldBuilders) {
                if (!groupNode.fieldBuilders[key]) {
                    groupNode.fieldBuilders[key] = this.fieldBuilders[key];
                }
            }
            groupNode.onChange(this.onChangeHandler).editable(this.editableHandler).build();
            this.groups.push(groupNode);
            this.groupMap[this.form.groups[i].name] = groupNode;
            if (this.element.querySelector(".Dynamic_Form_Container")) {
                this.element.querySelector(".Dynamic_Form_Container").appendChild(groupNode.element);
            } else {
                this.element.appendChild(groupNode.element);
            }

        }

        if (this.container.appendChild) {
            this.container.innerHTML = "";
            this.container.appendChild(this.element);
        } else {
            document.querySelector(container).innerHTML = "";
            document.querySelector(container).appendChild(this.element);
        }

        if (this.onInitHandler) {
            this.onInitHandler(this);
        }

    };

    this.rule = function (name, data) {
        this.ruleHandlers[name](data);
        return this;
    };

    this.ruleCascade = function (data) {
        var level = 0;
        this.ruleData = data;

        while (level < this.ruleData.fields.length - 1) {
            var groupFieldNames = this.ruleData.fields[level].split(".");
            var field = this.group(groupFieldNames[0]).field(groupFieldNames[1]);
            this.ruleFieldValue[this.ruleData.fields[level]] = field.val();
            field.onChange([function () {
                this.refreshFieldsValues();
            }.bind(this)]);
            level += 1;
        }
        this.refreshFieldsValues();
    };

    this.refreshFieldsValues = function (depth, rules) {
        var level = depth;
        var tempRules = rules;
        if (!level) {
            level = 0;
        }
        if (!tempRules) {
            tempRules = this.ruleData.rules;
        }
        var groupFieldNames = this.ruleData.fields[level].split(".");
        var field = this.group(groupFieldNames[0]).field(groupFieldNames[1]);
        var nextRule = field.getRule(tempRules, field.val());
        field.refreshFieldValues(tempRules);
        this.ruleFieldValue[this.ruleData.fields[level]] = field.val();
        level += 1;
        if (level < this.ruleData.fields.length) {
            this.refreshFieldsValues(level, nextRule.children);
        }
    };

    this.withRule = function (name, rule) {
        this.ruleHandlers[name] = rule;
        return this;
    };

};

var DynamicGroup = function (group, values) {
    var _group = this;

    this.disabled = false;
    this.name = group.name;
    this.group = group;
    this.fields = [];
    this.fieldMap = {};
    this.index = 0;
    if (values) {
        this.values = values;
    } else {
        this.values = {};
    }

    this.onChangeHandler = [];

    this.fieldBuilders = {};

    this.field = function (name) {
        return this.fieldMap[name];
    };

    this.onChange = function (handler) {
        if (handler) {
            this.onChangeHandler.push(handler);
            if (this.fields) {
                for (var fieldIndex = 0; fieldIndex < this.fields.length; fieldIndex += 1) {
                    this.fields[fieldIndex].onChange(handler);
                }
            }
        }
        return this;
    };

    this.editable = function (handler) {
        this.editableHandler = handler;
        return this;
    };

    this.validate = function () {
        var flag = true;
        if (this.disabled) {
            return flag;
        }
        if (this.group.optional && this.element.querySelector("[name='group_optional']") && !this.element.querySelector("[name='group_optional']").checked) {
            return flag;
        }
        for (var i = 0; i < _group.fields.length; i++) {
            var field = _group.fields[i];
            if (!field.validate()) {
                flag = false;
            }
        }
        return flag;
    };

    this.val = function (setValue) {
        if (this.group.multiple && this.multipleVal) {
            return this.multipleVal(setValue);
        }
        if (setValue) {
            for (var key in setValue) {
                if (this.fieldMap[key]) {
                    this.fieldMap[key].val(setValue[key]);
                }
            }
            return this;
        }
        var data = {};
        for (var index = 0; index < this.fields.length; index += 1) {
            var field = this.fields[index];
            data[field.name] = field.val();
        }
        return data;
    };

    this.build = function () {
        for (var i = 0; i < this.group.fields.length; i++) {
            if (this.fieldLimit && i >= this.fieldLimit) {
                break;
            }
            var field = this.group.fields[i];
            if (field.hidden) {
                continue;
            }
            var typeName;
            if (field.type.name) {
                typeName = field.type.name.toLowerCase();
            } else {
                typeName = field.type.toLowerCase();
            }
            var fieldBuilder = this.fieldBuilders[typeName];
            if (!fieldBuilder) {
                fieldBuilder = DynamicFieldBuilders[typeName];
            }
            if (!fieldBuilder) {
                console.log(field.type + "builder not found");
                continue;
            }
            var tempEditable = null;
            if (this.editableHandler) {
                tempEditable = field.editable;
                field.editable = this.editableHandler(field, this.values[field.name]);
            }
            var fieldNode = fieldBuilder(field, this.values[field.name]);
            if (tempEditable !== null) {
                fieldNode.attribute.editable = tempEditable;
            }
            if (this.onChangeHandler) {
                fieldNode.onChange(this.onChangeHandler);
            }
            fieldNode.groupName = this.name;
            fieldNode.groupIndex = this.index;
            this.fields.push(fieldNode);
            this.fieldMap[fieldNode.name] = fieldNode;
            if (this.element.querySelector(".Dynamic_Group_Container")) {
                this.element.querySelector(".Dynamic_Group_Container").appendChild(fieldNode.element)
            } else {
                this.element.appendChild(fieldNode.element);
            }
        }

        if (this.group.optional && this.element.querySelector(".header-content .checkbox")) {
            this.element.querySelector(".header-content .checkbox").addEventListener("click", function () {
                this.element.toggleClass("require");
            }.bind(this));
        }

        return this;
    };

};

var DynamicField = function (field, value) {

    this.name = field.name;
    this.value = value;
    this.attribute = field;
    this.element = createElement("<div></div>");

    this.validator = null;
    this.errorMessage = "";

    this.onChangeHandler = [];

    this.handlers = [];

    this.error = function (message) {
        var messageElement = this.element.querySelector(".attribute-message");
        messageElement.innerText = message;
        messageElement.addClass("show");
        return false;
    };

    this.onChange = function (handler) {
        if (handler && handler.length > 0) {
            this.onChangeHandler = this.onChangeHandler.concat(handler);
        }
        return this;
    };

    this.triggerChangeHandler = function () {
        if (this.onChangeHandler) {
            if (this.onChangeHandler.length > 0) {
                for (var i = 0; i < this.onChangeHandler.length; i++) {
                    this.onChangeHandler[i](this.val(), this);
                }
            }
        }

    };

    this.validate = function (exceptList) {
        if (!this.attribute.editable || this.hidden) {
            return true;
        }
        if (this.validator) {
            var validatingValue = this.val();
            if (this.attribute.multiple) {
                for (var i = 0; i < validatingValue.length; i++) {
                    var validateResult = this.validator(validatingValue[i]);
                    if (validateResult && !except(validateResult, exceptList)) {
                        if (this.attribute.options[validateResult + "Message"]) {
                            this.error(this.attribute.options[validateResult + "Message"].replace("{}", this.attribute.options[validateResult]));
                        } else {
                            this.error(validateResult);
                        }
                        return false;
                    }
                }
                this.noerror();
                return true;
            }

            var validateResult = this.validator(validatingValue);
            if (validateResult && !except(validateResult, exceptList)) {
                if (this.attribute.options[validateResult + "Message"]) {
                    this.error(this.attribute.options[validateResult + "Message"].replace("{}", this.attribute.options[validateResult]));
                } else {
                    this.error(validateResult);
                }
                return false;
            }
            this.noerror();
            return true;
        }

        function except(message, exceptArray) {
            if (!exceptArray) {
                return false;
            }
            if (exceptArray.includes(message)) {
                return true;
            }
            return false;
        }

        this.noerror();
        return true;
    };

    this.error = function (message) {
        var messageElement = this.element.querySelector(".attribute-message");
        if (messageElement) {
            messageElement.innerText = message;
            messageElement.addClass("show");
        }
    };

    this.noerror = function () {
        var messageElement = this.element.querySelector(".attribute-message");
        if (messageElement) {
            messageElement.removeClass("show");
        }
    };

    this.editable = function () {
        return this.attribute.editable;
    };

    this.hide = function () {
        this.hidden = true;
        this.element.style = "display: none";
        return this;
    };

    this.show = function () {
        this.hidden = false;
        this.element.style = "";
        return this;
    };

    this.val = function (setValue) {
        if (setValue) {
            this.input.value = setValue;
            if (this.element.querySelector(".static-value")) {
                this.element.querySelector(".static-value").innerText = setValue;
            }
            return;
        }
        if (this.editable()) {
            if (this.attribute.multiple) {
                this.inputs = this.element.querySelectorAll("input");
                var data = [];
                for (var i = 0; i < this.inputs.length; i += 1) {
                    data.push(this.inputs[i].value);
                }
                return data;
            }
            try {
                return this.input.value;
            } catch (e) {
                console.log(this.name);
            }
        } else {
            if (this.attribute.multiple) {
                var inputs = field.element.querySelectorAll(".form-control-static");
                var result = [];
                for (var i = 0; i < inputs.length; i += 1) {
                    result.push(inputs[i].innerText);
                }
                return result;
            }

            return this.element.querySelector(".form-control-static").innerText;
        }

    };

    this.init = function () {
        if (!this.input) {
            this.input = this.element.querySelector("[name='" + field.name + "']");
        }

        if (this.value || this.attribute.defaultValue) {
            this.val(this.value || this.attribute.defaultValue);

        }
        if (this.input && this.editable()) {
            if (this.input instanceof NodeList && this.input.length > 1) {
                for (var inputIndex = 0; inputIndex < this.input.length; inputIndex += 1) {
                    this.input[inputIndex].addEventListener("change", function () {
                        if (this.onChangeHandler) {
                            if (this.onChangeHandler.length > 0) {
                                for (var eventIndex = 0; eventIndex < this.onChangeHandler.length; eventIndex += 1) {
                                    this.onChangeHandler[eventIndex](this.val(), this);
                                }
                            }
                        }
                    }.bind(this));
                    this.input[inputIndex].addEventListener("blur", function () {
                        this.validate();
                    }.bind(this));
                }
            } else {
                this.input.addEventListener("change", function () {
                    if (this.onChangeHandler) {
                        if (this.onChangeHandler.length > 0) {
                            for (var i = 0; i < this.onChangeHandler.length; i++) {
                                this.onChangeHandler[i](this.val(), this);
                            }
                        }
                    }
                }.bind(this));
                this.input.addEventListener("blur", function () {
                    this.validate();
                }.bind(this));

            }
        }
    };
};

Element.prototype.addClass = function (cls) {
    try {
        if (hasClass(this, cls)) {
            return;
        }
        var objClass = this.className;
        if (objClass === "") {
            this.className = cls;
        } else {
            this.className = objClass + " " + cls;
        }
    } catch (error) {
        console.log(error);
    }

};

Element.prototype.removeClass = function (cls) {
    if (!hasClass(this, cls)) {
        return;
    }
    var objClass = " " + this.className + " ";
    objClass = objClass.replace(/(\s+)/gi, " ");
    var removed = objClass.replace(" " + cls + " ", " ");
    removed = removed.replace(/(^\s+)|(\s+$)/g, "");
    this.className = removed;
};

Element.prototype.toggleClass = function (cls) {
    if (hasClass(this, cls)) {
        this.removeClass(cls);
    } else {
        this.addClass(cls);
    }
};

function hasClass(obj, cls) {
    var classArray = obj.className.split(/\s+/);
    for (var key in classArray) {
        if (classArray[key] === cls) {
            return true;
        }
    }
    return false;
}

function createElement(html) {
    var div = document.createElement("div");
    div.innerHTML = html;
    return div.firstElementChild;
}

function createDynamicGroup(templateId, groupAttribute, value) {
    var group = new DynamicGroup(groupAttribute, value);
    var render = Handlebars.compile(document.querySelector(templateId).innerHTML);
    var html = render(groupAttribute);
    group.element = createElement(html);
    return group;
}

function createDynamicField(templateId, fieldAttribute, value) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector(templateId).innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    return field;
}
