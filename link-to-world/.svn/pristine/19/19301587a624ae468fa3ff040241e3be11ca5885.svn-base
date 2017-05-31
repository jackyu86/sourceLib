/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.formGroupTable = {
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
            limit: 20
        },
        data: {},
        selected: [],
        searching: false,
        delete: function(id) {
            for (var i = 0; i < $scope.formGroupTable.data.items.length; i++) {
                if (id === $scope.formGroupTable.data.items[i].id) {
                    $scope.formGroupTable.data.items.splice(i, 1);
                }
            }
        }, edit: function(formGroup) {
            $state.go("console.insurance.form-group.update", {id: formGroup.id});
        },
        batchDelete: function(ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.formGroupTable.delete(ids[i]);
            }
        }
    };
    $scope.reloadFormGroups = function() {
        $http.put("/admin/ajax/form-group/find", $scope.formGroupTable.query)
            .then(function(response) {
                $scope.formGroupTable.data = response.data;
                $scope.formGroupTable.selected = [];
            });
    };
    $scope.cancelSearching = function() {
        $scope.formGroupTable.searching = false;
        $scope.formGroupTable.query.name = null;
        $scope.reloadFormGroups();
    };
    $scope.reloadFormGroups();
    $scope.toast = function(message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.viewFormGroup = function(group) {
        $state.go("console.insurance.form-field.list", {groupId: group.id, group: group});
    };
};