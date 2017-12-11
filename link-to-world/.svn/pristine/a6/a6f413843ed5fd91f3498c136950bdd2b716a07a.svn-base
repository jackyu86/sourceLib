/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    if (!$stateParams.treeId) {
        $state.go("console.insurance.job-tree.list");
    }
    $scope.jobTree = $stateParams.tree;
    if (!$scope.jobTree) {
        //todo get jobTree
    }
    $scope.treeData = [];
    $scope.current = {};
    $scope.$watch('tree.currentNode', function (newObj, oldObj) {
        if ($scope.tree && angular.isObject($scope.tree.currentNode)) {
            $state.go("console.insurance.job.update", {id: $scope.tree.currentNode.job.id, node: $scope.tree.currentNode});
        }
    }, false);
    $scope.selectDefaultNode = function () {
        $scope.tree.currentNode = $scope.treeData[0];
        $scope.tree.selectNodeLabel($scope.tree.currentNode);
        $scope.current = $scope.tree.currentNode;
    };

    $scope.create = function (event) {
        var rScope = $scope;
        var treeId = $stateParams.treeId;
        $mdDialog.show({
            controller: function ($scope, $http) {
                $scope.job = {};
                $scope.job.jobTreeId = treeId;
                $scope.save = function () {
                    if (!rScope.check($scope.job))return false;
                    $http.post("/admin/ajax/job", $scope.job)
                        .then(function () {
                            rScope.toast("创建成功");
                            rScope.load(rScope.current);
                            setTimeout($mdDialog.cancel(), 500);
                        }).catch(function (response) {
                        if (response.status == 400) {
                            rScope.toast(response.data.invalidFields[0].message);
                        }
                    });
                };
                $scope.cancel = function () {
                    $mdDialog.cancel();
                };
            },
            templateUrl: "/admin/insurance/job/view/insurance-job.modal.create.html",
            parent: angular.element(".page"),
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
    $scope.load = function (current) {
        $http.get("/admin/ajax/job/tree/" + $stateParams.treeId)
            .then(function (response) {
                $scope.treeData = response.data;
                if ($scope.treeData && $scope.treeData.length) {
                    if (current == null) {
                        $scope.selectDefaultNode();
                    } else {
                        $scope.tree.selectNodeLabel(current);
                    }
                }
            });
    };
    $scope.load(null);
    $scope.$on("createChild", function (e, d) {
        $scope.load(null);
    });
    $scope.$on("updateChild", function (e, d) {
        $scope.load(null);
    });
    $scope.$on("deleteChild", function (e, d) {
        $scope.load(null);
    });
};