/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.jobNode = $stateParams.node;
    $scope.deletable = function () {
        return !($scope.jobNode && $scope.jobNode.children && $scope.jobNode.children.length > 0);
    };

    $scope.save = function () {
        if (!$scope.check($scope.jobNode.job))return false;
        $http.put("/admin/ajax/job/" + $scope.jobNode.job.id, $scope.jobNode.job)
            .then(function () {
                $scope.toast("更新成功");
                $scope.$emit("updateChild", null);
            }).catch(function (response) {
            $scope.toast(response.data.invalidFields[0].message);
        });
    };

    $scope.createChild = function (event) {
        var parentId = $scope.jobNode.job.id;
        var jobTreeId = $scope.jobNode.job.jobTreeId;
        var rrScope = $scope;
        $mdDialog.show({
            controller: function ($scope, $http) {
                $scope.job = {};
                $scope.job.parentId = parentId;
                $scope.job.jobTreeId = jobTreeId;
                $scope.save = function () {
                    if (!rrScope.check($scope.job))return false;
                    $http.post("/admin/ajax/job", $scope.job)
                        .then(function () {
                            rrScope.toast("创建成功");
                            rrScope.$emit("createChild", rrScope.jobNode);
                            setTimeout($mdDialog.cancel(), 500);
                        }).catch(function (response) {
                        if (response.status == 400) {
                            rrScope.toast(response.data.invalidFields[0].message);
                        }
                    });
                };
                $scope.cancel = function () {
                    $mdDialog.cancel();
                };
            },
            templateUrl: "/admin/insurance/job/view/insurance-job.modal.create.html",
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true
        });
    };

    $scope.check = function (job) {
        if (!job.displayName) {
            $scope.toast("名称必须填");
            return false;
        }
        if (!job.code) {
            $scope.toast("代码必须填");
            return false;
        }
        if (!job.riskLevel) {
            $scope.toast("风险等级必须填");
            return false;
        }
        return true;
    };
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right top")
            .hideDelay(2000);
        $mdToast.show(toast);
    };

    $scope.deleteJob = function () {
        var confirm = $mdDialog.confirm()
            .title('确认删除职业' + $scope.jobNode.job.displayName + "?")
            .ariaLabel('Delete Job')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete("/admin/ajax/job/" + $scope.jobNode.job.id)
                .then(function () {
                    $scope.toast("删除成功");
                    $scope.$emit("deleteChild", null);
                });
        }, function () {

        });
    };
};