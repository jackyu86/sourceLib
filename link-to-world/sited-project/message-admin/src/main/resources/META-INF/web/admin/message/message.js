'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var messages = require('/admin/ajax/i18n/message!json');

var messageListController = require('/admin/message/controller/message-list-controller');

angular.module('message', ['ngCookies', 'ui.router'])
    .config(function($stateProvider) {
        $stateProvider.state('console.message', {
            url: '/message',
            template: '<md-content ui-view layout="column"></md-content>'
        }).state('console.message.list', {
            url: "/",
            templateUrl: '/admin/message/view/message.list.html',
            controller: messageListController
        })
    })
    .run(function($rootScope, $http, Console) {
        Console.messages.add(messages);

        Console.menu.add({
            name: "message.message",
            permission: 'message.GET',
            icon: "fa-users",
            state: "console.message.list",
            priority: 8
        });
    });