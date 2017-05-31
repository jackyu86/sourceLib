'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog) {
    $scope.ticketTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false
    };

    $scope.reloadTicket = function () {
        $http.put('/admin/ajax/ticket', $scope.ticketTable.query).then(function (response) {
            $scope.ticketTable.data = response.data;
            $scope.ticketTable.selected = [];
        });
    };

    $scope.cancelSearching = function () {
        $scope.ticketTable.searching = false;
        $scope.ticketTable.query.name = null;

        $scope.reloadTicket();
    };

    $scope.reloadTicket();
};