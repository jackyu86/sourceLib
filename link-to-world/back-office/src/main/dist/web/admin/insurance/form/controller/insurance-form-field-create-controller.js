/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.formField = {
        type: "String",
        options: {
            notNull: true
        }
    };
    $scope.formGroup = $stateParams.group;

    $scope.save = function() {
        if (!$scope.check($scope.formField))return;
        $http.post("/admin/ajax/form-field", $scope.formField)
            .then(function() {
                $scope.toast("创建字段成功");
            });
    };

    $scope.$watch('formField.type', function(old, newValue) {
        var type = $scope.formField.type;
        if (type === 'String') {
            $scope.formField.options = {
                notNull: true
            }
        } else if (type === 'Integer') {
            $scope.formField.options = {
                notNull: true,
                min: null,
                max: null
            };
        } else if (type === 'Long') {
            $scope.formField.options = {
                notNull: true,
                min: null,
                max: null
            };
        } else if (type === 'Double') {
            $scope.formField.options = {
                notNull: true,
                min: null,
                max: null
            };
        } else if (type === 'Boolean') {
            $scope.formField.options = {
                notNull: true
            };
        }

    });
    $scope.fieldTypes = [
        {name: 'String', displayName: '字符串'},
        {name: 'Integer', displayName: '整数'},
        {name: 'Long', displayName: '长整数'},
        {name: 'Double', displayName: '浮点数'},
        {name: 'Boolean', displayName: '布尔'}
    ];

    $scope.check = function(formField) {
        if (!formField.displayName) {
            $scope.toast("名称不能为空");
            return false;
        }
        return true;
    };
    $scope.toast = function(message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.cancel = function() {
        $scope.data.formField = angular.copy($scope.data.origin);
    };
    $scope.displayAsList = [{displayName: "枚举型", value: "radio"}, {displayName: "下拉型", value: "selection"}];
};