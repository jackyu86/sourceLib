/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast, $element, $sce) {
    $scope.data = {};
    $scope.data.auditProduct = {};
    $scope.auditRequest = {};
    $http.get("/admin/ajax/product/audit/" + $stateParams.id)
        .then(function (response) {
            $scope.data.auditProduct = response.data;
        });
    $http.get("/admin/ajax/product/audit/" + $stateParams.id + "/differ")
        .then(function (response) {
            $scope.differ = response.data.differ;
        });
    $scope.differHtml = function (differ) {
        return $sce.trustAsHtml(differ);
    };
    $scope.submit = function (status) {
        $scope.auditRequest.status = status;
        var confirm = $mdDialog.confirm()
            .title('确认提交审核？')
            .ariaLabel('Update AuditProduct')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.put('/admin/ajax/product/audit/' + $stateParams.id, $scope.auditRequest).then(function () {
                var toast = $mdToast.simple()
                    .textContent('审核请求提交成功')
                    .position("bottom right")
                    .hideDelay(2000);
                $mdToast.show(toast);
            }).catch(function (response) {
                $scope.toast(response.data);
            });
        }, function () {
        });
    };
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};