/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.clauseTable = {
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
            for (var i = 0; i < $scope.clauseTable.data.items.length; i++) {
                if (id === $scope.clauseTable.data.items[i].id) {
                    $scope.clauseTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (clause) {
            $state.go("console.insurance.clause.update", {id: clause.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.clauseTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadClauses = function () {
        $http.put("/admin/ajax/clause/find", $scope.clauseTable.query)
            .then(function (response) {
                $scope.clauseTable.data = response.data;
                $scope.clauseTable.selected = [];
            });
    };

    $scope.deleteClause = function (clause) {
        var confirm = $mdDialog.confirm()
            .title('确认删除条款"' + clause.name + '"')
            .ariaLabel('Delete Clause')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/clause/' + clause.id).then(function () {
                $scope.clauseTable.delete(clause.id);
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
    $scope.cancelSearching = function () {
        $scope.clauseTable.searching = false;
        $scope.clauseTable.query.name = null;
        $scope.reloadClauses();
    };
    $scope.reloadClauses();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};