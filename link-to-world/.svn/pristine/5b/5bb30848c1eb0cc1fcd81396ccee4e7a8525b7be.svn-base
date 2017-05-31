'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var dealerListController = require('dealer/controller/dealer-list-controller');
var dealerUpdateController = require('dealer/controller/dealer-update-controller');
var fileStyle = require('dealer/style/dealer.css!css');
var dealerProductController = require('dealer/controller/dealer-product-controller');
var dealerStatisticsController = require('dealer/controller/dealer-statistics-controller');
var dealerSettlementController = require('dealer/controller/dealer-settlement-controller');

angular
    .module('dealer', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function($stateProvider) {
        $stateProvider.state('console.dealer', {
            url: '/dealer',
            template: '<md-content ui-view layout="column"></md-content>'
        }).state('console.dealer.list', {
            url: '/list',
            templateUrl: '/admin/dealer/view/dealer.list.html',
            controller: dealerListController
        }).state('console.dealer.create', {
            url: '/create',
            templateUrl: '/admin/dealer/view/dealer.update.html',
            controller: dealerUpdateController
        }).state('console.dealer.update', {
            url: '/:id',
            templateUrl: '/admin/dealer/view/dealer.update.html',
            controller: dealerUpdateController
        }).state('console.dealer.product', {
            url: '/:id/product',
            templateUrl: '/admin/dealer/view/dealer.product.html',
            controller: dealerProductController
        }).state('console.dealer.statistics', {
            url: '/dealer/statistics',
            templateUrl: '/admin/dealer/view/dealer.statistics.html',
            controller: dealerStatisticsController
        }).state('console.dealer.settlement', {
            url: '/dealer/settlement',
            templateUrl: '/admin/dealer/view/dealer.settlement.html',
            controller: dealerSettlementController
        });
    })
    .directive("creditTrackingType", function() {
        return {
            require: 'ngModel',
            link: function(scope, elem, iAttrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function(context) {
                    var display;
                    switch (context) {
                        case "CHECKOUT":
                            display = "支付";
                            break;
                        case "INIT":
                            display = "创建";
                            break;
                        case "UPDATE":
                            display = "更新";
                            break;
                        case "RESET":
                            display = "回销";
                            break;
                        default:
                            display = "";
                    }
                    $(elem).html(display);
                    return display;
                });
            }
        };
    })
    .directive("translateStatus", function() {
        return {
            require: 'ngModel',
            link: function(scope, elem, iAttrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function(context) {
                    var display;
                    switch (context) {
                        case "ACTIVE":
                            display = "正常";
                            break;
                        case "AUDITING":
                            display = "审核中";
                            break;
                        case "INACTIVE":
                            display = "冻结";
                            break;
                        case "DELETED":
                            display = "已删除";
                            break;
                        default:
                            display = "";
                    }
                    $(elem).html(display);
                    return display;
                });
            }
        };
    })
    .directive('username', function() {
        return {
            require: "ngModel",
            link: function(scope, ele, attrs, ngModelController) {
                ngModelController.$parsers.push(function(value) {
                    if (!/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value)
                        && !/1[3578]\d{9}/.test(value)) {
                        ngModelController.$setValidity('username', false);
                    } else {
                        ngModelController.$setValidity('username', true);
                    }
                    return value;
                });
            }
        }
    })
    .directive('identification', function() {
        return {
            require: "ngModel",
            link: function(scope, ele, attrs, ngModelController) {
                ngModelController.$parsers.push(function(value) {
                    var original = value;
                    if (!value || !(/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/).test(value)) {
                        ngModelController.$setValidity('identification', false);
                    } else if (!(/1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|81|82|91/).test(value.substr(0, 2))) {
                        ngModelController.$setValidity('identification', false);
                    } else if (value.length === 18) {
                        var parity = ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
                        var sum = 0;
                        value = value.split("");
                        for (var i = 0; i < 17; i++) {
                            sum += value[i] * Math.pow(2, 17 - i);
                        }
                        ngModelController.$setValidity('identification', parity[sum % 11] === value[17]);
                    } else {
                        ngModelController.$setValidity('identification', true);
                    }
                    return original;
                });
            }
        }
    })
    .directive('invalid', function() {
        return {
            require: "ngModel",
            link: function(scope, ele, attrs, ngModelController) {
                ngModelController.$setValidity('invalid', true);
            }
        }
    })
    .directive('limit', function() {
        return {
            require: "ngModel",
            scope: true,
            link: function(scope, ele, attrs, ngModelController) {
                var limit = attrs.limit.split('-');
                var min = parseInt(limit[0]);
                var max = parseInt(limit[1]);
                ele.on('blur keyup change', function() {
                    var value = parseInt(scope.$eval(attrs.ngModel)),
                        newValue = value;
                    if (isNaN(value)) {
                        newValue = 0;
                    }
                    if (value < min) {
                        newValue = min;
                    }
                    if (value > max) {
                        newValue = max;
                    }

                    ngModelController.$setViewValue(newValue);
                    ngModelController.$render();
                });
            }
        }
    })
    .directive('dealerProductCategory', ['$timeout', function($timeout, $state) {
        return {
            scope: {
                category: '=',
                isCategorySelected: '&',
                isProductSelected: '&',
                checkProduct: '&',
                checkCategory: '&',
            },
            templateUrl: 'partials/dealer-product-category.tmpl.html',
            link: function(scope, ele, attrs, ngModelController) {
            }
        };
    }])
    .directive('dealerAllocatedProductCategory', ['$timeout', function($timeout, $state) {
        return {
            scope: {
                category: '=',
                isCategorySelected: '&',
                isProductSelected: '&',
                checkProduct: '&',
                checkCategory: '&',
                surrenderMarks: '=',
                bankAccountMarks: '=',
                rates: '='
            },
            templateUrl: 'partials/dealer-allocated-product-category.tmpl.html',
            link: function(scope, ele, attrs, ngModelController) {
            }
        };
    }])
    .run(function(Console) {
        Console.messages.add([
            {key: "分销商用户管理", value: "分销商用户管理"},
            {key: "分销商列表", value: "分销商列表"},
            {key: "业绩汇总统计", value: "业绩汇总统计"},
            {key: "结算明细查询", value: "结算明细查询"}
        ]);

        Console.menu.add({
            name: '分销商用户管理',
            state: 'console.dealer',
            icon: 'fa-folder',
            priority: 1,
            children: [
                {
                    name: "分销商列表",
                    permission: 'dealer.admin.GET',
                    state: "console.dealer.list",
                    icon: "fa-list-ul",
                    priority: 1
                },
                {
                    name: "业绩汇总统计",
                    permission: 'report.admin.GET',
                    state: "console.dealer.statistics",
                    icon: "fa-table",
                    priority: 2
                },
                {
                    name: "结算明细查询",
                    permission: 'report.admin.GET',
                    state: "console.dealer.settlement",
                    icon: "fa-table",
                    priority: 3
                }
            ]
        });
    })
;