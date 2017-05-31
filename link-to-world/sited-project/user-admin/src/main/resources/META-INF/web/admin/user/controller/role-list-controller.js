'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $timeout, $mdDialog) {
    $scope.roles = {};
    $scope.selected = [];

    $scope.query = {
        page: 1,
        limit: 20,
        name: ''
    };

    $scope.load = function () {
        $scope.promise = $http.put('/admin/ajax/role/find', $scope.query);
        $scope.promise.then(function (response) {
            $scope.roles = response.data;
        });
    };

    $scope.delete = function (role) {
        var confirm = $mdDialog.confirm()
            .title('确认删除角色' + role.name)
            .textContent(role.name)
            .ariaLabel('Delete Role')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/role/' + role.id).then(function () {
                for (var i = 0; i < $scope.roles.items.length; i++) {
                    if (role.id === $scope.roles.items[i].id) {
                        $scope.roles.items.splice(i, 1);
                        break;
                    }
                }
            });
        });
    };

    $scope.update = function (role) {
        $state.go('console.role.update', {id: role.id});
    };

    $scope.options = {
        autoSelect: true,
        boundaryLinks: false,
        largeEditDialog: false,
        pageSelector: false,
        rowSelection: true,
        index: function () {
            if ($scope.roles.limit) {
                return $scope.roles.page;
            }
            return 0;
        },
        total: function () {
            if ($scope.roles.limit) {
                return $scope.roles.total % $scope.roles.limit === 0 ? parseInt($scope.roles.total / $scope.roles.limit) : parseInt($scope.roles.total / $scope.roles.limit) + 1;
            }
            return 0;
        }
    };

    $scope.load();
};