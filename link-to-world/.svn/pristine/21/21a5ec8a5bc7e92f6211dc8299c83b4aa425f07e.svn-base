/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast, $element) {
    $scope.auditProductTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            status: null,
            order: 'status',
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            for (var i = 0; i < $scope.auditProductTable.data.items.length; i++) {
                if (id === $scope.auditProductTable.data.items[i].id) {
                    $scope.auditProductTable.data.items.splice(i, 1);
                }
            }
        }, edit: function (auditProduct) {
            $state.go("console.product.audit.update", {id: auditProduct.id});
        }
    };
    $scope.reloadAuditProducts = function () {
        $http.put("/admin/ajax/product/audit/list", $scope.auditProductTable.query)
            .then(function (response) {
                $scope.auditProductTable.data = response.data;
                $scope.auditProductTable.selected = [];
            });
    };

    $scope.deleteAuditProduct = function (auditProduct) {
        var confirm = $mdDialog.confirm()
            .title('确认删除记录"' + auditProduct.productDisplayName + '"')
            .ariaLabel('Delete AuditProduct')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/product/audit/' + auditProduct.id).then(function () {
                $scope.auditProductTable.delete(auditProduct.id);
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
        $scope.auditProductTable.searching = false;
        $scope.auditProductTable.query.status = null;
        $scope.reloadAuditProducts();
    };
    $scope.reloadAuditProducts();
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};