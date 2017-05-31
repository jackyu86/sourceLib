/**
 * @author miller
 */
'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var vendorListController = require("vendor/controller/vendor-list-controller");
var vendorUpdateController = require("vendor/controller/vendor-update-controller");
var vendorCreateController = require("vendor/controller/vendor-create-controller");

angular
    .module('vendor', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function($stateProvider) {
        $stateProvider.state('console.vendor', {
            url: "/vendor",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state('console.vendor.list', {
            url: "/list",
            templateUrl: "/admin/vendor/view/vendor.list.html",
            controller: vendorListController
        }).state('console.vendor.create', {
            url: "/create",
            templateUrl: "/admin/vendor/view/vendor.create.html",
            controller: vendorCreateController
        }).state('console.vendor.update', {
            url: "/:id",
            templateUrl: "/admin/vendor/view/vendor.update.html",
            controller: vendorUpdateController
        });
    })
    .filter("insuranceVendorLevel", function() {
        return function(level) {
            switch (level) {
                case "INSURANCE_GROUP":
                    return "保险集团";
                case "INSURANCE_SUB_COMPANY":
                    return "保险子公司";
                case "INSURANCE_BRANCH":
                    return "保险分公司";
                case "INSURANCE_AGENT":
                    return "保险中介公司";
                default:
                    return "未知级别";
            }
        }
    })
    .run(function(Console) {
        var messages = [{key: "供应商管理", value: "供应商管理"}];
        Console.messages.add(messages);
        Console.menu.add({
            permission: 'vendor.admin.GET',
            name: '供应商管理',
            state: 'console.vendor.list',
            icon: 'fa-truck',
            priority: 6
        });
    })
;