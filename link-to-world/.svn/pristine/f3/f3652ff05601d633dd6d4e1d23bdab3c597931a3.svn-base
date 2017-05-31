'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdToast, $mdDialog) {
    $http.get('/admin/ajax/order/' + $stateParams.orderId).then(function (response) {
        $scope.order = response.data;
    });

    $scope.discharge = function () {
        var confirm = $mdDialog.confirm()
            .title('确认撤单?')
            .ariaLabel('discharge order')
            .theme('white')
            .targetEvent(event)
            .ok('撤单')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.put("/admin/ajax/order/" + $scope.order.id + "/discharge")
                .then(function () {
                    $mdToast.simple()
                        .textContent('已撤单!')
                        .position('top right')
                        .hideDelay(3000)
                });
        }, function () {

        });
    };

    $scope.goBack = function () {
        history.back();
    }

};