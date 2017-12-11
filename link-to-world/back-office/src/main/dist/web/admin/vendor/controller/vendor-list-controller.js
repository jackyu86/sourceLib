/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog, $mdToast) {
    $scope.vendorTable = {
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
            for (var i = 0; i < $scope.vendorTable.data.items.length; i++) {
                if (id === $scope.vendorTable.data.items[i].id) {
                    $scope.vendorTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (vendor) {
            $state.go("console.vendor.update", {id: vendor.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.vendorTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadVendors = function () {
        $http.put("/admin/ajax/vendor/find", $scope.vendorTable.query)
            .then(function (response) {
                $scope.vendorTable.data = response.data;
                $scope.vendorTable.selected = [];
            })
    };

    $scope.deleteVendor = function (vendor) {
        var confirm = $mdDialog.confirm()
            .title('确认删除供应商"' + vendor.name + '"')
            .ariaLabel('Delete Vendor')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/vendor/' + vendor.id).then(function () {
                $scope.vendorTable.delete(vendor.id);
                var toast = $mdToast.simple()
                    .textContent('删除成功')
                    .position("top right")
                    .hideDelay(2000);
                $mdToast.show(toast);
            });
        }, function () {
        });
    };

    $scope.batchDeleteVendors = function () {
        var confirm = $mdDialog.confirm()
            .title('确认删除选中的供应商' + $scope.vendorTable.selected.length + "个?")
            .ariaLabel('Delete Vendor')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');
        var request = {ids: []};
        for (var i = 0; i < $scope.vendorTable.selected.length; i++) {
            request.ids.push($scope.vendorTable.selected[i].id);
        }
        $mdDialog.show(confirm).then(function () {
            $http.put('/admin/ajax/vendor/delete', request)
                .then(function () {
                    $scope.vendorTable.batchDelete(request.ids);
                    var toast = $mdToast.simple()
                        .textContent('删除成功')
                        .position("top right")
                        .hideDelay(2000);
                    $mdToast.show(toast);
                });
        }, function () {
        });
    };

    $scope.cancelSearching = function () {
        $scope.vendorTable.searching = false;
        $scope.vendorTable.query.name = null;
        $scope.reloadVendors();
    };

    $scope.reloadVendors();
};