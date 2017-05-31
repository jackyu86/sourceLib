/**
 * @author miller
 */
'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var productListController = require("/admin/product/controller/product-list-controller");
var productUpdateController = require("/admin/product/controller/product-update-controller");
var productCreateController = require("/admin/product/controller/product-create-controller");
var productAuditListController = require("/admin/product/controller/product-audit-list-controller");
var productAuditUpdateController = require("/admin/product/controller/product-audit-update-controller");
var productSerialListController = require("/admin/product/serial/controller/product-serial-list-controller");
var productSerialCreateController = require("/admin/product/serial/controller/product-serial-create-controller");
var productSerialUpdateController = require("/admin/product/serial/controller/product-serial-update-controller");

angular
    .module('product', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function($stateProvider) {
        $stateProvider.state("console.product", {
            url: "/product",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.product.list", {
            url: "/list",
            templateUrl: "/admin/product/view/product.list.html",
            controller: productListController
        }).state("console.product.create", {
            url: "/create",
            templateUrl: "/admin/product/view/product.create.html",
            controller: productCreateController
        }).state("console.product.c", {
            url: "/c",
            templateUrl: "/admin/product/view/product.create.new.html",
            controller: productCreateController
        }).state("console.product.update", {
            url: "/update/:id",
            templateUrl: "/admin/product/view/product.update.html",
            controller: productUpdateController
        }).state("console.product.audit", {
            url: "/audit",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.product.audit.list", {
            url: "/list",
            templateUrl: "/admin/product/view/product.audit.list.html",
            controller: productAuditListController
        }).state("console.product.audit.update", {
            url: "/update/:id",
            templateUrl: "/admin/product/view/product.audit.update.html",
            controller: productAuditUpdateController
        }).state("console.product.serial", {
            url: "/serial",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.product.serial.list", {
            url: "/list",
            templateUrl: "/admin/product/serial/view/product-serial.list.html",
            controller: productSerialListController
        }).state("console.product.serial.create", {
            url: "/create",
            templateUrl: "/admin/product/serial/view/product-serial.create.html",
            controller: productSerialCreateController
        }).state("console.product.serial.update", {
            url: "/update/:id",
            templateUrl: "/admin/product/serial/view/product-serial.update.html",
            controller: productSerialUpdateController
        })
        ;
    })
    .filter("insurancePeriodUnit", function() {
        return function(unit) {
            switch (unit) {
                case"DAY":
                    return "天";
                case "MONTH":
                    return "月";
                case "YEAR":
                    return "年";
                case "AGE":
                    return "岁";
                case "LIFE":
                    return "一生";
                default:
                    return "未知类型";
            }
        }
    })
    .filter("insuranceDividendType", function() {
        return function(type) {
            switch (type) {
                case "HL01":
                    return "累积生息";
                case "HL02":
                    return "现金领取";
                case "HL03":
                    return "抵交保费";
                case "HL04":
                    return "购买增额交清保险";
                default:
                    return "未知类型"
            }
        }
    })
    .run(function(Console) {
        Console.messages.add([
            {key: "产品管理", value: "产品管理"},
            {key: "产品列表", value: "产品列表"},
            {key: "产品系列", value: "产品系列"},
            {key: "产品审核", value: "产品审核"}
        ]);
        Console.menu.add({
            name: "产品管理",
            permission: "product.admin.GET",
            state: "console.product",
            icon: "fa-truck",
            priority: 6,
            children: [
                {
                    name: "产品列表",
                    state: "console.product.list",
                    icon: "fa-truck",
                    priority: 1
                },
                {
                    name: "产品系列",
                    state: "console.product.serial.list",
                    icon: "fa-truck",
                    priority: 2
                },
                {
                    name: "产品审核",
                    state: "console.product.audit.list",
                    icon: "fa-truck",
                    priority: 3
                }
            ]
        })
    });