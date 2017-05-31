"use strict";

/* global CommonUtils Papa Handlebars DynamicForm createElement CheckoutInformationBoolean CheckoutInformationEffectiveInsurance CheckoutInformationString createDynamicGroup parseLocalDate SideNav */
$(function () {
    var Form = {
        insuredSelf: false,
        insuredSelfIndex: 0,
        myForm: null,
        nav: null,
        formData: {},
        restoreGroups: [],
        restrictJobElement: $("#product-restricted-job"),
        jobRestrict: false,
        restrictedJobList: [],
        init: function () {
            this.productId = $("#product-id").val();
            this.jobRestrict = this.restrictJobElement.length > 0;
            if (this.jobRestrict) {
                this.restrictedJobList = JSON.parse(this.restrictJobElement.html())
                if (!this.restrictedJobList) {
                    this.restrictedJobList = [];
                }
            }
            this.initForm();
            if (this.myForm) {
                this.nav = new SideNav(this.myForm, true);
                this.nav.briefForm.onChange(function (input, node) {
                    if (this.heavySickRule && node.attribute.type.toLowerCase() === "liability") {
                        this.syncLiability(input, node);
                    }
                    this.refreshData(input, node);
                    if (node.validate()) {
                        this.getPrice(this.formData);
                    }
                }.bind(this));
                this.nav.detailForm.onChange(function (input, node) {
                    if (this.heavySickRule && node.attribute.type.toLowerCase() === "liability") {
                        this.syncLiability(input, node);
                    }
                    this.refreshData(input, node);
                    if (node.validate()) {
                        this.getPrice(this.formData);
                    }
                }.bind(this));
            }
        },
        refreshData: function (input, node) {
            this.synchronization.syncFormValue(this.nav.briefForm, node, input);
            this.synchronization.syncFormValue(this.nav.detailForm, node, input);
            this.synchronization.syncFormValue(this.myForm, node, input);
            this.formData = this.myForm.val();
            localStorage.setItem("checkout_form_data", JSON.stringify(this.formData));
            localStorage.setItem("checkout_form_id", this.productId);
        },
        synchronization: {
            syncFormValue: function (form, node, value) {
                var group = form.group(node.groupName);
                if (!group) {
                    return;
                }
                if (group.length) {
                    this.setMultipleValue(group, node, value);
                } else {
                    this.setValue(group, node, value);
                }
            },
            setMultipleValue: function (group, node, value) {
                var index = 0;
                for (; index < group.length; index += 1) {
                    if (group[index].index === node.index) {
                        this.setValue(group[index], node, value);
                    }
                }
            },
            setValue: function (group, node, value) {
                var field = group.field(node.name);
                if (field && field !== node) {
                    field.val(value);
                }
            }
        },

        mergeValue: function (formData) {
            var savedDataStr = localStorage.getItem("checkout_form_data");
            if (!savedDataStr) {
                return;
            }
            if (this.productId !== localStorage.getItem("checkout_form_id")) {
                return;
            }
            this.formData = JSON.parse(savedDataStr);
            for (var groupKey in this.formData) {
                if (this.formData.hasOwnProperty(groupKey)) {
                    formData.value[groupKey] = mergeGroupData(formData.value[groupKey], this.formData[groupKey]);
                }
            }

            function mergeGroupData(formGroupData, savedGroupData) {
                var groupIndex = 0;
                if (!formGroupData) {
                    return savedGroupData;
                }
                if (formGroupData instanceof Array) {
                    if (savedGroupData instanceof Array) {
                        for (; groupIndex < savedGroupData.length; groupIndex += 1) {
                            if (groupIndex < formGroupData.length) {
                                mergeFieldData(formGroupData[groupIndex], savedGroupData[groupIndex]);
                            } else {
                                formGroupData.push(savedGroupData[groupIndex]);
                            }
                        }
                    } else {
                        mergeFieldData(formGroupData[0], savedGroupData);
                    }
                } else if (savedGroupData instanceof Array) {
                    mergeFieldData(formGroupData, savedGroupData[0]);
                } else {
                    mergeFieldData(formGroupData, savedGroupData);
                }
                return formGroupData;
            }

            function mergeFieldData(formGroupData, savedGroupData) {
                for (var fieldKey in savedGroupData) {
                    if (savedGroupData.hasOwnProperty(fieldKey) && !formGroupData[fieldKey]) {
                        formGroupData[fieldKey] = savedGroupData[fieldKey];
                    }
                }
            }
        },
        syncOwnData: function () {
            var groups = this.myForm.group("insured");
            var data = this.myForm.group("policy-holder").val();
            if (this.insuredSelf) {
                data.relation = 5;
                if (groups.length) {
                    this.syncOwnGroup(groups, data);
                } else {
                    groups.val(data);
                }
            }
        },
        syncOwnGroup: function (groupList, data) {
            var index = 0;
            for (; index < groupList.length; index += 1) {
                if (groupList[index].index === this.insuredSelfIndex) {
                    groupList[index].val(data);
                    break;
                }
            }
        },
        activeSelfInsured: function () {
            var holderGroup = this.myForm.group("policy-holder");
            var group = this.myForm.group("insured");
            var selfInsured = null;
            var gi = 0;
            var fi = 0;
            if (group.length) {
                for (gi = 0; gi < group.length; gi += 1) {
                    if (group[gi].index > 0) {
                        group[gi].disabled = true;
                        $(group[gi].element).remove();
                    } else {
                        selfInsured = group[gi];
                    }
                }
            } else {
                selfInsured = group;
            }

            for (; fi < holderGroup.fields.length; fi += 1) {
                var field = holderGroup.fields[fi];
                if (selfInsured.field(field.name)) {
                    $(selfInsured.field(field.name).element).hide();
                }
            }
            $(selfInsured.field("relation").element).hide();
            $(selfInsured.element).find(".btn-upload-insured").hide();
            $(".insured-add-wrap").hide();
        },
        inactiveSelfInsured: function () {
            var group = this.myForm.group("insured");
            var selfInsured = null;
            var gi = 0;
            var fi = 0;
            if (group.length) {
                for (gi = 0; gi < group.length; gi += 1) {
                    if (group[gi].index === 0) {
                        selfInsured = group[gi];
                    }
                }
                for (gi = 0; gi < group.length; gi += 1) {
                    if (group[gi].index > 0) {
                        group[gi].disabled = true;
                        $(selfInsured.element).find(".add-group").parents(".field-list").before(group[gi].element)
                    }
                }
            } else {
                selfInsured = group;
            }

            for (; fi < selfInsured.fields.length; fi += 1) {
                var field = selfInsured.fields[fi];
                $(field.element).show();
            }
            $(selfInsured.field("relation").element).show();
            $(selfInsured.element).find(".btn-upload-insured").show();

            $(".insured-add-wrap").show();
        },

        bindRelation: function () {
            var groups = this.myForm.group("insured");
            var gi = 0;
            if (groups.length) {
                for (; gi < groups.length; gi += 1) {
                    var relationField = groups[gi].field("relation");
                    relationField.onChange([function (input, node) {
                        this.changeRelation(node);
                    }.bind(this)]);
                }
            } else {
                var field = groups.field("relation");

                field.onChange([function (input, node) {
                    this.changeRelation(node);
                }.bind(this)]);
            }

        },
        changeRelation: function (field) {
            if (field.val() === "5") {
                if (this.insuredSelf && this.insuredSelfIndex !== field.groupIndex) {
                    field.error("同一个人只能有一份");
                    return;
                }
                this.insuredSelf = true;
                this.insuredSelfIndex = field.groupIndex;
                this.syncOwnData();
            } else {
                this.insuredSelf = this.anyInsuredSelf();
            }
        },

        anyInsuredSelf: function () {
            var index = 0;
            var groups = this.myForm.group("insured");
            if (groups.length) {
                for (; index < groups.length; index += 1) {
                    if (String(groups[index].field("relation").val()) === "5") {
                        return true;
                    }
                }
            } else if (String(groups.field("relation").val()) === "5") {
                return true;
            }
            return false;
        },
        syncLiability: function (input, node) {
            this.syncFormLiability(input, node, this.myForm);
            this.syncFormLiability(input, node, this.nav.briefForm);
            this.syncFormLiability(input, node, this.nav.detailForm);
        },
        syncFormLiability: function (input, node, form) {
            for (var index = 0; index < form.group(node.groupName).fields.length; index += 1) {
                var field = form.group(node.groupName).fields[index];
                if (field.attribute.type.toLowerCase() === "liability") {
                    field.val(input);
                }
            }
        },
        fillUserIdInfo: function (group, value) {
            group.field("birthDate").val(value.number.substr(6, 4) + "-" + value.number.substr(10, 2) + "-" + value.number.substr(12, 2));
            group.field("gender").val(parseInt(value.number.substr(16, 1)) % 2 === 0 ? "F" : "M");
        },
        initForm: function () {
            var formData = JSON.parse($(".form-container>script").text());
            Form.mergeValue(formData);
            var myForm = new DynamicForm(formData, ".form-container");

            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector("#tpl_checkout_information_form").innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("information", function (groupAttribute, value) {
                var group = createDynamicGroup("#tpl_checkout_group_information_statement", groupAttribute, value);
                group.fieldBuilders.boolean = CheckoutInformationBoolean;
                group.fieldBuilders.string = CheckoutInformationString;
                group.fieldBuilders.effectiveinsurance = CheckoutInformationEffectiveInsurance;
                return group;
            }).groupBuilder("common", function (groupAttribute, value) {
                return createDynamicGroup("#tpl_checkout_information_default_group", groupAttribute, value);
            }).groupBuilder("policy-holder", function (groupAttribute, value) {
                var group = createDynamicGroup("#tpl_checkout_information_customer_group", groupAttribute, value);
                if ($("#user-is-dealer").val() && $("#user-is-dealer").val() === "true") {
                    $.ajax("/ajax/policy-holder").done(function (result) {
                        if (result.total > 0) {
                            group.policyHolders = {};
                            for (var i = 0; i < result.total; i++) {
                                var policyHolder = result.items[i];
                                group.policyHolders[policyHolder.id] = policyHolder;
                                $("#policy-holder-selector").append("<option value='" + policyHolder.id + "'>" + policyHolder.name + "</option>")
                            }
                        }
                    }).fail(function (result) {
                        console.log("get policy holder error");
                    });
                    $(group.element).find("#policy-holder-selector").change(function () {
                        if (group.policyHolders[$(this).val()]) {
                            var data = group.policyHolders[$(this).val()];
                            data.birthDate = data.birthDate.substr(0, data.birthDate.indexOf("T"));
                            data.id = {
                                type: "1",
                                number: data.idNumber
                            };
                            group.val(data);
                        }
                    });

                } else {
                    $(group.element).find("#policy-holder-selector-label").hide();
                }
                $(group.element).find("#policy-holder-selector").hide();
                var commonCustomerSelfCheckbox = $(group.element).find(".common-customer-self");
                commonCustomerSelfCheckbox.parent().click(function () {
                    group.disableSelector();
                    commonCustomerSavedCheckbox.prop("checked", false);
                    commonCustomerSavedCheckbox.attr("checked", null);
                    if (!(commonCustomerSelfCheckbox.prop("checked") || commonCustomerSelfCheckbox.attr("checked"))) {
                        $.getJSON("/ajax/user/info", function (result) {
                            if (result.id) {
                                if (result.id.number) {
                                    result.id.type = "1";
                                } else {
                                    result.id = null;
                                }
                            }
                            if (result.birthDate) {
                                result.birthDate = result.birthDate.substr(0, result.birthDate.indexOf("T"));
                            }
                            group.val(result);
                            this.syncOwnData();
                        }.bind(this));
                    }
                }.bind(this));
                var commonCustomerSavedCheckbox = $(group.element).find(".common-customer-saved");
                commonCustomerSavedCheckbox.parent().click(function () {
                    commonCustomerSelfCheckbox.prop("checked", false);
                    commonCustomerSelfCheckbox.attr("checked", null);
                    if (commonCustomerSavedCheckbox.prop("checked") || commonCustomerSavedCheckbox.attr("checked")) {
                        group.disableSelector();
                    } else {
                        group.enableSelector();
                    }
                });
                group.disableSelector = function () {
                    $("#policy-holder-selector").hide();
                };
                group.enableSelector = function () {
                    $("#policy-holder-selector").show();
                };

                group.onChange(function (value, field) {
                    this.syncOwnData();
                    if (field.name === "id" && value.type == "1") {
                        this.fillUserIdInfo(group, value);
                    }
                }.bind(this));
                return group;
            }.bind(this)).groupBuilder("insured", function (groupAttribute, value) {
                var group = null;
                if (groupAttribute.multiple) {
                    group = createDynamicGroup("#tpl_checkout_information_insured_group", groupAttribute, value[0]);
                } else {
                    group = createDynamicGroup("#tpl_checkout_information_insured_group", groupAttribute, value);
                }
                group.uploadValue = function (titleList, dataList, thisGroup) {
                    var data = {};
                    for (var i = 0; i < titleList.length; i++) {
                        if (titleList[i] === "id") {
                            var values = dataList[i].split(":");
                            data.id = {type: values[0], number: values[1]};
                        } else {
                            data[titleList[i]] = dataList[i];
                        }
                    }
                    if ($(thisGroup.element).find(".field-list").length > 0) {
                        $(thisGroup.element).find(".field-list").eq(0).find("[name=insurant]").prop("checked", false).removeAttr("checked");
                        $(thisGroup.element).find(".field-list").eq(0).find("[name=insurant][value=2]").prop("checked", true).attr("checked", "checked");
                    } else {
                        $(thisGroup.element).find("[name=insurant]").prop("checked", false).removeAttr("checked");
                        $(thisGroup.element).find("[name=insurant][value=2]").prop("checked", true).attr("checked", "checked");

                    }
                    thisGroup.val(data);
                };

                $(group.element).find(".upload-insured").change(function (event) {
                    var files = event.target.files;
                    Papa.parse(files[0], {
                        complete: function (result) {
                            group.uploadValue(result.data[0], result.data[1], group);
                            if (result.data.length >= 2) {
                                for (var i = 2; i < result.data.length; i++) {
                                    var newGroup = group.addGroup();
                                    group.uploadValue(result.data[0], result.data[i], newGroup);
                                }
                            } else {
                                alert("文件内容错误");
                            }
                        },
                        error: function (a, b, c, d) {
                            alert("文件格式错误");
                        }
                    });
                });
                group.pnr = {
                    uploadMultiple: function (pnrData) {
                        var data = {};
                        var nameLine = null;
                        var index = 0;
                        for (; index < pnrData.length; index += 1) {
                            if (pnrData[index][0].indexOf("1") >= 0) {
                                nameLine = pnrData[index][0];
                                break;
                            }
                        }
                        data.name = this.getName(nameLine, 1);
                        data.id = {type: "1"};
                        data.id.number = this.getIdNumber(pnrData, 1);
                        group.val(data);
                        if (nameLine.indexOf("2.") >= 0) {
                            index = 2;
                            do {
                                data.name = this.getName(nameLine, index);
                                data.id = {type: "1"};
                                data.id.number = this.getIdNumber(pnrData, index);
                                group.addGroup().val(data);
                                index += 1;
                            } while (data.name !== null);
                        }
                    },
                    uploadSingle: function (pnrData) {
                        var data = {};
                        var index = 0;
                        for (; index < pnrData.length; index += 1) {
                            if (pnrData[index][0].indexOf("1") >= 0) {
                                data.name = this.getName(pnrData[index][0], 1);
                                break;
                            }
                        }
                        data.id = {type: "1"};
                        data.id.number = this.getIdNumber(pnrData, 1);
                        group.val(data);
                    },
                    getName: function (data, index) {
                        var array = data.split(/\s+/);
                        var di = 0;
                        for (; di < array.length; di += 1) {
                            if (array[di].indexOf(String(index)) >= 0) {
                                return array[di].substr(2);
                            }
                        }
                        return null;
                    },
                    getIdNumber: function (data, index) {
                        var array = [];
                        for (var di = 0; di < data.length; di += 1) {
                            if (data[di][0].indexOf("P" + index) >= 0) {
                                array = data[di][0].split(/\s+/);
                                return array[array.length - 1].substr(2, array[array.length - 1].indexOf("/") - 2);
                            }
                        }
                        return null;
                    },
                    uploadFlight: function (data) {
                        var index = 0;
                        var flightInfo = null;
                        for (; index < data.length; index += 1) {
                            if (data[index][0].indexOf("1") >= 0) {
                                flightInfo = data[index + 1][0].split(/\s+/);
                                if (flightInfo[0] === "") {
                                    flightInfo.splice(0, 1);
                                }

                                myForm.group("flight").val({
                                    flightNo: flightInfo[1],
                                    flightTime: this.getFlightTime(flightInfo[3], flightInfo[6])
                                });
                                return;
                            }

                        }
                    },
                    getFlightTime: function (dateData, timeData) {
                        return new Date().getFullYear() +
                            "-" + this.getMonthNumber(dateData.substr(4)) +
                            "-" + parseInt(dateData.substr(2, 2), 10) +
                            "T" + timeData.substr(0, 2) +
                            ":" + timeData.substr(2, 2) +
                            ":00";
                    },
                    getMonthNumber: function (name) {
                        var months = {
                            "jan": "01",
                            "feb": "02",
                            "mar": "03",
                            "apr": "04",
                            "may": "05",
                            "jun": "06",
                            "jul": "07",
                            "aug": "08",
                            "sep": "09",
                            "sept": "09",
                            "oct": "10",
                            "nov": "11",
                            "dec": "12"
                        };
                        return months[name.toLowerCase()];
                    }
                };

                $(group.element).find(".upload-pnr").change(function (event) {
                    var files = event.target.files;
                    Papa.parse(files[0], {
                        complete: function (result) {
                            if (groupAttribute.multiple) {
                                group.pnr.uploadMultiple(result.data);
                            } else {
                                group.pnr.uploadSingle(result.data);
                            }
                            if (myForm.group("flight")) {
                                group.pnr.uploadFlight(result.data);
                            }
                        },
                        error: function (a, b, c, d) {
                            $.alert({size: "small", content: "文件格式错误"});
                        }
                    });
                });

                $(".btn-pnr").click(function () {
                    $(".pnr-form-error").hide();
                    Papa.parse($("#pnr-content").val(), {
                        complete: function (result) {
                            if (result.data.length == 0) {
                                $(".pnr-form-error").show();
                                return;
                            }
                            if (groupAttribute.multiple) {
                                group.pnr.uploadMultiple(result.data);
                            } else {
                                group.pnr.uploadSingle(result.data);
                            }
                            if (myForm.group("flight")) {
                                group.pnr.uploadFlight(result.data);
                            }
                            $("#copy-pnr").modal("hide");
                        },
                        error: function (a, b, c, d) {
                            $(".pnr-form-error").show();
                            // $.alert({size: "small", content: "PNR格式错误"});
                        }
                    });
                });

                if (groupAttribute.multiple) {
                    group.additionalGroup = function (additionalGroupAttribute, additionalValue) {
                        var additionalGroup = createDynamicGroup("#tpl_checkout_information_insured_group_additional", additionalGroupAttribute, additionalValue);
                        additionalGroup.index = $(group.element).find(".field-list").length - 1;
                        additionalGroup.onChange(myForm.onChangeHandler).editable(myForm.editableHandler).build();
                        $(additionalGroup.element).find(".btn-delete").click(function () {
                            var amount = parseInt($(".customer .amount").eq(0).text(), 10) - 1;
                            $(".customer .amount").text(amount);
                            $(".total-unit .amount").text(amount * parseInt(myForm.group("product").field("unit").val(), 10));

                            $(additionalGroup.element).remove();
                            for (var i = 0; i < myForm.groups.length; i++) {
                                if (myForm.groups[i].name === additionalGroup.name && myForm.groups[i].index === additionalGroup.index) {
                                    myForm.groups.splice(i, 1);
                                    break;
                                }
                            }
                            this.getPrice(myForm.val());
                        }.bind(this));
                        $(group.element).find("[name='insurant'][value='1']").parent().click(function (event) {
                            if ($(event.currentTarget).find("input").prop("checked") || $(event.currentTarget).find("input").attr("checked")) {
                                return;
                            }
                            $.getJSON("/ajax/user/info", function (result) {
                                additionalGroup.val(result);
                            });
                        });

                        $(additionalGroup.element).find(".upload-insured").change(function (event) {
                            var files = event.target.files;
                            Papa.parse(files[0], {
                                complete: function (result) {
                                    group.uploadValue(result.data[0], result.data[1], additionalGroup);
                                    if (result.data.length >= 2) {
                                        for (var i = 2; i < result.data.length; i++) {
                                            var newGroup = group.addGroup();
                                            group.uploadValue(result.data[0], result.data[i], newGroup);
                                        }
                                    } else {
                                        alert("文件内容错误");
                                    }
                                },
                                error: function (a, b, c, d) {
                                    alert("文件格式错误");
                                }
                            });
                        });
                        additionalGroup.onChange(function (value, field) {
                            if (field.name === "id" && value.type == "1") {
                                this.fillUserIdInfo(group, value);
                            }
                        }.bind(this));

                        return additionalGroup;
                    }.bind(this);
                    $(group.element).find(".add-group").click(function () {
                        group.addGroup();
                        this.getPrice(myForm.val());
                    }.bind(this));

                    group.addGroup = function () {
                        var amount = parseInt($(".customer .amount").eq(0).text(), 10) + 1;
                        $(".customer .amount").text(amount);
                        $(".total-unit .amount").text(amount * parseInt(myForm.group("product").field("unit").val(), 10));

                        var newGroup = group.additionalGroup(groupAttribute, {});
                        myForm.groups.push(newGroup);

                        $(group.element).find(".add-group").parents(".field-list").before(newGroup.element);
                        return newGroup;
                    };
                }

                if (value && value.length > 1) {
                    for (var i = 1; i < value.length; i++) {
                        var amount = parseInt($(".customer .amount").eq(0).text(), 10) + 1;
                        $(".customer .amount").text(amount);
                        $(myForm.element).find(".customer .amount").text(amount);
                        var newGroup = group.additionalGroup(groupAttribute, value[i]);
                        myForm.groups.push(newGroup);
                        var fieldList = $(group.element).find(".field-list");
                        fieldList.eq(fieldList.length - 1).before(newGroup.element);
                    }
                }

                var insuredSelf = $(group.element).find(".insured-self");
                insuredSelf.parent().click(function () {
                    if (insuredSelf.prop("checked") || insuredSelf.attr("checked")) {
                        this.insuredSelf = this.anyInsuredSelf();
                        this.inactiveSelfInsured();
                    } else {
                        this.activeSelfInsured();
                        this.insuredSelf = true;
                        this.syncOwnData();
                    }
                }.bind(this));
                group.onChange(function (value, field) {
                    if (field.name === "id" && value.type == "1") {
                        this.fillUserIdInfo(group, value);
                    }
                }.bind(this));
                return group;
            }.bind(this)).groupBuilder("emergency-contact", function (groupAttribute, value) {
                var group = null;
                if (groupAttribute.multiple) {
                    if (value) {
                        group = createDynamicGroup("#tpl_checkout_information_emergency_contact_group", groupAttribute, value[0]);
                    } else {
                        group = createDynamicGroup("#tpl_checkout_information_emergency_contact_group", groupAttribute, value);
                    }

                    group.additionalGroup = function (additionalGroupAttribute, additionalValue) {
                        var additionalGroup = createDynamicGroup("#tpl_checkout_information_emergency_contact_group_additional", additionalGroupAttribute, additionalValue);
                        additionalGroup.index = $(group.element).find(".field-list").length - 1;
                        additionalGroup.onChange(myForm.onChangeHandler).editable(myForm.editableHandler).build();
                        $(additionalGroup.element).find(".btn-delete").click(function () {
                            $(additionalGroup.element).remove();
                            for (var index = 0; index < myForm.groups.length; index += 1) {
                                if (myForm.groups[index].name === additionalGroup.name && myForm.groups[index].index === additionalGroup.index) {
                                    myForm.groups.splice(index, 1);
                                    return;
                                }
                            }
                        });

                        return additionalGroup;
                    };
                    $(group.element).find(".add-group").click(function () {
                        group.addGroup();
                    });

                    group.addGroup = function () {
                        var newGroup = group.additionalGroup(groupAttribute, {});

                        myForm.groups.push(newGroup);

                        $(group.element).find(".add-group").parents(".field-list").before(newGroup.element);
                        return newGroup;
                    };

                } else {
                    group = createDynamicGroup("#tpl_checkout_information_emergency_contact_group", groupAttribute, value);
                }

                if (value && value.length > 1) {
                    for (var index = 1; index < value.length; index += 1) {
                        var newGroup = group.additionalGroup(groupAttribute, value[index]);
                        newGroup.index = $(group).find(".field-list").length - 1;
                        myForm.groups.push(newGroup);
                        var fieldList = $(group.element).find(".field-list");
                        fieldList.eq(fieldList.length - 1).before(newGroup.element);
                    }
                }
                return group;
            }).withRule("heavySick", function (data) {
                for (var key in data) {
                    var groupFieldName = key.split(".");
                    var field = myForm.group(groupFieldName[0]).field(groupFieldName[1]);
                    var rules = data[key];
                    var rule = field.getRule(rules);
                    if (rule && rule.rule) {
                        for (var targetKey in rule.rule) {
                            var targetGroupFieldName = targetKey.split(".");
                            var targetRule = rule.rule[targetKey];
                            var targetField = myForm.group(targetGroupFieldName[0]).field(targetGroupFieldName[1]);
                            targetField.refreshFieldValues(targetRule);
                        }
                    }
                }
            }).onChange(function (input, node) {
                if (node.name === "birthDate") {
                    var field = myForm.group(node.groupName).field("age");
                    if (field) {
                        field.val(CommonUtils.dateToAge(input));
                    }
                    if (this.heavySickRule) {
                        this.heavySickRule();
                    }
                } else if (node.name === "legalBeneficiary") {
                    var index = 0;
                    var group = myForm.group(node.groupName);
                    for (; index < group.fields.length; index += 1) {
                        var beneficiaryField = group.fields[index];
                        if (beneficiaryField.name === "legalBeneficiary" || beneficiaryField.name === "type") {
                            return;
                        }
                        if (input === "Y") {
                            beneficiaryField.hide();
                        } else {
                            beneficiaryField.show();
                        }
                    }
                } else if (node.groupName === "insured" && node.name === "job") {
                    if (this.jobRestrict && this.restrictedJobList.indexOf(node.val()) < 0) {
                        node.element.querySelector(".occupation-leaf").value = "";
                    }
                }
                if (this.heavySickRule && node.attribute.type.toLowerCase() === "liability") {
                    this.syncLiability(input, node);
                }
                this.refreshData(input, node);
                if (node.validate(["notNull"])) {
                    this.getPrice(myForm.val());
                    $(".summary-item").each(function (index, item) {
                        var liabilityList = $(item).find(".summary-cell");
                        liabilityList.each(function (cellIndex, cell) {
                            if ($(cell).data("name") === node.name) {
                                $(cell).find(".amount").text(node.val());
                            }
                        });
                    });
                }

            }.bind(this)).onInit(function () {
                for (var i = 0; i < myForm.groups.length; i++) {
                    var group = myForm.groups[i];
                    for (var j = 0; j < group.fields.length; j++) {
                        var field = group.fields[j];
                        $(".summary-item").each(function () {
                            var liabilityList = $(this).find(".summary-cell");
                            liabilityList.each(function () {
                                if ($(this).data("name") === field.name) {
                                    $(this).find(".amount").text(field.val());
                                }
                            });
                        });
                    }
                }
                var legalBeneficiaryField = myForm.group("beneficiary").field("legalBeneficiary");
                if (legalBeneficiaryField && legalBeneficiaryField.name === "legalBeneficiary" && legalBeneficiaryField.val() === "Y") {
                    var index = 0;
                    var beneficiaryGroup = myForm.group(legalBeneficiaryField.groupName);
                    for (; index < beneficiaryGroup.fields.length; index += 1) {
                        var beneficiaryField = beneficiaryGroup.fields[index];
                        if (beneficiaryField.name !== "legalBeneficiary" && beneficiaryField.name !== "type") {
                            beneficiaryField.hide();
                        }
                    }
                }
                for (var groupIndex = 0; groupIndex < myForm.groups.length; groupIndex++) {
                    var birthDateField = myForm.groups[groupIndex].field("birthDate");
                    var ageField = myForm.groups[groupIndex].field("age");
                    if (birthDateField && ageField) {
                        ageField.val(CommonUtils.dateToAge(birthDateField.val()));
                    }
                }
                this.getPrice(myForm.val());
                $(".total-unit .amount").text(parseInt(myForm.group("product").field("unit").val(), 10));
            }.bind(this)).init();

            Form.myForm = myForm;

            $(".order-main").show();
            adjustFooter();

            this.bindRelation();

            $(myForm.element).find(".total-period").text(myForm.group("plan").field("period").val().displayName);

            if (myForm.group("information")) {
                var displayArray = [];
                for (var i = 0; i < myForm.groups.length; i++) {
                    displayArray.push(!myForm.groups[i].hidden);
                }

                var hash = window.location.hash;
                if (hash.indexOf("#step2") >= 0) {
                    for (var i = 0; i < myForm.groups.length; i++) {
                        if (myForm.groups[i].name === "information") {
                            $(myForm.groups[i].element).hide();
                        } else {
                            $(myForm.groups[i].element).toggle(displayArray[i]);
                        }
                    }
                    $(".btn-confirm").hide();
                    $(".btn-submit").show();
                    $(".summary-section").show();
                } else {
                    for (var i = 0; i < myForm.groups.length; i++) {
                        if (myForm.groups[i].name === "information") {
                            $(myForm.groups[i].element).show();
                        } else {
                            $(myForm.groups[i].element).hide();
                        }
                    }
                    $(".btn-confirm").show();
                    $(".btn-submit").hide();
                    $(".summary-section").hide();
                }
                window.addEventListener("popstate", function () {
                    if (window.location.hash.indexOf("step2") >= 0) {
                        $(myForm.group("information").element).fadeOut("slow", function () {
                            for (var i = 0; i < myForm.groups.length; i++) {
                                if (myForm.groups[i].name !== "information" && displayArray[i]) {
                                    $(myForm.groups[i].element).fadeIn("slow");
                                }
                            }
                            $(".btn-submit").fadeIn("slow");
                            $(".summary-section").fadeIn("slow");
                        });
                        $(".btn-confirm").fadeOut("slow");

                    } else {
                        for (var i = 0; i < myForm.groups.length; i++) {
                            $(myForm.groups[i].element).fadeOut("slow");
                        }
                        $(".btn-submit").fadeOut("slow", function () {
                            $(myForm.group("information").element).fadeIn("slow");
                            $(".btn-confirm").fadeIn("slow");
                        });
                        $(".summary-section").fadeOut("slow");

                    }
                });

            } else {
                $(".btn-confirm").hide();
            }
            $(".btn-confirm").click(function () {
                if (myForm.group("information").validate()) {
                    window.location.href = "#step2";
                } else {
                    $("#ticket").modal('show');
                }
            });

            $("#submit-ticket").click(function () {
                if (!$("#ticket-form").valid()) {
                    return false;
                }
                var data = {};
                data.fullName = $("#fullName").val();
                data.phone = $("#phone").val();
                $.ajax({
                    url: "/ajax/ticket",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function () {
                    $("#ticket").modal('hide');
                }.bind(this)).fail(function () {
                    $.alert({
                        "size": "small",
                        "content": "提交失败"
                    });
                }.bind(this));
            });

            $(".btn-submit").click(function () {
                var orderId = $("#orderId").val();
                var checkoutId = $("#checkoutId").val();
                console.log(myForm.val());
                if (!myForm.validate()) {
                    $("body,html").animate({
                        scrollTop: $(".attribute-message.show").eq(0).offset().top - 50
                    }, 200);
                    return;
                }
                if (!$("#agree-policy").prop("checked")) {
                    $("#agree-policy-tip").show();
                    return false;
                } else {
                    $("#agree-policy-tip").hide();
                }
                var data = {};
                data.productId = this.productId;
                data.checkoutId = checkoutId;
                data.value = myForm.val();
                $.ajax({
                    url: "/ajax/place-order",
                    type: "post",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function () {
                    localStorage.setItem("checkout_form_data", undefined);
                    localStorage.setItem("checkout_form_id", undefined);
                    if (orderId) {
                        window.location.href = "/order/" + orderId + "/preview";
                    } else {
                        window.location.href = "/checkout/preview?id=" + checkoutId;
                    }
                }).fail(function (result) {
                    if (result.status == 403) {
                        $.alert({
                            size: "small",
                            content: "订单发生变化，无法提交！"
                        });
                        return;
                    }
                    Form.processError(result.responseJSON);
                });
            }.bind(this));
            var productRule = $(".product-rule");
            if (productRule.length > 0 && productRule.text()) {
                if (productRule.data("funcname") === "cascade") {
                    myForm.ruleCascade(JSON.parse(productRule.text()));
                } else if (productRule.data("funcname") === "heavySick") {
                    this.heavySickRule = function () {
                        myForm.rule("heavySick", JSON.parse(productRule.text()));
                    };
                }
            }

        },
        processError: function (response) {
            if (response.invalidFields) {
                var fields = response.invalidFields;
                for (var i = 0; i < fields.length; i++) {
                    var paths = fields[i].path.split(".");
                    var message = fields[i].message;
                    this.myForm.group(paths[0]).field(paths[1]).error(message);
                }
                $("body").animate({scrollTop: $(".attribute-message.show").eq(0).offset().top - 50}, 200);
            } else {
                $.alert({
                    "size": "small",
                    "content": response.message
                });
            }

        },
        getPrice: function (data) {
            console.log("get price");
            $.ajax({
                url: "/ajax/product/" + this.productId + "/price",
                type: "put",
                data: JSON.stringify(data),
                headers: {"Content-Type": "application/json"},
                dataType: "json"
            }).done(function (result) {
                $(".summary-item").find(".cost").text(result.price);
                if (result.price !== result.sale_price) {
                    $(".total-price").find(".amount").text(result.price).show();
                }
                $(".sale-price").find(".amount").text(result.sale_price);
                $(".invoice-fee").find(".amount").text(result.invoice_fee);
                $(".total-sale-price").find(".amount").text(result.total);
            }).fail(function (result) {
                console.log(result);
            });
        }
    };
    Form.init();

});
