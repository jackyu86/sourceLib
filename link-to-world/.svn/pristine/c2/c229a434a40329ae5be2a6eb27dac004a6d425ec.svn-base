/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.data = {};
    $http.get("/admin/ajax/form-group/" + $stateParams.id)
        .then(function(response) {
            $scope.data.formGroup = response.data;
            $scope.data.origin = angular.copy($scope.data.formGroup);
        });
    $scope.save = function() {
        if (!$scope.check($scope.data.formGroup))return;
        $http.put("/admin/ajax/form-group/" + $scope.data.formGroup.id, $scope.data.formGroup)
            .then(function() {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function(formGroup) {
        if (!formGroup.displayName) {
            $scope.toast("名称不能为空");
            return false;
        }
        return true;
    };

    $scope.toast = function(message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.cancel = function() {
        $scope.data.formGroup = angular.copy($scope.data.origin);
    };
};