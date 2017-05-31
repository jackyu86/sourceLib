'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog) {
    $scope.dealerTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        levels: [{value: null, name: "全部"},
            {value: "LEVEL1", name: "一级"},
            {value: "LEVEL2", name: "二级"},
            {value: "LEVEL3", name: "三级"},
            {value: "LEVEL4", name: "四级"}],
        query: {
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            $http.get("/admin/ajax/dealer/" + id + "/children/check").then(function (response) {
                var title = '是否确认删除这个分销商？';
                if (response.data) {
                    title += '（注：该分销商存在下级分销商和出单员！）'
                }
                var confirm = $mdDialog.confirm()
                    .title(title)
                    .ok('确认')
                    .cancel('取消');

                $mdDialog.show(confirm).then(function () {
                    $http.delete("/admin/ajax/dealer/" + id).then(function () {
                        $scope.reloadDealer();
                    })
                }, function () {

                });
            })
        },
        edit: function (dealer) {
            $state.go('console.dealer.update', {id: dealer.id});
        },
        block: function (id) {
            $http.get("/admin/ajax/dealer/" + id + "/block").then(function () {
                $scope.reloadDealer();
            })
        },
        unblock: function (id) {
            $http.get("/admin/ajax/dealer/" + id + "/unblock").then(function () {
                $scope.reloadDealer();
            })
        }
    };

    $scope.reloadDealer = function () {
        $http.put('/admin/ajax/dealer/find', $scope.dealerTable.query).then(function (response) {
            $scope.dealerTable.data = response.data;
            $scope.dealerTable.selected = [];
        });
    };

    $scope.cancelSearching = function () {
        $scope.dealerTable.searching = false;
        $scope.dealerTable.query.name = null;

        $scope.reloadDealer();
    };

    $scope.productManager = function (dealer) {
        $state.go("console.dealer.product", {id: dealer.id});
    };

    $scope.reloadDealer();
};