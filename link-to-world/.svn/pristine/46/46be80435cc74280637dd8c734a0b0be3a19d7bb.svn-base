/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog, $mdToast, $location) {
    $scope.treeData = [];
    $scope.current = {};
    $scope.$watch('tree.currentNode', function (newObj, oldObj) {
        if ($scope.tree && angular.isObject($scope.tree.currentNode)) {
            $state.go("console.insurance.category.update", {id: $scope.tree.currentNode.category.id, node: $scope.tree.currentNode});
        }
    }, false);
    $scope.selectDefaultNode = function () {
        $scope.tree.currentNode = $scope.treeData[0];
        $scope.tree.selectNodeLabel($scope.tree.currentNode);
        $scope.current = $scope.tree.currentNode;
    };

    $scope.create = function (event) {
        var rScope = $scope;
        $mdDialog.show({
            controller: function ($scope, $http) {
                $scope.category = {};
                $scope.save = function () {
                    if (!rScope.check($scope.category))return false;
                    $http.post("/admin/ajax/insurance/category", $scope.category)
                        .then(function () {
                            rScope.toast("创建成功");
                            rScope.load(rScope.current);
                            setTimeout($mdDialog.cancel(), 500);
                        });
                };
                $scope.cancel = function () {
                    $mdDialog.cancel();
                };
            },
            templateUrl: "/admin/insurance/category/view/insurance-category.modal.create.html",
            parent: angular.element(".page"),
            targetEvent: event,
            clickOutsideToClose: true
        });
    };
    $scope.check = function (category) {
        if (!category.name) {
            $scope.toast("名称必须填");
            return false;
        }
        if (category.displayOrder == null) {
            $scope.toast("排序必须填");
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
        $http.get("/admin/ajax/insurance/category/tree")
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
    $scope.$on("deleteChild", function (e, d) {
        $scope.load(null);
    });
    $scope.$on("updated", function (e, d) {
        $scope.load(null);
    })
};