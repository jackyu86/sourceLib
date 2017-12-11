/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.jobTreeTable = {
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
            for (var i = 0; i < $scope.jobTreeTable.data.items.length; i++) {
                if (id === $scope.jobTreeTable.data.items[i].id) {
                    $scope.jobTreeTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (jobTree) {
            $state.go("console.insurance.job-tree.update", {id: jobTree.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.jobTreeTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadJobTrees = function () {
        $http.put("/admin/ajax/job-tree/find", $scope.jobTreeTable.query)
            .then(function (response) {
                $scope.jobTreeTable.data = response.data;
                $scope.jobTreeTable.selected = [];
            });
    };

    $scope.deleteJobTree = function (jobTree) {
        var confirm = $mdDialog.confirm()
            .title('确认删除职业树"' + jobTree.name + '"')
            .ariaLabel('Delete JobTree')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/job-tree/' + jobTree.id).then(function () {
                $scope.jobTreeTable.delete(jobTree.id);
                var toast = $mdToast.simple()
                    .textContent('删除成功')
                    .position("bottom right")
                    .hideDelay(2000);
                $mdToast.show(toast);
            }).catch(function (response) {
                if (response.status == 400) {
                    $scope.toast(response.data.invalidFields[0].message);
                } else {
                    $scope.toast(response.data);
                }
            });
        }, function () {
        });
    };
    $scope.cancelSearching = function () {
        $scope.jobTreeTable.searching = false;
        $scope.jobTreeTable.query.name = null;
        $scope.reloadJobTrees();
    };
    $scope.reloadJobTrees();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.viewTree = function (tree) {
        $state.go("console.insurance.job", {treeId: tree.id, tree: tree});
    };
};