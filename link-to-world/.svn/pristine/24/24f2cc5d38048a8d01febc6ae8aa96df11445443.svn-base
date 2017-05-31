/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.data = {claim: {}};
    $scope.save = function () {
        if (!$scope.check($scope.data.claim))return;
        $http.post("/admin/ajax/insurance/claim", $scope.data.claim)
            .then(function () {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function (claim) {
        if (!claim.name) {
            $scope.toast("名称不能为空");
            return false;
        }
        if (!claim.title) {
            $scope.toast("标题不能为空");
            return false;
        }
        if (!claim.content) {
            $scope.toast("内容不能为空");
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
        $scope.data.claim = angular.copy($scope.data.origin);
    };
};