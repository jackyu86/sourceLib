/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    if (!$stateParams.groupId) {
        $state.go("console.insurance.form-group.list");
    }
    $scope.formGroup = $stateParams.group;
    if (!$scope.formGroup) {
        $http.get("/admin/ajax/form-group/" + $stateParams.groupId)
            .then(function(response) {
                $scope.formGroup = response.data;
                $scope.reloadFormFields();
            });
    }
    $scope.isCustomGroup = function(formGroup) {
        return formGroup.name === 'custom';
    };

    $scope.formFieldTable = {
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
        delete: function(id) {
            for (var i = 0; i < $scope.formFieldTable.data.items.length; i++) {
                if (id === $scope.formFieldTable.data.items[i].id) {
                    $scope.formFieldTable.data.items.splice(i, 1);
                }
            }
        }, edit: function(formField) {
            $state.go("console.insurance.form-field.update", {id: formField.id, group: $scope.formGroup});
        },
        batchDelete: function(ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.formFieldTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadFormFields = function() {
        if ($scope.formGroup) {
            $http.put("/admin/ajax/form-field/find/" + $scope.formGroup.id, $scope.formFieldTable.query)
                .then(function(response) {
                    $scope.formFieldTable.data = response.data;
                    $scope.formFieldTable.selected = [];
                });
        }
    };
    $scope.cancelSearching = function() {
        $scope.formFieldTable.searching = false;
        $scope.formFieldTable.query.name = null;
        $scope.reloadFormFields();
    };
    $scope.reloadFormFields();
    $scope.toast = function(message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
};