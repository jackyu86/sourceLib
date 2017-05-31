/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    if (!$stateParams.groupId) {
        $state.go("console.insurance.liability-group.list");
    }
    $scope.groupId = $stateParams.groupId;
    $scope.group = $stateParams.group;
    if (!$scope.group) {
        $http.get("/admin/ajax/insurance/liability/group/" + $stateParams.groupId)
            .then(function (response) {
                $scope.group = response.data;
                $scope.liabilityTable.query.groupId = $scope.group.id;
                $scope.reloadLiabilities();
            });
    }
    $scope.liabilityTable = {
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
            limit: 10,
            groupId: $stateParams.group ? $stateParams.group.id : null
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            for (var i = 0; i < $scope.liabilityTable.data.items.length; i++) {
                if (id === $scope.liabilityTable.data.items[i].id) {
                    $scope.liabilityTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (liability) {
            $state.go("console.insurance.liability.update", {id: liability.id, group: $scope.group});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.liabilityTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadLiabilities = function () {
        if ($scope.group) {
            $http.put("/admin/ajax/insurance/liability/find", $scope.liabilityTable.query)
                .then(function (response) {
                    $scope.liabilityTable.data = response.data;
                    $scope.liabilityTable.selected = [];
                });
        }
    };
    $scope.cancelSearching = function () {
        $scope.liabilityTable.searching = false;
        $scope.liabilityTable.query.name = null;
        $scope.reloadLiabilities();
    };
    $scope.reloadLiabilities();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.deleteLiability = function (liability) {
        var confirm = $mdDialog.confirm()
            .title('确认删除分组"' + liability.name + '"')
            .ariaLabel('Delete Liability')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/insurance/liability/delete/' + liability.id)
                .then(function () {
                    $scope.liabilityTable.delete(liability.id);
                    var toast = $mdToast.simple()
                        .textContent('删除成功')
                        .position("bottom right")
                        .hideDelay(2000);
                    $mdToast.show(toast);
                }).catch(function (response) {
                if (response.status == 400) {
                    var error = response.data.invalidFields[0];
                    $scope.toast("删除失败!原因：" + error.messageKey);
                } else {
                    $scope.toast(response.data);
                }
            });
        }, function () {
        });
    }
};