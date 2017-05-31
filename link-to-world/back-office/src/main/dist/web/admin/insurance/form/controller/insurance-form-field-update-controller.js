/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.data = {};
    $scope.formGroup = $stateParams.group;
    $http.get("/admin/ajax/form-field/" + $stateParams.id)
        .then(function (response) {
            $scope.data.formField = response.data;
            $scope.data.origin = angular.copy($scope.data.formField);
        });
    $scope.save = function () {
        if (!$scope.check($scope.data.formField))return;
        $http.put("/admin/ajax/form-field/" + $scope.data.formField.id, $scope.data.formField)
            .then(function () {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function (formField) {
        if (!formField.displayName) {
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
        $scope.data.formField = angular.copy($scope.data.origin);
    };
    $scope.goList = function () {
        $state.go("console.insurance.form-field.list", {groupId: $scope.data.formField.groupId, group: $scope.formGroup});
    };
    $scope.displayAsList = [{displayName: "枚举型", value: "radio"}, {displayName: "下拉型", value: "selection"}];
};