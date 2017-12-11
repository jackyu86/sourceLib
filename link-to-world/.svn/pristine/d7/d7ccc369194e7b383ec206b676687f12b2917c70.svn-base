/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    if (!$stateParams.id) {
        $state.go("console.insurance.liability-group.list");
    }
    $scope.data = {};
    $scope.data.liability = {};
    $scope.data.liability.liveBenefit = {};
    $scope.data.liability.riskProtection = {};
    $http.get("/admin/ajax/insurance/liability/" + $stateParams.id)
        .then(function (response) {
            $scope.data.liability = response.data;
            if (!$scope.data.liability.liveBenefit || $scope.data.liability.liveBenefit == null) {
                $scope.data.liability.liveBenefit = {};
            } else {
                if ($scope.data.liability.liveBenefit.startTime != null) {
                    $scope.data.liability.liveBenefit.startTime = new Date($scope.data.liability.liveBenefit.startTime);
                }
            }
            if (!$scope.data.liability.riskProtection || $scope.data.liability.riskProtection == null) {
                $scope.data.liability.riskProtection = {};
            }

            $http.get("/admin/ajax/insurance/liability/group/" + response.data.groupId)
                .then(function (response) {
                    $scope.group = response.data;
                });
        });

    $scope.goBack = function () {
        $state.go("console.insurance.liability.list", {groupId: $scope.group.id});
    };
    $scope.check = function (liability) {
        if (!liability.name) {
            $scope.toast("名称必填");
            return false;
        }
        if (!liability.priority) {
            $scope.toast("优先级必填");
            return false;
        }
        if (isNaN(liability.priority)) {
            $scope.toast("优先级必须为数字");
            return false;
        }
        if (!liability.type) {
            $scope.toast("类型必填");
            return false;
        }
        if (liability.type == 'LIVE_BENEFIT') {
            var benefit = liability.liveBenefit;
            if (!benefit.receiveType) {
                $scope.toast("领取方式必填");
                return false;
            }
            if (benefit.receiveType == 'INSTALLMENT') {
                if (!benefit.receiveFrequencyType) {
                    $scope.toast("领取次数方式必填");
                    return false;
                }
                if (benefit.receiveFrequencyType == 'ABSOLUTE' || benefit.receiveFrequencyType == 'LIVE_TIMES') {
                    if (!$scope.receiveTimes) {
                        $scope.toast("领取次数必填");
                        return false;
                    }
                }
                if (!benefit.proportionEnabled) {
                    $scope.toast("请选择是否变频");
                    return false;
                } else if (!benefit.proportion) {
                    $scope.toast("变频比例必填");
                }
            }
            if (!benefit.startTime) {
                $scope.toast("开始时间必填");
                return false;
            }
        } else if (liability.type == 'RISK_PROTECTION') {
            if (!liablity.riskProtection.type) {
                $scope.toast("风险保障类型必填");
                return false;
            }
        }
        return true;
    };
    $scope.save = function () {
        if (!$scope.check($scope.data.liability)) {
            return false;
        }
        $http.put("/admin/ajax/insurance/liability/update/" + $scope.data.liability.id, $scope.data.liability)
            .then(function (response) {
                $scope.toast("保存成功");
                $state.go("console.insurance.liability.list", {groupId: $scope.group.id});
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