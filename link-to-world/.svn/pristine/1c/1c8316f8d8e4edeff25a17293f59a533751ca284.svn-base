'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var ngSortable = require('ngSortable');

var ticketListController = require('ticket/controller/ticket-list-controller');

angular
    .module('ticket', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function ($stateProvider) {
        $stateProvider.state('console.ticket', {
            url: '/list',
            templateUrl: '/admin/ticket/view/ticket.list.html',
            controller: ticketListController
        });
    })
    .run(function (Console) {
        Console.messages.add([
            {key: "客户联系方式列表", value: "客户联系方式列表"}
        ]);

        Console.menu.add({
            permission: 'ticket.admin.GET',
            name: '客户联系方式列表',
            state: 'console.ticket',
            icon: 'fa-folder',
            priority: 10
        });
    })
;