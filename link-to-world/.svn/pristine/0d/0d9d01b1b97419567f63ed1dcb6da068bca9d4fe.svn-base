/**
 * @author miller
 */
'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.levels = [
        "INSURANCE_GROUP",
        "INSURANCE_SUB_COMPANY",
        "INSURANCE_BRANCH",
        "INSURANCE_AGENT"
    ];
    $scope.data = {};
    $http.get("/admin/ajax/vendor/" + $stateParams.id)
        .then(function(response) {
            $scope.data.vendor = response.data;
            $scope.data.origin = angular.copy($scope.data.vendor);
        });
    //todo
    $scope.uploadImage = function(file) {
        Upload.upload({
            url: "/admin/ajax/file/upload",
            data: {
                file: file
            }
        }).then(function(response) {
            console.log(response);
            $scope.data.vendor.imageURL = response.data.path;
        }, function(errResponse) {
            console.log(errResponse);
        }, function(event) {

        });
    };
    //todo
    $scope.uploadLicenceImage = function(file) {
        Upload.upload({
            url: "/admin/ajax/file/upload",
            data: {
                file: file
            }
        }).then(function(response) {
            console.log(response);
            $scope.data.vendor.licenceImageURL = response.data.path;
        }, function(errResponse) {
            console.log(errResponse);
        }, function(event) {

        });
    };
    $scope.save = function() {
        if (!$scope.check($scope.data.vendor))return;
        $http.put("/admin/ajax/vendor/" + $scope.data.vendor.id, $scope.data.vendor)
            .then(function() {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function(vendor) {
        if (!vendor.name) {
            $scope.toast("名称不能为空");
            return false;
        }
        if (!vendor.level) {
            $scope.toast("供应商级别不能为空");
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
        $scope.data.vendor = angular.copy($scope.data.origin);
    };
};
