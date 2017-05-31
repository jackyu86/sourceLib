'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http) {
    $scope.settlementTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        levels: [{value: "LEVEL1", name: "一级"},
            {value: "LEVEL2", name: "二级"},
            {value: "LEVEL3", name: "三级"},
            {value: "LEVEL4", name: "四级"}],
        statuses: [{value: "DRAFT", name: "拟定"},
            {value: "PAYMENT_PENDING", name: "待支付"},
            {value: "PAYMENT_FAILED", name: "支付失败"},
            {value: "PAYMENT_COMPLETED", name: "支付成功"},
            {value: "AUDITING", name: "核保"},
            {value: "VENDOR_INSURED", name: "已承保"},
            {value: "VENDOR_REJECT", name: "拒保"},
            {value: "DOCUMENTED", name: "已出单"},
            {value: "SURRENDERING", name: "请求退保"},
            {value: "SURRENDERED", name: "已退保"},
            {value: "REFUND", name: "已退款"}],
        query: {
            level: "LEVEL1",
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false
    };

    $scope.reloadSettlement = function () {
        $http.put('/admin/ajax/dealer/settlement', $scope.settlementTable.query).then(function (response) {
            $scope.settlementTable.data = response.data;
            $scope.settlementTable.selected = [];
        });
    };

    $scope.cancelSearching = function () {
        $scope.settlementTable.searching = false;
        $scope.settlementTable.query = {
            level: "LEVEL1",
            page: 1,
            limit: 10
        };

        $scope.reloadSettlement();
    };

    $scope.reloadSettlement();
};