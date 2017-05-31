'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http) {
    $scope.statisticsTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        levels: [{value: null, name: "全部"}, {value: "LEVEL1", name: "一级"}, {value: "LEVEL2", name: "二级"}, {value: "LEVEL3", name: "三级"}, {value: "LEVEL4", name: "四级"}],
        query: {
            level: null
        },
        data: {},
        selected: [],
        searching: false
    };

    $scope.reloadStatistics = function () {
        $http.put('/admin/ajax/dealer/statistics', $scope.statisticsTable.query).then(function (response) {
            $scope.statisticsTable.data = response.data;
            $scope.statisticsTable.selected = [];
        });
    };

    $scope.cancelSearching = function () {
        $scope.statisticsTable.searching = false;
        $scope.statisticsTable.query = {
            level: "LEVEL1",
            page: 1,
            limit: 10
        };

        $scope.reloadStatistics();
    };

    $scope.reloadStatistics();
};