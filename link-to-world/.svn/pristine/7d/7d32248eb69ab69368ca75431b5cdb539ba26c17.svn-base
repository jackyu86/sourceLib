'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var messages = require('/admin/ajax/i18n/order!json');
var orderListController = require('order/controller/order-list-controller');
var orderOfflineListController = require('order/controller/order-offline-list-controller');
var orderDetailController = require('order/controller/order-detail-controller');
var orderStyle = require('order/style/order.css!css');

angular
    .module('order', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function ($stateProvider) {
        $stateProvider.state('console.order', {
            url: '/order',
            template: '<md-content ui-view layout="column"></md-content>'
        }).state('console.order.list', {
            url: '/order/list',
            templateUrl: '/admin/order/view/order.list.html',
            controller: orderListController
        }).state('console.order.offline', {
            url: '/order/list/offline',
            templateUrl: '/admin/order/view/order.offline.list.html',
            controller: orderOfflineListController
        }).state('console.order.detail', {
            url: "/:orderId",
            templateUrl: '/admin/order/view/order.detail.html',
            controller: orderDetailController
        });
    })
    .directive("percentFormat", function () {
        return {
            require: 'ngModel',
            link: function (scope, elem, iAttrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function (context) {
                    var display = context / 100 + "%";
                    $(elem).text(display);
                    return display;
                });
            }
        };
    }).directive("orderStatus", function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, iAttrs, ngModelCtrl) {
            ngModelCtrl.$formatters.push(function (context) {
                var display;
                switch (context) {
                    case "DRAFT":
                        display = "拟定";
                        break;
                    case "PAYMENT_PENDING":
                        display = "待支付";
                        break;
                    case "PAYMENT_FAILED":
                        display = "支付失败";
                        break;
                    case "PAYMENT_COMPLETED":
                        display = "支付成功";
                        break;
                    case "AUDITING":
                        display = "核保";
                        break;
                    case "VENDOR_INSURED":
                        display = "已承保";
                        break;
                    case "VENDOR_REJECT":
                        display = "拒保";
                        break;
                    case "DOCUMENTED":
                        display = "已出单";
                        break;
                    case "SURRENDERING":
                        display = "请求退保";
                        break;
                    case "SURRENDERED":
                        display = "已退保";
                        break;
                    case "REFUND":
                        display = "已退款";
                        break;
                    case "SURRENDER_FAILED":
                        display = "退款失败";
                        break;
                    default:
                        display = "";
                }
                $(elem).text(display);
                return display;
            });
        }
    };
})
    .directive('insuredName', function () {
        return {
            require: "ngModel",
            link: function (scope, ele, attrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function (orderItems) {
                    var names = "";
                    orderItems.forEach(function (orderItem) {
                        if (names) {
                            names += ",";
                        }
                        names += orderItem.name;
                    });
                    $(ele).text("[" + names + "]");
                    return "[" + names + "]";
                });
            }
        }
    })
    .directive('policyCode', function () {
        return {
            require: "ngModel",
            link: function (scope, ele, attrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function (orderItems) {
                    var names = "";
                    if (orderItems) {
                        orderItems.forEach(function (orderItem) {
                            console.log(orderItem);
                            if (names) {
                                names += ",";
                            }
                            names += orderItem.policyCode == null ? "" : orderItem.policyCode;
                        });
                        if (names) {
                            $(ele).text("[" + names + "]");
                            return "[" + names + "]";
                        }
                    }
                });
            }
        }
    })
    .directive('deadTime', function () {
        return {
            require: "ngModel",
            link: function (scope, ele, attrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function (order) {
                    var date = new Date(order.planStartTime);
                    switch (order.periodUnit) {
                        case 'DAY':
                            date.setDate(date.getDate() + order.periodValue);
                            break;
                        case 'MONTH':
                            date.setMonth(date.getMonth() + order.periodValue);
                            break;
                        case 'YEAR':
                            date.setFullYear(date.getFullYear() + order.periodValue);
                            break;
                        case 'AGE':
                            $(ele).text(order.periodValue + "岁");
                            return order.periodValue + "岁";
                        case 'AGE_DAY':
                            $(ele).text("年龄到" + order.periodValue + "天");
                            return "年龄到" + order.periodValue + "天";
                        case'LIFE':
                            $(ele).text("终身");
                            return "终身";
                    }
                    $(ele).text(date.getFullYear() + "" + (date.getMonth() + 1) + "-" + date.getDate() + " 00:00:00");
                    return date.getFullYear() + "" + (date.getMonth() + 1) + "-" + date.getDate() + " 00:00:00";
                });
            }
        }
    })
    .directive('dynamicForm', function () {
        return {
            require: "ngModel",
            link: function (scope, ele, attrs, ngModelCtrl) {
                ngModelCtrl.$formatters.push(function (jsonData) {
                    var data = JSON.parse(jsonData);

                    function generateNode() {

                    }

                    var names = "";
                    orderItems.forEach(function (orderItem) {
                        if (names) {
                            names += ",";
                        }
                        names += orderItem.name;
                    });
                    $(ele).text("[" + names + "]");
                    return "[" + names + "]";
                });
            }
        }
    })
    .run(function (Console) {
        Console.messages.add(messages);
        Console.messages.add([
            {key: "订单管理", value: "订单管理"},
            {key: "线下支付管理", value: "线下支付管理"}
        ]);

        Console.menu.add({
            permission: 'order.admin.GET',
            name: '订单管理',
            state: 'console.order.list',
            icon: 'fa-folder',
            priority: 3
        });
        Console.menu.add({
            permission: 'order.admin.GET',
            name: '线下支付管理',
            state: 'console.order.offline',
            icon: 'fa-folder',
            priority: 4
        });
    });