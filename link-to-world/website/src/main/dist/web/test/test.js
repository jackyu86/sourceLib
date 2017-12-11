var myForm = new DynamicForm(descriptor, ".container");

document.querySelector(".btn-submit").onclick = function() {
    console.log(myForm.valid());
}
alert(new Date().getTime() - time);

var form1 = {
    "value": {},
    "groups": [{
        "name": "insured",
        "displayName": "被保险人",
        "optional": null,
        "multiple": null,
        "fields": [{
            "name": "age",
            "groupName": "insured",
            "type": {
                "name": "Age",
                "enumType": false,
                "displayAs": [],
                "options": [{
                    "name": "required",
                    "displayName": "Required",
                    "message": null,
                    "type": "Boolean",
                    "javaType": "java.lang.Boolean",
                    "defaultValue": false
                }, {
                    "name": "min",
                    "displayName": "Min",
                    "message": "年龄不大于{}",
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }, {
                    "name": "max",
                    "displayName": "Max",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }]
            },
            "displayName": "承保年龄",
            "options": {"min": 10, "max": 80},
            "defaultValue": null,
            "editable": false,
            "multiple": null,
            "hidden": false,
            "displayAs": null
        }]
    }, {
        "name": "product",
        "displayName": "产品",
        "optional": null,
        "multiple": null,
        "fields": [{
            "name": "unit",
            "groupName": "product",
            "type": {
                "name": "Unit",
                "enumType": false,
                "displayAs": [],
                "options": [{
                    "name": "required",
                    "displayName": "Required",
                    "message": null,
                    "type": "Boolean",
                    "javaType": "java.lang.Boolean",
                    "defaultValue": false
                }, {
                    "name": "min",
                    "displayName": "最小份数",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }, {
                    "name": "max",
                    "displayName": "最大份数",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }]
            },
            "displayName": "限购份数",
            "options": {"min": 1, "max": 1},
            "defaultValue": 1,
            "editable": false,
            "multiple": null,
            "hidden": false,
            "displayAs": null
        }]
    }]
};

var form = {
    "value": {},
    "groups": [{
        "name": "insured",
        "displayName": "被保险人",
        "optional": null,
        "multiple": null,
        "fields": [{
            "name": "age",
            "groupName": "insured",
            "type": {
                "name": "Age",
                "enumType": false,
                "displayAs": [],
                "options": [{
                    "name": "required",
                    "displayName": "Required",
                    "message": null,
                    "type": "Boolean",
                    "javaType": "java.lang.Boolean",
                    "defaultValue": false
                }, {
                    "name": "min",
                    "displayName": "Min",
                    "message": "年龄不大于{}",
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }, {
                    "name": "max",
                    "displayName": "Max",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }]
            },
            "displayName": "承保年龄",
            "options": {"min": 10, "max": 80},
            "defaultValue": null,
            "editable": true,
            "multiple": null,
            "hidden": false,
            "displayAs": null
        }]
    }, {
        "name": "product",
        "displayName": "产品",
        "optional": null,
        "multiple": null,
        "fields": [{
            "name": "unit",
            "groupName": "product",
            "type": {
                "name": "Unit",
                "enumType": false,
                "displayAs": [],
                "options": [{
                    "name": "required",
                    "displayName": "Required",
                    "message": null,
                    "type": "Boolean",
                    "javaType": "java.lang.Boolean",
                    "defaultValue": false
                }, {
                    "name": "min",
                    "displayName": "最小份数",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }, {
                    "name": "max",
                    "displayName": "最大份数",
                    "message": null,
                    "type": "Integer",
                    "javaType": "java.lang.Integer",
                    "defaultValue": null
                }]
            },
            "displayName": "限购份数",
            "options": {"min": 1, "max": 5},
            "defaultValue": 1,
            "editable": true,
            "multiple": null,
            "hidden": false,
            "displayAs": null
        }]
    }]
};