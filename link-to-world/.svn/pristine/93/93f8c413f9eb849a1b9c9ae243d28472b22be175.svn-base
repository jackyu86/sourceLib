/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast) {
    $scope.data = {};
    $scope.data.insurance = {master: false};
    $scope.data.insurance.liabilities = [];
    $scope.data.insurance.priceTable = {xFactors: [], table: null};
    $scope.selectedLiability = null;
    $scope.selectedLiabilityIds = [];
    $scope.selectedLiabilities = [];

    $scope.baseFactorGroups = [];
    $scope.baseFactorFields = [];
    $scope.baseFactorGroup = null;
    $scope.baseFactorField = null;
    $scope.xFactorGroups = [];
    $scope.xFactorFields = [];
    $scope.xFactorGroup = null;
    $scope.xFactorField = null;
    $scope.selectedXFactors = [];
    $scope.yFactorGroups = [];
    $scope.yFactorFields = [];
    $scope.yFactorGroup = null;
    $scope.yFactorField = null;

    $scope.selectLiability = function (liability) {
        $scope.selectedLiability = liability;
    };
    $scope.addLiability = function () {
        if (!$scope.canAddLiability()) {
            return false;
        }
        var ref = {
            insuranceLiabilityId: $scope.selectedLiability.id,
            amount: {selections: []},
            description: "",
            liabilityName: $scope.selectedLiability.name,
            toggle: true
        };
        $scope.data.insurance.liabilities.push(ref);
        $scope.selectedLiabilityIds.push($scope.selectedLiability.id);
        $scope.selectedLiabilities.push($scope.selectedLiability);
        if ($scope.baseFactorGroup != null && $scope.baseFactorGroup.name == "liability") {
            $scope.baseFactorFields.push({name: $scope.selectedLiability.id, displayName: $scope.selectedLiability.name});
        }
        if ($scope.xFactorGroup != null && $scope.xFactorGroup.name == "liability") {
            $scope.xFactorFields.push({name: $scope.selectedLiability.id, displayName: $scope.selectedLiability.name});
        }
        if ($scope.yFactorGroup != null && $scope.yFactorGroup.name == "liability") {
            $scope.yFactorFields.push({name: $scope.selectedLiability.id, displayName: $scope.selectedLiability.name});
        }
        $scope.selectedLiability = null;
    };
    $scope.canAddLiability = function () {
        if ($scope.selectedLiability == null) {
            return false;
        }
        return !($scope.selectedLiabilityIds.indexOf($scope.selectedLiability.id) > -1);
    };
    $scope.loadLiability = function () {
        $http.put("/admin/ajax/insurance/liability/find", $scope.liabilityQuery)
            .then(function (response) {
                $scope.liabilities = response.data.items;
            });
    };
    $scope.deleteLiability = function (index) {
        $scope.selectedLiabilityIds.splice(index, 1);
        $scope.data.insurance.liabilities.splice(index, 1);
        $scope.selectedLiabilities.splice(index, 1);
    };
    $scope.liabilityQuery = {name: null, page: 1, limit: 999999};
    $http.put("/admin/ajax/form-group/find", {page: 1, limit: 999999})
        .then(function (response) {
            $scope.baseFactorGroups = response.data.items;
            $scope.xFactorGroups = response.data.items;
            $scope.yFactorGroups = response.data.items;
        });
    $scope.selectBaseFactorGroup = function (group) {
        $scope.baseFactorGroup = group;
        if (group.name != "liability") {
            $http.put("/admin/ajax/form-field/find/" + group.id, {page: 1, limit: 999999})
                .then(function (response) {
                    $scope.baseFactorFields = response.data.items;
                });
        } else {
            $scope.baseFactorFields = [];
            if ($scope.selectedLiabilities.length == 0) {
                $scope.toast("请先添加保险责任");
                return false;
            }
            for (var i = 0; i < $scope.selectedLiabilities.length; i++) {
                var l = $scope.selectedLiabilities[i];
                var f = {name: l.id, displayName: l.name};
                $scope.baseFactorFields.push(f);
            }
        }
    };
    $scope.selectBaseFactorField = function (field) {
        $scope.baseFactorField = field;
    };
    $scope.selectBaseFactor = function () {
        $scope.data.insurance.priceTable.baseFactor = $scope.baseFactorGroup.name + "." + $scope.baseFactorField.name;
    };
    $scope.selectXFactorGroup = function (group) {
        $scope.xFactorGroup = group;
        if (group.name != "liability") {
            $http.put("/admin/ajax/form-field/find/" + group.id, {page: 1, limit: 999999})
                .then(function (response) {
                    $scope.xFactorFields = response.data.items;
                });
        } else {
            $scope.xFactorFields = [];
            if ($scope.selectedLiabilities.length == 0) {
                $scope.toast("请先添加保险责任");
                return false;
            }
            for (var i = 0; i < $scope.selectedLiabilities.length; i++) {
                var l = $scope.selectedLiabilities[i];
                var f = {name: l.id, displayName: l.name};
                $scope.xFactorFields.push(f);
            }
        }
    };
    $scope.selectXFactorField = function (field) {
        $scope.xFactorField = field;
    };
    $scope.addXFactor = function () {
        if (!$scope.canAddXFactor())return false;
        var f = $scope.xFactorGroup.name + "." + $scope.xFactorField.name;
        $scope.data.insurance.priceTable.xFactors.push(f);
        $scope.selectedXFactors.push(f);
    };
    $scope.canAddXFactor = function () {
        if ($scope.xFactorField == null)return false;
        return !($scope.selectedXFactors.indexOf($scope.xFactorGroup.name + "." + $scope.xFactorField.name) > -1);
    };
    $scope.deleteXFactor = function (index) {
        $scope.data.insurance.priceTable.xFactors.splice(index, 1);
        $scope.selectedXFactors.splice(index, 1);
    };
    $scope.selectYFactorGroup = function (group) {
        $scope.yFactorGroup = group;
        if (group.name != "liability") {
            $http.put("/admin/ajax/form-field/find/" + group.id, {page: 1, limit: 999999})
                .then(function (response) {
                    $scope.yFactorFields = response.data.items;
                });
        } else {
            $scope.yFactorFields = [];
            if ($scope.selectedLiabilities.length == 0) {
                $scope.toast("请先添加保险责任");
                return false;
            }
            for (var i = 0; i < $scope.selectedLiabilities.length; i++) {
                var l = $scope.selectedLiabilities[i];
                var f = {name: l.id, displayName: l.name};
                $scope.yFactorFields.push(f);
            }
        }
    };
    $scope.selectYFactorField = function (field) {
        $scope.yFactorField = field;
    };
    $scope.selectYFactor = function () {
        $scope.data.insurance.priceTable.yFactor = $scope.yFactorGroup.name + "." + $scope.yFactorField.name;
    };

    $scope.save = function () {
        if (!$scope.check($scope.data.insurance))return false;
        if ($scope.data.insurance.priceTable.engine == "TABLE" || $scope.data.insurance.priceTable.engine == "BASE_TABLE") {
            //$scope.data.insurance.priceTable.tableString = angular.fromJson($scope.data.table);
            $scope.data.insurance.priceTable.table = $scope.data.table;
        }
        console.log($scope.data.insurance);
        $http.post("/admin/ajax/insurance", $scope.data.insurance)
            .then(function (response) {
                $scope.toast("保存成功");
                $state.go("console.insurance.list");
            })
    };
    $scope.check = function (insurance) {
        if (!insurance.name) {
            $scope.toast("名称必填");
            return false;
        }
        if (insurance.master == null) {
            $scope.toast("是否主险必填");
            return false;
        }
        if (insurance.maxAmount == null) {
            $scope.toast("最大份数必填");
            return false;
        }
        if (isNaN(insurance.maxAmount)) {
            $scope.toast("最大份数必须是数字");
            return false;
        }
        if (insurance.maxAmount < 1) {
            $scope.toast("最大份数最少为1");
            return false;
        }
        if (insurance.liabilities.length <= 0) {
            $scope.toast("请选择保险责任");
            return false;
        }
        for (var i = 0; i < insurance.liabilities.length; i++) {
            var l = insurance.liabilities[i];
            var a = l.amount;
            if (a.type == null) {
                $scope.toast("保额类型必填");
                return false;
            }
            if (a.type == "USER_INPUT") {
                if (a.inputMax == null) {
                    $scope.toast("最大值必填");
                    return false;
                }
                if (a.inputMin == null) {
                    $scope.toast("最小值必填");
                    return false;
                }
                if (a.inputIncrementUnit == null) {
                    $scope.toast("增长单位必填");
                    return false;
                }
            } else if (a.type == "USER_SELECTION") {
                if (a.selections == null || a.selections.length <= 0) {
                    $scope.toast("用户选项必填");
                    return false;
                }
                for (var si = 0; si < a.selections.length; si++) {
                    if (isNaN(a.selections[si])) {
                        $scope.toast("用户选项必须为数字");
                        return false;
                    }
                }
            } else if (a.type == "UNIT_SELECTION") {
                if (a.minUnits == null) {
                    $scope.toast("最小份数必填");
                    return false;
                }
                if (a.maxUnits == null) {
                    $scope.toast("最大份数必填");
                    return false;
                }
            } else if (a.type == "FORMULA") {
                if (a.formulaName == null) {
                    $scope.toast("公式名称必填");
                    return false;
                }
            } else if (a.type == "FIXED") {
                if (a.fixedValue == null) {
                    $scope.toast("固定保额必填");
                    return false;
                }
            }
        }
        var p = insurance.priceTable;
        if (p.engine == null) {
            $scope.toast("计费引擎必填");
            return false;
        }
        if (p.engine == "FIXED") {
            if (p.fixedPrice == null) {
                $scope.toast("固定保费必填");
                return false;
            }
        } else if (p.engine == "TABLE") {
            if (p.xFactors == null || p.xFactors.length <= 0) {
                $scope.toast("请选择X因子");
                return false;
            }
            if (p.yFactor == null) {
                $scope.toast("请选择Y因子");
            }
            try {
                var t = angular.fromJson($scope.data.table);
            } catch (e) {
                console.log(e);
                $scope.toast("请输入正确的价格表JSON字符串");
                return false;
            }
        } else if (p.engine == "BASE_TABLE") {
            if (p.xFactors == null || p.xFactors.length <= 0) {
                $scope.toast("请选择X因子");
                return false;
            }
            if (p.yFactor == null) {
                $scope.toast("请选择Y因子");
            }
            if ($scope.data.table == null) {
                $scope.toast("请输入价格表");
                return false;
            }
            try {
                var t = angular.fromJson($scope.data.table);
            } catch (e) {
                console.log(e);
                $scope.toast("请输入正确的价格表JSON字符串");
                return false;
            }
            if (p.base == null) {
                $scope.toast("请输入基本保险金额");
                return false;
            }
            if (p.baseFactor == null) {
                $scope.toast("请选择基本计费因子");
                return false;
            }
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
};