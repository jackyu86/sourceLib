/**
 * @author miller
 */
'use strict';

var angular = require('angular');
var uiRouter = require('uiRouter');
var ngCookies = require('ngCookies');
var enumValuesController = require('enum/controller/enum-values-controller');

angular
    .module('enum', ['ngCookies', 'ui.router', 'as.sortable'])
    .config(function($stateProvider) {
        $stateProvider.state('console.enum', {
            url: "/enum",
            template: "<md-content ui-view layout='column'></md-content>"
        }).state("console.enum.values", {
            url: "/:name",
            templateUrl: "/admin/enum/view/enum.values.html",
            controller: enumValuesController
        });
    })
    .run(function(Console, $http) {
        Console.messages.add([
            {key: "产品枚举数据", value: "产品枚举数据"}
        ]);

        $http.get('/admin/ajax/enum-type').then(function(response) {
            var children = [];

            for (var i = 0; i < response.data.length; i++) {
                Console.messages.add([
                    {key: response.data[i].name, value: response.data[i].displayName}
                ]);
                children.push({
                    name: response.data[i].name,
                    state: "console.enum.values({name: '" + response.data[i].name + "'})",
                    icon: "fa-phone-square"
                })
            }

            Console.menu.add({
                name: '产品枚举数据',
                permission: 'insurance-enum.admin.GET',
                icon: 'fa-truck',
                priority: 7,
                children: children
            });
        });
    });