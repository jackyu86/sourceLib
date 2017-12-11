/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.claimTable = {
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
            for (var i = 0; i < $scope.claimTable.data.items.length; i++) {
                if (id === $scope.claimTable.data.items[i].id) {
                    $scope.claimTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (claim) {
            $state.go("console.insurance.claim.update", {id: claim.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.claimTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadClaims = function () {
        $http.put("/admin/ajax/insurance/claim/find", $scope.claimTable.query)
            .then(function (response) {
                $scope.claimTable.data = response.data;
                $scope.claimTable.selected = [];
            });
    };

    $scope.cancelSearching = function () {
        $scope.claimTable.searching = false;
        $scope.claimTable.query.title = null;
        $scope.reloadClaims();
    };
    $scope.reloadClaims();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.deleteClaim = function (claim) {
        var confirm = $mdDialog.confirm()
            .title('确认删除理赔指引"' + claim.name + '"')
            .ariaLabel('Delete Claim')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/insurance/claim/' + claim.id).then(function () {
                $scope.claimTable.delete(claim.id);
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
};