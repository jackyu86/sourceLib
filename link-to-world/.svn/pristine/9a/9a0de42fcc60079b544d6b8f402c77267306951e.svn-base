/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.data = {};
    $scope.data.group = {};
    $http.get("/admin/ajax/insurance/liability/group/" + $stateParams.id)
        .then(function (response) {
            $scope.data.group = response.data;
        });
    $scope.save = function () {
        if (!$scope.check($scope.data.group))return;
        $http.put("/admin/ajax/insurance/liability/group/" + $stateParams.id, $scope.data.group)
            .then(function () {
                $scope.toast("保存成功");
                $state.go("console.insurance.liability-group.list");
            });
    };
    $scope.check = function (group) {
        if (!group.name) {
            $scope.toast("名称不能为空");
            return false;
        }
        if (!group.priority) {
            $scope.toast("优先级不能为空");
            return false;
        }
        if (isNaN(group.priority)) {
            $scope.toast("优先级不是数字");
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
};