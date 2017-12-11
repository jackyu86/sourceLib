'use strict';

var angular = require('angular');
module.exports = function($scope, $rootScope, $http, $stateParams, $state, $timeout, $mdDialog) {
    $scope.customers = {};
    $scope.selected = [];

    $scope.query = {
        page: 1,
        limit: 20,
        username: null,
        fullName: null,
        identification: null,
        role: null
    };

    $scope.table = {
        searching: false
    };

    $scope.load = function() {
        $scope.promise = $http.put('/admin/ajax/customer/query', $scope.query);
        $scope.promise.then(function(response) {
            $scope.customers = response.data;
        });
    };

    $scope.cancelSearching = function() {
        $scope.table.searching = false;
        $scope.query = {
            page: 1,
            limit: 20
        };
        $scope.load();
    }

    $scope.options = {
        autoSelect: true,
        boundaryLinks: false,
        largeEditDialog: false,
        pageSelector: false,
        rowSelection: true,
        index: function() {
            if ($scope.customers.limit) {
                return $scope.customers.page;
            }
            return 0;
        },
        total: function() {
            if ($scope.customers.limit) {
                return $scope.customers.total % $scope.customers.limit === 0 ? parseInt($scope.customers.total / $scope.customers.limit) : parseInt($scope.customers.total / $scope.customers.limit) + 1;
            }
            return 0;
        }
    };

    $scope.load();
};