'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdToast, Upload) {
    $scope.loading = false;
    $scope.role = {
        permissions: []
    };
    $scope.permissionGroups = [];

    $scope.original = angular.copy($scope.role);

    if ($state.is('console.role.update')) {
        $http.get('/admin/ajax/role/' + $stateParams.id).then(function (response) {
            $scope.role = response.data;

            if (!$scope.role.permissions) {
                $scope.role.permissions = [];
            }

            $scope.original = angular.copy($scope.role);
        });
    }

    $http.get('/admin/ajax/permission').then(function (response) {
        $scope.permissionGroups = response.data;
    });

    $scope.hasPermission = function (permission) {
        var i = $scope.role.permissions.length;
        while (i--) {
            if ($scope.role.permissions[i] === permission) {
                return true;
            }
        }
        return false;
    };

    $scope.toggle = function (permission) {
        var i = $scope.role.permissions.length;
        while (i--) {
            if ($scope.role.permissions[i] === permission) {
                $scope.role.permissions.splice(i, 1);
                return;
            }
        }

        $scope.role.permissions.push(permission);
    };

    $scope.isModified = function () {
        return !angular.equals($scope.role, $scope.original);
    };

    $scope.cancel = function () {
        $scope.role = angular.copy($scope.original);
    };

    $scope.save = function () {
        if ($state.is('console.role.update')) {
            $scope.loading = true;
            $http.put('/admin/ajax/role/' + $scope.role.id, $scope.role).then(function () {
                var toast = $mdToast.simple()
                    .textContent('角色已保存')
                    .position("top right")
                    .hideDelay(2000);

                $mdToast.show(toast);

                $scope.loading = false;
            });
        } else {
            $http.post("/admin/ajax/role", $scope.role).then(function (response) {
                var toast = $mdToast.simple()
                    .textContent('角色已创建')
                    .position("top right")
                    .hideDelay(2000);

                $mdToast.show(toast);
                $state.go('console.role.update', {id: response.data.id});
            });
        }
    };
};