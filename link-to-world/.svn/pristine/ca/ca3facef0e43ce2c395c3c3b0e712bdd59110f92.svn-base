/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.data = {};
    $http.get("/admin/ajax/job-tree/" + $stateParams.id)
        .then(function (response) {
            $scope.data.jobTree = response.data;
            $scope.data.origin = angular.copy($scope.data.jobTree);
        });
    $scope.save = function () {
        if (!$scope.check($scope.data.jobTree))return;
        $http.put("/admin/ajax/job-tree/" + $scope.data.jobTree.id, $scope.data.jobTree)
            .then(function () {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function (jobTree) {
        if (!jobTree.name) {
            $scope.toast("名称不能为空");
            return false;
        }

        return true;
    };
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.cancel = function () {
        $scope.data.jobTree = angular.copy($scope.data.origin);
    };
};