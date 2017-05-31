'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var customerListController = require('customer/controller/customer-list-controller');

angular.module('customer', ['ngCookies', 'ui.router'])
    .config(function($stateProvider) {
        $stateProvider.state('console.customer', {
            url: '/customer',
            template: '<md-content ui-view layout="column"></md-content>'
        }).state('console.customer.list', {
            url: "/",
            templateUrl: '/admin/customer/view/customer.list.html',
            controller: customerListController
        });
    })
    .run(function($rootScope, $http, Console) {
        Console.messages.add([
            {key: "个人用户管理", value: "个人用户管理"},
        ]);

        Console.menu.add({
            name: "个人用户管理",
            state: "console.customer.list",
            icon: "fa-users",
            permission: "customer.GET",
            priority: 2
        });
    });