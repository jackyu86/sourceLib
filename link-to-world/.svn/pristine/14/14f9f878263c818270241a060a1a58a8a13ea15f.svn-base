'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state) {
    $scope.file = {
        form: {},
        original: {},
        isChanged: function () {
            return !angular.equals($scope.file.form, $scope.file.original);
        }
    };

    $http.get('/admin/ajax/file/' + $stateParams.id).then(function (response) {
        $scope.file.form = response.data;
        $scope.file.original = angular.copy($scope.file.form);
    });

    $scope.saveFile = function () {
        $http.put('/admin/ajax/file/' + $scope.file.form.id, $scope.file.form).then(function () {
            $state.go('console.file.list');
        });
    };

    $scope.cancel = function () {
        $scope.file.form = angular.copy($scope.file.original);
    };
};
