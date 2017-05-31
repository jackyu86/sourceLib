'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var messages = require('/admin/ajax/i18n/user!json');
var userListController = require('user/controller/user-list-controller');
var userUpdateController = require('user/controller/user-update-controller');
var roleListController = require('user/controller/role-list-controller');
var roleUpdateController = require('user/controller/role-update-controller');

angular.module('user', ['ngCookies', 'ui.router'])
    .config(function($stateProvider) {
        $stateProvider.state('console.user', {
            url: '/user',
            template: '<md-content ui-view layout="column"></md-content>'
        }).state('console.user.list', {
            url: "/",
            templateUrl: '/admin/user/view/user.list.html',
            controller: userListController
        }).state('console.user.create', {
            url: "/create",
            templateUrl: '/admin/user/view/user.update.html',
            controller: userUpdateController
        }).state('console.user.update', {
            url: "/:id",
            templateUrl: '/admin/user/view/user.update.html',
            controller: userUpdateController
        }).state('console.role', {
            url: '/role',
            template: '<md-content ui-view></md-content>'
        }).state('console.role.list', {
            url: "/",
            templateUrl: '/admin/user/view/role.list.html',
            controller: roleListController
        }).state('console.role.create', {
            url: "/create",
            templateUrl: '/admin/user/view/role.update.html',
            controller: roleUpdateController
        }).state('console.role.update', {
            url: "/:id",
            templateUrl: '/admin/user/view/role.update.html',
            controller: roleUpdateController
        });
    })
    .run(function($rootScope, $http, Console) {
        Console.messages.add(messages);
        Console.messages.add([
            {key: "账户管理", value: "账户管理"},
            {key: "用户管理", value: "用户管理"},
            {key: "角色管理", value: "角色管理"}
        ]);

        if (Console.user.menuEnabled) {
            Console.menu.add({
                name: "账户管理",
                state: "console.user.list",
                icon: "fa-users",
                permission: "user.GET",
                children: [
                    {
                        name: "用户管理",
                        state: "console.user.list",
                        icon: "fa-user",
                        priority: 10
                    },
                    {
                        name: "角色管理",
                        state: "console.role.list",
                        icon: "fa-user",
                        priority: 10
                    }
                ],
                priority: 5
            });
        }
        $http.get('/admin/ajax/user/self').then(function(response) {
            $rootScope.user = response.data;

            $rootScope.user.hasRole = function(role) {
                var i = $rootScope.user.roles.length;
                while (i) {
                    if ($rootScope.user.roles[i - 1] === role) {
                        return true;
                    }
                    i--;
                }
                return false;
            };

            $rootScope.user.hasPermission = function(permission) {
                if (!permission || $rootScope.user.hasRole('admin')) {
                    return true;
                }

                if (!$rootScope.user.permissions) {
                    return false;
                }

                var i = $rootScope.user.permissions.length;
                while (i) {
                    if ($rootScope.user.permissions[i - 1] === permission) {
                        return true;
                    }
                    i--;
                }
                return false;
            };

            for (var i = 0; i < Console.menu.items.length; i++) {
                var item = Console.menu.items[i];

                if (!$rootScope.user.hasPermission(item.permission)) {
                    Console.menu.items.splice(i, 1);
                } else if (item.children) {
                    for (var j = 0; j < item.children.length; j++) {
                        if (!$rootScope.user.hasPermission(item.children[j].permission)) {
                            item.children.splice(j, 1);
                        }
                    }
                }
            }
        });
    });