'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdToast, Upload) {
    $scope.processing = false;
    $scope.user = {
        roles: []
    };
    $scope.original = angular.copy($scope.user);
    $scope.roles = [];


    $http.put('/admin/ajax/role/find', {page: 1, limit: 1000}).then(function (response) {
        $scope.roles = response.data.items;
    });

    if ($state.is('console.user.update')) {
        $http.get('/admin/ajax/user/' + $stateParams.id).then(function (response) {
            $scope.user = response.data;

            if (!$scope.user.roles) {
                $scope.user.roles = [];
            }

            $scope.original = angular.copy($scope.user);
        });
    }

    $scope.isUserChanged = function () {
        return !angular.equals($scope.user, $scope.original);
    };

    $scope.cancel = function () {
        $scope.user = angular.copy($scope.original);
    };

    $scope.save = function () {
        if ($state.is('console.user.update')) {
            $scope.processing = true;
            $http.put('/admin/ajax/user/' + $scope.user.id, $scope.user).then(function () {
                var toast = $mdToast.simple()
                    .textContent('用户已保存')
                    .position("top right")
                    .hideDelay(2000);

                $mdToast.show(toast);

                $scope.processing = false;
            });
        } else {
            $http.post("/admin/ajax/user", $scope.user).then(function (response) {
                var toast = $mdToast.simple()
                    .textContent('用户已创建')
                    .position("top right")
                    .hideDelay(2000);

                $mdToast.show(toast);
                $state.go('console.user.update', {id: response.data.id});
            });
        }
        if ($scope.user.password !== undefined) {
            $http.post("/admin/ajax/user/" + $scope.user.id + "/password", {"password": $scope.user.password})
        }
    };

    $scope.upload = function (file) {
        Upload.upload({
            url: '/ajax/file/upload',
            data: {file: file}
        }).then(function (resp) {
            $scope.user.imageURL = resp.data.path;
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };
};