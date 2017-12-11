/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.productTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            order: '-created_time',
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            for (var i = 0; i < $scope.productTable.data.items.length; i++) {
                if (id === $scope.productTable.data.items[i].id) {
                    $scope.productTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (product) {
            $state.go("console.product.update", {id: product.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.productTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadProducts = function () {
        $http.put("/admin/ajax/product/find", $scope.productTable.query)
            .then(function (response) {
                $scope.productTable.data = response.data;
                $scope.productTable.selected = [];
            });
    };
    $scope.cancelSearching = function () {
        $scope.productTable.searching = false;
        $scope.productTable.query.name = null;
        $scope.reloadProducts();
    };
    $scope.reloadProducts();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.active = function (product) {
        var confirm = $mdDialog.confirm()
            .title('确定上架商品"' + product.displayName + '"')
            .ariaLabel('Active Product')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            var data = {status: "ACTIVE"};
            $http.put("/admin/ajax/product/" + product.id + "/status", data)
                .then(function () {
                    $scope.reloadProducts();
                })
        }, function () {
        });

    };
    $scope.inActive = function (product) {
        var confirm = $mdDialog.confirm()
            .title('确定下架商品"' + product.displayName + '"')
            .ariaLabel('Inactive Product')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            var data = {status: "INACTIVE"};
            $http.put("/admin/ajax/product/" + product.id + "/status", data)
                .then(function () {
                    $scope.reloadProducts();
                });
        }, function () {
        });
    };
    $scope.copy = function (product) {
        var confirm = $mdDialog.confirm()
            .title('确定赋值商品"' + product.displayName + '"')
            .ariaLabel('Copy Product')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.put("/admin/ajax/product/copy", product)
                .then(function () {
                    $scope.toast("复制成功");
                    $scope.reloadProducts();
                });
        }, function () {
        });
    };
};