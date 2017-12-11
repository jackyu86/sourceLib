/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.liabilityGroupTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            order: '-priority',
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            for (var i = 0; i < $scope.liabilityGroupTable.data.items.length; i++) {
                if (id === $scope.liabilityGroupTable.data.items[i].id) {
                    $scope.liabilityGroupTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (liabilityGroup) {
            $state.go("console.insurance.liability-group.update", {id: liabilityGroup.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.liabilityGroupTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadLiabilityGroups = function () {
        $http.put("/admin/ajax/insurance/liability/group/find", $scope.liabilityGroupTable.query)
            .then(function (response) {
                $scope.liabilityGroupTable.data = response.data;
                $scope.liabilityGroupTable.selected = [];
                for (var i = 0; i < $scope.liabilityGroupTable.data.items.length; i++) {
                    $scope.getCount(i);
                }
            });
    };
    $scope.getCount = function (i) {
        var g = $scope.liabilityGroupTable.data.items[i];
        $http.get("/admin/ajax/insurance/liability/group/" + g.id + "/count")
            .then(function (response) {
                $scope.liabilityGroupTable.data.items[i].count = response.data;
            });
    };
    $scope.cancelSearching = function () {
        $scope.liabilityGroupTable.searching = false;
        $scope.liabilityGroupTable.query.name = null;
        $scope.reloadLiabilityGroups();
    };
    $scope.reloadLiabilityGroups();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.viewLiabilityGroup = function (group) {
        $state.go("console.insurance.liability.list", {groupId: group.id, group: group});
    };
    $scope.deleteGroup = function (group) {
        var confirm = $mdDialog.confirm()
            .title('确认删除分组"' + group.name + '"')
            .ariaLabel('Delete Group')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/insurance/liability/group/' + group.id)
                .then(function () {
                    $scope.liabilityGroupTable.delete(group.id);
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
    };
    $scope.groupCount = function (liabilityGroup) {
        return !(liabilityGroup.count && liabilityGroup.count > 0);
    };
};