'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdToast) {
    $scope.processing = false;
    $scope.isEdit = false;
    $scope.existCredit = false;

    if ($state.is('console.dealer.update')) {
        var id = $stateParams.id;
        $http.get('/admin/ajax/dealer/' + id).then(function (response) {
            $scope.data = response.data;
            $scope.states = response.data.states;
            $scope.original = angular.copy($scope.data);
            $scope.changeCity();
            $scope.isEdit = true;

            if ($scope.data.credit != undefined) {
                $scope.existCredit = true;
                $scope.creditStatusDisplay = creditStatusDisplay($scope.data.credit.status);
            }
        });

        $scope.reloadCreditTracking = function () {
            $http.get('/admin/ajax/dealer/' + id + '/credit/tracking').then(function (response) {
                $scope.creditTrackingTable = response;
            })
        };

        $scope.reset = function () {
            var dealer = $scope.data.dealer;
            if (dealer === undefined || dealer.id === undefined) {
                var toast = $mdToast.simple()
                    .textContent('分销商不存在！')
                    .position("bottom right")
                    .hideDelay(2000);

                $mdToast.show(toast);
                return;
            }
            $http.post('/admin/ajax/dealer/' + dealer.id + "/reset").then(function () {
                $scope.data.credit.totalCredits = $scope.data.credit.quota;
                $scope.reloadCreditTracking();
            });
        };

        $scope.reloadCreditTracking();
    } else {
        $http.get('/admin/ajax/province').then(function (response) {
            $scope.states = response.data;
        })
    }

    $scope.isDataChanged = function () {
        return !angular.equals($scope.data, $scope.original);
    };

    $scope.cancel = function () {
        $scope.data = angular.copy($scope.original);
    };

    $scope.changeCreditStatus = function () {
        $http.get('/admin/ajax/dealer/' + $scope.data.dealer.id + "/credit/status").then(function (response) {
            $scope.creditStatusDisplay = creditStatusDisplay(response.data);
        })
    };

    $scope.changeCity = function () {
        var stateId = $scope.states.filter(function (state) {
            return state.name === $scope.data.dealer.state;
        })[0].id;
        $http.get('/admin/ajax/province/' + stateId).then(function (response) {
            $scope.cities = response.data;
            $scope.changeWard();
        })
    };

    $scope.changeWard = function () {
        var selectedCities = $scope.cities.filter(function (city) {
            return city.name === $scope.data.dealer.city;
        });
        if (selectedCities.length == 0) {
            $scope.wards = [];
            return;
        }
        var cityId = selectedCities[0].id;
        $http.get('/admin/ajax/city/' + cityId).then(function (response) {
            $scope.wards = response.data;
        })
    };

    $scope.save = function () {
        $scope.processing = true;
        if ($state.is('console.dealer.update')) {
            $http.put('/admin/ajax/dealer', $scope.data).then(function (response) {
                handleSuccess(response, $mdToast, $state);
            }, function (response) {
                handleError(response);
            });
        } else {
            $http.post("/admin/ajax/dealer", $scope.data).then(function (response) {
                handleSuccess(response, $mdToast, $state);
            }, function (response) {
                handleError(response);
            });
        }
        $scope.processing = false;
    };
};

function handleSuccess(response, $mdToast, $state) {
    var toast = $mdToast.simple()
        .textContent('分销商已保存')
        .position("top right")
        .hideDelay(2000);

    $mdToast.show(toast);
    $state.go('console.dealer.update', {id: response.data.dealer.id}, {reload: true});
}

function handleError(response) {
    var e = response.data;
    if (e.invalidFields) {
        $scope.dealerForm[e.invalidFields[0].path].$setValidity('invalid', false);
    }
    $scope.processing = false;
}

function creditStatusDisplay(status) {
    if (status == "ACTIVE") {
        return "锁定";
    }
    return "解锁";
}