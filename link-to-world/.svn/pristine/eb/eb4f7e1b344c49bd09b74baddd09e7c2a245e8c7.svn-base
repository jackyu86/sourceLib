var OccupationBuilder = function (attribute, Form) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_occupation').innerHTML);
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
    node.trunkSelector = node.element.querySelector(".occupation-trunk");
    node.branchSelector = node.element.querySelector(".occupation-branch");
    node.leafSelector = node.element.querySelector(".occupation-leaf");
    node.trunkSelector.onchange = function () {
        var chunk = node.trunkSelector.value;
        for (var i = node.branchSelector.children.length; i > 1; i--) {
            node.branchSelector.remove(i - 1);
        }
        if (!chunk) {
            return false;
        }
        if (node.tree[chunk]) {
            for (var key in node.tree[chunk]) {
                var data = node.tree[chunk][key];
                node.branchSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
            }
        } else {
            $.get("/ajax/occupation/" + chunk + "/children")
                .done(function (result) {
                    node.tree[chunk] = {};
                    result.forEach(function (data) {
                        node.tree[chunk][data.id] = data;
                        node.branchSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
                    });
                })
                .fail(function (result) {

                });
        }
    };
    node.branchSelector.onchange = function () {
        var chunk = node.trunkSelector.value;
        var branch = node.branchSelector.value;
        for (var i = node.leafSelector.children.length; i > 1; i--) {
            node.leafSelector.remove(i - 1);
        }
        if (!branch) {
            return;
        }
        if (node.tree[chunk][branch]) {
            for (var key in node.tree[chunk][branch]) {
                var data = node.tree[chunk][branch][key];
                node.leafSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
            }
        } else {
            $.getJSON("/ajax/occupation/" + branch + "/children")
                .done(function (result) {
                    node.tree[chunk][branch] = {};
                    result.forEach(function (data) {
                        node.tree[chunk][branch][data.id] = data;
                        node.leafSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
                    });
                })
                .fail(function (result) {

                });
        }
    };
    $.get("/ajax/occupation/top")
        .done(function (result) {
            result.forEach(function (data) {
                node.tree[data.id] = result;
                node.trunkSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
            });
        })
        .fail(function (result) {

        });

    if (attribute.constraints) {
        attribute.constraints.forEach(function (constraint) {
            node.validators.push(Form.validatorBuilders[constraint.validator.toLowerCase()](constraint));
        });
    }
    return node;
};

var ReadOnlyOccupationBuilder = function (attribute) {
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_occupation_readonly').innerHTML);
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