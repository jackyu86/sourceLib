'use strict';

var angular = require('angular');
var consoleController = require('console/controller/console-controller');
var module = angular.module('console', ['ngCookies', 'ui.router']);
var messages = require('/admin/ajax/i18n/console!json');


module.config(function($stateProvider) {
    $stateProvider.state('console.dashboard', {
        url: '/',
        controller: consoleController,
        templateUrl: '/admin/console/view/dashboard.home.html'
    });
});

module.run(function(Console) {
    Console.messages.add(messages);
});
