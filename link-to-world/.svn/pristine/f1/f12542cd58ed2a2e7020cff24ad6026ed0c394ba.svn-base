/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog, $mdToast) {
    $scope.categoryNode = $stateParams.node;
    $scope.deletable = function () {
        return !($scope.categoryNode && $scope.categoryNode.children && $scope.categoryNode.children.length > 0);
    };

    $scope.save = function () {
        if (!$scope.check($scope.categoryNode.category))return false;
        $http.put("/admin/ajax/insurance/category/" + $scope.categoryNode.category.id, $scope.categoryNode.category)
            .then(function () {
                $scope.toast("更新成功");
                $scope.$emit("updated", null);
            })
    };

    $scope.createChild = function (event) {
        var parentId = $scope.categoryNode.category.id;
        var rrScope = $scope;
        $mdDialog.show({
            controller: function ($scope, $http) {
                $scope.category = {};
                $scope.category.parentId = parentId;
                $scope.save = function () {
                    if (!rrScope.check($scope.category))return false;
                    $http.post("/admin/ajax/insurance/category", $scope.category)
                        .then(function () {
                            rrScope.toast("创建成功");
                            rrScope.$emit("createChild", rrScope.categoryNode);
                            setTimeout($mdDialog.cancel(), 500);
                        });
                };
                $scope.cancel = function () {
                    $mdDialog.cancel();
                };
            },
            templateUrl: "/admin/insurance/category/view/insurance-category.modal.create.html",
            parent: angular.element(document.body),
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

    $scope.deleteCategory = function (event) {
        var confirm = $mdDialog.confirm()
            .title('确认删除分类' + $scope.categoryNode.category.name + "?")
            .ariaLabel('Delete InsuranceCategory')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete("/admin/ajax/insurance/category/" + $scope.categoryNode.category.id)
                .then(function () {
                    $scope.toast("删除成功");
                    $scope.$emit("deleteChild", null);
                });
        }, function () {

        });
    };
};