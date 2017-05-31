'use strict';

var angular = require('angular');
module.exports = function($scope, $rootScope, $http, $stateParams, $state, $timeout, $mdDialog) {
    $scope.users = [];
    $scope.selected = [];

    $scope.query = {
        page: 1,
        limit: 20,
        username: null,
        role: null
    };

    $scope.table = {
        searching: false
    };

    $scope.roles = [];

    $http.put('/admin/ajax/role/find', {page: 1, limit: 100}).then(function(response) {
        $scope.roles = response.data.items;
    });

    $scope.load = function() {
        $scope.promise = $http.put('/admin/ajax/user/find', $scope.query);
        $scope.promise.then(function(response) {
            $scope.users = response.data;
        });
    };

    $scope.cancelSearching = function() {
        $scope.table.searching = false;
        $scope.query = {
            page: 1,
            limit: 20,
            username: null,
            role: null
        };
        $scope.load();
    };

    $scope.deleteUser = function(user) {
        var confirm = $mdDialog.confirm()
            .title('确认删除用户' + user.username)
            .textContent('')
            .ariaLabel('Delete User')
            .theme('white')
            .targetEvent(event)
            .ok('确定')
            .cancel('取消');

        $mdDialog.show(confirm).then(function() {
            $http.delete('/admin/ajax/user/' + user.id).then(function() {
                for (var i = 0; i < $scope.users.items.length; i++) {
                    if (user.id === $scope.users.items[i].id) {
                        $scope.users.items.splice(i, 1);
                    }
                }
            });
        });
    };

    $scope.updateUser = function(user) {
        $state.go('console.user.update', {id: user.id});
    };

    $scope.options = {
        autoSelect: true,
        boundaryLinks: false,
        largeEditDialog: false,
        pageSelector: false,
        rowSelection: true,
        index: function() {
            if ($scope.users.limit) {
                return $scope.users.page;
            }
            return 0;
        },
        total: function() {
            if ($scope.users.limit) {
                return $scope.users.total % $scope.users.limit === 0 ? parseInt($scope.users.total / $scope.users.limit) : parseInt($scope.users.total / $scope.users.limit) + 1;
            }
            return 0;
        }
    };

    $scope.load();
};