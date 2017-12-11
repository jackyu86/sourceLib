/**
 * @author miller
 */
'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var messages = require('/admin/ajax/i18n/form!json');
var insuranceCategoryController = require("insurance/category/controller/insurance-category-controller");
var insuranceCategoryUpdateController = require("insurance/category/controller/insurance-category-update-controller");
var insuranceClauseListController = require("insurance/clause/controller/insurance-clause-list-controller");
var insuranceClauseCreateController = require("insurance/clause/controller/insurance-clause-create-controller");
var insuranceClauseUpdateController = require("insurance/clause/controller/insurance-clause-update-controller");
var insuranceJobListController = require("insurance/job/controller/insurance-job-list-controller");
var insuranceJobUpdateController = require("insurance/job/controller/insurance-job-update-controller");
var insuranceJobTreeListController = require("insurance/job/controller/insurance-job-tree-list-controller");
var insuranceJobTreeUpdateController = require("insurance/job/controller/insurance-job-tree-update-controller");
var insuranceJobTreeCreateController = require("insurance/job/controller/insurance-job-tree-create-controller");
var insuranceFormGroupListController = require("insurance/form/controller/insurance-form-group-list-controller");
var insuranceFormGroupUpdateController = require("insurance/form/controller/insurance-form-group-update-controller");
var insuranceFormFieldListController = require("insurance/form/controller/insurance-form-field-list-controller");
var insuranceFormFieldUpdateController = require("insurance/form/controller/insurance-form-field-update-controller");
var insuranceFormFieldCreateController = require("insurance/form/controller/insurance-form-field-create-controller");
var insuranceLiabilityGroupCreateController = require("insurance/liability/controller/insurance-liability-group-create-controller");
var insuranceLiabilityGroupUpdateController = require("insurance/liability/controller/insurance-liability-group-update-controller");
var insuranceLiabilityGroupListController = require("insurance/liability/controller/insurance-liability-group-list-controller");
var insuranceLiabilityListController = require("insurance/liability/controller/insurance-liability-list-controller");
var insuranceLiabilityCreateController = require("insurance/liability/controller/insurance-liability-create-controller");
var insuranceLiabilityUpdateController = require("insurance/liability/controller/insurance-liability-update-controller");
var insuranceListController = require("insurance/controller/insurance-list-controller");
var insuranceCreateController = require("insurance/controller/insurance-create-controller");
var insuranceUpdateController = require("insurance/controller/insurance-update-controller");
var insuranceClaimListController = require("insurance/claim/controller/insurance-claim-list-controller");
var insuranceClaimCreateController = require("insurance/claim/controller/insurance-claim-create-controller");
var insuranceClaimUpdateController = require("insurance/claim/controller/insurance-claim-update-controller");

angular
    .module('insurance', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function($stateProvider) {
        $stateProvider.state('console.insurance', {
            url: "/insurance",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.list", {
            url: "/list",
            templateUrl: "/admin/insurance/view/insurance.list.html",
            controller: insuranceListController
        }).state("console.insurance.create", {
            url: "/create",
            templateUrl: "/admin/insurance/view/insurance.create.html",
            controller: insuranceCreateController
        }).state("console.insurance.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/view/insurance.update.html",
            controller: insuranceUpdateController
        }).state("console.insurance.category", {
            url: "/category",
            templateUrl: "/admin/insurance/category/view/insurance-category.html",
            controller: insuranceCategoryController
        }).state("console.insurance.category.update", {
            url: "/:id",
            params: {node: null},
            templateUrl: "/admin/insurance/category/view/insurance-category.update.html",
            controller: insuranceCategoryUpdateController
        }).state("console.insurance.clause", {
            url: "/clause",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.clause.list", {
            url: "/list",
            templateUrl: "/admin/insurance/clause/view/insurance-clause.list.html",
            controller: insuranceClauseListController
        }).state("console.insurance.clause.create", {
            url: "/create",
            templateUrl: "/admin/insurance/clause/view/insurance-clause.create.html",
            controller: insuranceClauseCreateController
        }).state("console.insurance.clause.update", {
            url: "/:id",
            templateUrl: "/admin/insurance/clause/view/insurance-clause.update.html",
            controller: insuranceClauseUpdateController
        }).state("console.insurance.job-tree", {
            url: "/job-tree",
            template: "<md-content ui-view></md-content>"
        }).state("console.insurance.job-tree.list", {
            url: "/list",
            templateUrl: "/admin/insurance/job/view/insurance-job-tree.list.html",
            controller: insuranceJobTreeListController
        }).state("console.insurance.job-tree.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/job/view/insurance-job-tree.update.html",
            controller: insuranceJobTreeUpdateController
        }).state("console.insurance.job-tree.create", {
            url: "/create",
            templateUrl: "/admin/insurance/job/view/insurance-job-tree.create.html",
            controller: insuranceJobTreeCreateController
        }).state("console.insurance.job", {
            url: "/job-tree/:treeId",
            params: {tree: null},
            templateUrl: "/admin/insurance/job/view/insurance-job.list.html",
            controller: insuranceJobListController
        }).state("console.insurance.job.update", {
            url: "/update/:id",
            params: {node: null},
            templateUrl: "/admin/insurance/job/view/insurance-job.update.html",
            controller: insuranceJobUpdateController
        }).state("console.insurance.form-group", {
            url: "/form-group",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.form-group.list", {
            url: "/list",
            templateUrl: "/admin/insurance/form/view/insurance-form-group.list.html",
            controller: insuranceFormGroupListController
        }).state("console.insurance.form-group.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/form/view/insurance-form-group.update.html",
            controller: insuranceFormGroupUpdateController
        }).state("console.insurance.form-field", {
            url: "/form-field",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.form-field.list", {
            url: "/list/:groupId",
            params: {group: null},
            templateUrl: "/admin/insurance/form/view/insurance-form-field.list.html",
            controller: insuranceFormFieldListController
        }).state("console.insurance.form-field.update", {
            url: "/update/:id",
            params: {group: null},
            templateUrl: "/admin/insurance/form/view/insurance-form-field.update.html",
            controller: insuranceFormFieldUpdateController
        }).state("console.insurance.form-field.create", {
            url: "/create/",
            params: {group: null},
            templateUrl: "/admin/insurance/form/view/insurance-form-field.create.html",
            controller: insuranceFormFieldCreateController
        }).state("console.insurance.liability-group", {
            url: "/liability-group",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.liability-group.list", {
            url: "/",
            templateUrl: "/admin/insurance/liability/view/insurance-liability-group.list.html",
            controller: insuranceLiabilityGroupListController
        }).state("console.insurance.liability-group.create", {
            url: "/create",
            templateUrl: "/admin/insurance/liability/view/insurance-liability-group.create.html",
            controller: insuranceLiabilityGroupCreateController
        }).state("console.insurance.liability-group.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/liability/view/insurance-liability-group.update.html",
            controller: insuranceLiabilityGroupUpdateController
        }).state("console.insurance.liability", {
            url: "/liability",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.liability.list", {
            url: "/list/:groupId",
            params: {group: null},
            templateUrl: "/admin/insurance/liability/view/insurance-liability.list.html",
            controller: insuranceLiabilityListController
        }).state("console.insurance.liability.create", {
            url: "/create/:groupId",
            templateUrl: "/admin/insurance/liability/view/insurance-liability.create.html",
            controller: insuranceLiabilityCreateController
        }).state("console.insurance.liability.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/liability/view/insurance-liability.update.html",
            controller: insuranceLiabilityUpdateController
        }).state("console.insurance.claim", {
            url: "/claim",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.insurance.claim.list", {
            url: "/list",
            templateUrl: "/admin/insurance/claim/view/insurance-claim.list.html",
            controller: insuranceClaimListController
        }).state("console.insurance.claim.create", {
            url: "/create",
            templateUrl: "/admin/insurance/claim/view/insurance-claim.create.html",
            controller: insuranceClaimCreateController
        }).state("console.insurance.claim.update", {
            url: "/update/:id",
            templateUrl: "/admin/insurance/claim/view/insurance-claim.update.html",
            controller: insuranceClaimUpdateController
        })
        ;
    })
    .filter("clauseType", function() {
        return function(type) {
            switch (type) {
                case "MAIN":
                    return "主要条款";
                case "ADDITIONAL":
                    return "附加条款";
                default:
                    return "未知类型";
            }
        }
    }).run(function(Console) {
    Console.messages.add([
        {key: "产品基础数据", value: "产品基础数据"},
        {key: "保险分类", value: "保险分类"},
        {key: "保险条款", value: "保险条款"},
        {key: "职业管理", value: "职业管理"},
        {key: "投保单", value: "投保单"},
        {key: "保险责任", value: "保险责任"},
        {key: "险种配置", value: "险种配置"},
        {key: "vendor.vendor", value: "供应商"},
        {key: "理赔指引", value: "理赔指引"}
    ]);

    Console.messages.add(messages);
    Console.menu.add({
        name: '产品基础数据',
        permission: 'insurance.admin.GET',
        state: 'console.insurance',
        icon: 'fa-truck',
        priority: 7,
        children: [
            {
                name: "保险分类",
                state: "console.insurance.category",
                icon: "fa-phone-square",
            },
            {
                name: "保险条款",
                state: "console.insurance.clause.list",
                icon: "fa-info",
            },
            {
                name: "职业管理",
                state: "console.insurance.job-tree.list",
                icon: "fa-truck",
            },
            {
                name: "投保单",
                state: "console.insurance.form-group.list",
                icon: "fa-truck",
            },
            {
                name: "保险责任",
                state: "console.insurance.liability-group.list",
                icon: "fa-truck",
            },
            {
                name: "险种配置",
                state: "console.insurance.list",
                icon: "fa-truck"
            },
            {
                permission: 'vendor.admin.GET',
                name: 'vendor.vendor',
                state: 'console.vendor.list',
                icon: 'fa-truck',
                priority: 6
            },
            {
                name: "理赔指引",
                state: "console.insurance.claim.list",
                icon: "fa-truck"
            }
        ]
    });
});