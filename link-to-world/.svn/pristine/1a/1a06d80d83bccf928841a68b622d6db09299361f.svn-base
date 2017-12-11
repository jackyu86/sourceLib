/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.insuranceTable = {
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
            for (var i = 0; i < $scope.insuranceTable.data.items.length; i++) {
                if (id === $scope.insuranceTable.data.items[i].id) {
                    $scope.insuranceTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (insurance) {
            $state.go("console.insurance.update", {id: insurance.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.insuranceTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadInsurances = function () {
        $http.put("/admin/ajax/insurance/find", $scope.insuranceTable.query)
            .then(function (response) {
                $scope.insuranceTable.data = response.data;
                $scope.insuranceTable.selected = [];
            });
    };

    $scope.deleteInsurance = function (insurance) {
        var confirm = $mdDialog.confirm()
            .title('确认删除险种"' + insurance.name + '"')
            .ariaLabel('Delete Insurance')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/insurance/delete/' + insurance.id).then(function () {
                $scope.insuranceTable.delete(insurance.id);
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
        $scope.insuranceTable.searching = false;
        $scope.insuranceTable.query.name = null;
        $scope.reloadInsurances();
    };
    $scope.reloadInsurances();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};