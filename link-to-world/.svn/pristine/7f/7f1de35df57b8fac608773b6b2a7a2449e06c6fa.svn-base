/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdToast) {
    $scope.types = [
        "MAIN",
        "ADDITIONAL"
    ];
    $scope.data = {};
    $http.get("/admin/ajax/clause/" + $stateParams.id)
        .then(function (response) {
            $scope.data.clause = response.data;
            $scope.data.origin = angular.copy($scope.data.clause);
        });
    $scope.uploadContent = function (file) {
        var url = "/admin/ajax/file/upload";
        if (file) {
            Upload.upload({
                url: url,
                data: {file: file}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.clause.contentURL = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {

            });
        }
    };
    $scope.uploadExclusion = function (file) {
        var url = "/admin/ajax/file/upload";
        if (file) {
            Upload.upload({
                url: url,
                data: {file: file}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.clause.exclusionsUrl = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {

            });
        }
    };
    $scope.uploadWord = function (file) {
        var url = "/admin/ajax/file/upload";
        if (file) {
            Upload.upload({
                url: url,
                data: {file: file}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.clause.wordURL = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {

            });
        }
    };
    $scope.uploadPdf = function (file) {
        var url = "/admin/ajax/file/upload";
        if (file) {
            Upload.upload({
                url: url,
                data: {file: file}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.clause.pdfURL = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {

            });
        }
    };
    $scope.save = function () {
        if (!$scope.check($scope.data.clause))return;
        $http.put("/admin/ajax/clause/" + $scope.data.clause.id, $scope.data.clause)
            .then(function () {
                $scope.toast("更新成功");
            });
    };
    $scope.check = function (clause) {
        if (!clause.name) {
            $scope.toast("名称不能为空");
            return false;
        }
        if (!clause.type) {
            $scope.toast("类型不能为空");
            return false;
        }
        if (!clause.serialNumber) {
            $scope.toast("备案号不能为空");
            return false;
        }
        return true;
    };
    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right bottom")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.cancel = function () {
        $scope.data.clause = angular.copy($scope.data.origin);
    };
};