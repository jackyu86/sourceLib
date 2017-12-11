/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.data = {};
    $scope.data.serial = {};
    $scope.save = function () {
        if (!$scope.check($scope.data.serial))return;
        $http.post("/admin/ajax/product/serial", $scope.data.serial)
            .then(function () {
                $scope.toast("保存成功");
                $state.go("console.product.serial.list");
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
};