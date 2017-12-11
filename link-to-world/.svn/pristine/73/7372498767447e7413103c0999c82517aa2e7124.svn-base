/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, $mdDialog, $mdToast, $location) {
    $scope.name = $stateParams.name;
    $scope.values = [];
    $scope.selected = [];
    $scope.enumType = {};

    $http.get('/admin/ajax/enum-type/' + $stateParams.name).then(function(response) {
        $scope.enumType = response.data;
    });

    $http.get('/admin/ajax/enum-type/' + $stateParams.name + '/values').then(function(response) {
        $scope.values = response.data;
    });

    $scope.update = function(value, event) {
        $mdDialog.show({
            controller: function($scope) {
                $scope.value = value;
                $scope.update = function() {
                    $http.put('/admin/ajax/enum-type/' + $stateParams.name + '/' + value.id, value).then(function() {
                    });
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.hide();
                };
            },
            templateUrl: '/admin/enum/view/enum.valueUpdate.html',
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true
        });
    };

    $scope.create = function(event) {
        var values = $scope.values;
        $mdDialog.show({
            controller: function($scope) {
                $scope.value = {};
                $scope.update = function() {
                    $http.post('/admin/ajax/enum-type/' + $stateParams.name, $scope.value).then(function(response) {
                        values.push(response.data);
                    });
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.hide();
                };
            },
            templateUrl: '/admin/enum/view/enum.valueUpdate.html',
            parent: angular.element(document.body),
            targetEvent: event,
            clickOutsideToClose: true
        });
    };

    $scope.delete = function(value, event) {
        var values = $scope.values;
        var confirm = $mdDialog.confirm()
            .title('确认删除值"' + value.name + '"')
            .ariaLabel('Delete value')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function() {
            $http.delete('/admin/ajax/enum-type/' + $stateParams.name + '/' + value.id).then(function() {
                for (var i = 0; i , values.length; i++) {
                    if (values[i].id === value.id) {
                        values.splice(i, 1);
                        break;
                    }
                }
            });
        });
    };
};