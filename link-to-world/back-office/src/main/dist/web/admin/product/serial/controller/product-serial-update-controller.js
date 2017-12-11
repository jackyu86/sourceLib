/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.data = {};
    $http.get("/admin/ajax/product/serial/" + $stateParams.id)
        .then(function (response) {
            $scope.data.serial = response.data;
            $scope.data.origin = angular.copy($scope.data.serial);
        });
    $scope.save = function () {
        if (!$scope.check($scope.data.serial))return;
        $http.put("/admin/ajax/product/serial/" + $scope.data.serial.id, $scope.data.serial)
            .then(function () {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function (serial) {
        if (!serial.name) {
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
        $scope.data.serial = angular.copy($scope.data.origin);
    };
};