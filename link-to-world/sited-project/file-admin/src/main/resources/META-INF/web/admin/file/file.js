'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');

var fileController = require('file/controller/file-controller');
var fileListController = require('file/controller/file-list-controller');
var fileUpdateController = require('file/controller/file-update-controller');
var fileDashboardController = require('file/controller/file-dashboard-controller');
var fileStyle = require('file/style/file.css!css');
var messages = require('/admin/ajax/i18n/file!json');

angular
    .module('file', ['ngCookies', 'ui.router'])
    .config(function($stateProvider) {
        $stateProvider.state('console.file', {
            url: '/file/{path:.*}',
            templateUrl: '/admin/file/view/file.html',
            controller: fileController,
            controllerAs: 'vm'
        }).state('console.directory.file', {
            url: "/file",
            templateUrl: '/admin/file/view/file.list.html',
            controller: fileListController,
            controllerAs: 'vm'
        }).state('console.directory.file.create', {
            url: "/create",
            templateUrl: '/admin/file/view/file.update.html',
            controller: fileUpdateController,
            controllerAs: 'vm'
        }).state('console.directory.file.update', {
            url: "/:id",
            templateUrl: '/admin/file/view/file.update.html',
            controller: fileUpdateController,
            controllerAs: 'vm'
        });
    })
    .run(function(Console) {
        Console.messages.add(messages);

        Console.menu.add({
            permission: 'file.GET',
            name: 'file.file',
            state: 'console.file',
            icon: 'fa-folder',
            priority: 9
        });
    });