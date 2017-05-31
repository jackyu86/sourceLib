/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.serialTable = {
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
            for (var i = 0; i < $scope.serialTable.data.items.length; i++) {
                if (id === $scope.serialTable.data.items[i].id) {
                    $scope.serialTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (serial) {
            $state.go("console.product.serial.update", {id: serial.id});
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.serialTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadSerials = function () {
        $http.put("/admin/ajax/product/serial/find", $scope.serialTable.query)
            .then(function (response) {
                $scope.serialTable.data = response.data;
                $scope.serialTable.selected = [];
            })
    };
    $scope.deleteSerial = function (serial) {
        var confirm = $mdDialog.confirm()
            .title('确认删除系列"' + serial.name + '"')
            .ariaLabel('Delete Serial')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/product/serial/' + serial.id).then(function () {
                $scope.serialTable.delete(serial.id);
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
        $scope.serialTable.searching = false;
        $scope.serialTable.query.name = null;
        $scope.reloadSerials();
    };
    $scope.reloadSerials();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};