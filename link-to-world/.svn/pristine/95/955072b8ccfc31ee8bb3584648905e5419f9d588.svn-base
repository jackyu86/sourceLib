/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.data = {};
    $scope.save = function () {
        if (!$scope.check($scope.data.jobTree))return;
        $http.post("/admin/ajax/job-tree", $scope.data.jobTree)
            .then(function () {
                $scope.toast("保存成功");
                $state.go("console.insurance.job-tree.list");
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