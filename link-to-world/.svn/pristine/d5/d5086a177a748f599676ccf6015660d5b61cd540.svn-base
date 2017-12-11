"use strict";

/* global DynamicFieldBuilders Handlebars DynamicField createElement */
DynamicFieldBuilders.job = function (fieldAttribute, value) {
    var field = new DynamicField(fieldAttribute, value);
    var render = Handlebars.compile(document.querySelector('#tpl_attribute_job').innerHTML);
    var html = render(fieldAttribute);
    field.element = createElement(html);
    field.branches = {};
    field.leaves = {};
    field.ready = false;
    field.branchReady = true;
    field.leafReady = true;
    if (field.editable()) {
        field.validator = function (inputValue) {
            var options = field.attribute.options;
            if (options.notNull) {
                if (!inputValue || inputValue.length <= 0) {
                    return "notNull";
                }
            }
            return null;
        };
    }

    field.renderBranch = function (chunk, callback) {
        if (field.branches[chunk]) {
            for (var key in field.branches[chunk]) {
                var data = field.branches[chunk][key];
                field.branchSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
            }
            if (callback) {
                return callback();
            }
        } else {
            field.branchReady = false;
            $.get("/ajax/job/" + chunk + "/children").done(function (result) {
                field.branches[chunk] = {};
                for (var index = 0; index < result.length; index += 1) {
                    var leaf = result[index];
                    field.branches[chunk][leaf.id] = leaf;
                    field.branchSelector.appendChild(createElement("<option value='" + leaf.id + "'>" + leaf.displayName + "</option>"));
                }
                field.branchReady = true;
                if (callback) {
                    return callback();
                }
            }).fail(function (result) {

            });
        }
    };

    field.renderLeaves = function (branch, callback) {
        if (field.leaves[branch]) {
            for (var key in field.leaves[branch]) {
                var data = field.leaves[branch][key];
                field.leafSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));
            }
            if (callback) {
                return callback();
            }

        } else {
            field.leafReady = false;
            $.getJSON("/ajax/job/" + branch + "/children").done(function (result) {
                field.leaves[branch] = {};
                for (var index = 0; index < result.length; index += 1) {
                    var chunk = result[index];
                    field.leaves[branch][chunk.id] = chunk;
                    field.leafSelector.appendChild(createElement("<option value='" + chunk.id + "'>" + chunk.displayName + "</option>"));
                }

                field.leafReady = true;
                if (callback) {
                    return callback();
                }

            }).fail(function (result) {

            });
        }
    };

    if (field.editable()) {
        field.trunkSelector = field.element.querySelector(".occupation-trunk");
        field.branchSelector = field.element.querySelector(".occupation-branch");
        field.leafSelector = field.element.querySelector(".occupation-leaf");
        field.trunkSelector.onchange = function () {
            var chunk = field.trunkSelector.value;
            for (var i = field.branchSelector.children.length; i > 1; i--) {
                field.branchSelector.remove(i - 1);
            }
            if (!chunk) {
                return false;
            }
            field.renderBranch(chunk);
        };
        field.branchSelector.onchange = function () {
            var branch = field.branchSelector.value;
            for (var i = field.leafSelector.children.length; i > 1; i--) {
                field.leafSelector.remove(i - 1);
            }
            if (!branch) {
                return;
            }
            field.renderLeaves(branch);
        };
        $.get("/ajax/job/" + field.attribute.options.jobTreeId).done(function (result) {
            for (var index = 0; index < result.length; index += 1) {
                var data = result[index];
                field.trunkSelector.appendChild(createElement("<option value='" + data.id + "'>" + data.displayName + "</option>"));

            }
            field.ready = true;
        }).fail(function (result) {

        });

        field.fillJob = function (jobArray) {
            if (!field.ready) {
                setTimeout(function () {
                    field.fillJob(jobArray);
                }, 100);
                return;
            }
            field.trunkSelector.value = jobArray[0].id;
            field.renderBranch(jobArray[0].id, function () {
                field.branchSelector.value = jobArray[1].id;
            });
            field.renderLeaves(jobArray[1].id, function () {
                field.leafSelector.value = jobArray[2].id;
            });
        };

        field.val = function (setValue) {
            if (setValue) {
                $.get("/ajax/job/" + setValue + "/ancestor", function (result) {
                    field.fillJob(result);
                });
                return this;
            }
            return field.element.querySelector(".occupation-leaf").value;
        };
    } else {
        field.val = function (setValue) {
            if (setValue) {
                $.get("/ajax/job/" + setValue + "/ancestor", function (result) {
                    if (result && result.length > 0) {
                        var job = "";
                        for (var index = 0; index < result.length; index += 1) {
                            if (job !== "") {
                                job += " ";
                            }
                            job += result[index].displayName;
                        }
                        field.element.querySelector(".static-value").innerText = job;
                    }
                });
                return this;
            }
            return field.value;
        };
    }

    field.init();

    return field;
};
