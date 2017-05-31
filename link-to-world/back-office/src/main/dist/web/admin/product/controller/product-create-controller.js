/**
 * @author miller
 */
var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog,
                           $mdToast, $element, $location, $anchorScroll) {
    $element.find('input').on('keydown', function (ev) {
        ev.stopPropagation();
    });
    $scope.data = {};
    $scope.data.product = {};
    $scope.init = function () {
        var product = $scope.data.product;
        product.keywords = [];
        product.version = 0;
        product.tags = [];
        product.publicVisible = 'ALL';
        product.questions = [];
        product.cases = [];
        product.insuranceCategoryIds = [];
        product.period = {
            type: "FIXED",
            fixedValue: {unit: "DAY"},
            inputUnit: "DAY",
            inputMax: {displayName: "1天", value: 1, unit: "DAY"},
            inputMin: {displayName: "1天", value: 1, unit: "DAY"},
            selections: [],
            ages: [],
            earliestInsuranceTime: {displayName: "1天", value: 1, unit: "DAY"},
            startTimeType: "USER_INPUT"
        };
        product.payment = {
            methods: [],
            fixedPeriods: [],
            irregularPeriods: [],
            yearPeriods: [],
            agePeriods: []
        };
        product.jobRestricted = false;
        product.jobLevels = [];
        product.jobIds = [];
        product.status = 'ACTIVE';
        product.policyHolderMaxAge = {displayName: "", value: "", unit: "AGE"};
        product.policyHolderMinAge = {displayName: "", value: "", unit: "AGE"};
        product.insuredMaxAge = {displayName: "", value: "", unit: "AGE"};
        product.insuredMinAge = {displayName: "", value: "", unit: "AGE"};
        product.insurances = [];
        product.insuranceDividendTypes = [];
        product.insuranceCityIds = [];
        product.insuranceAreaIds = [];
        product.agreeUrls = [];
        product.insuranceClauseIds = [];
        product.insuranceClaimIds = [];
        product.insuranceClaims = [{}];
        product.plp = [];
        product.pdp = [{group: "product", field: "serial", editable: false}];
        product.formGroups = [];

        $scope.categories = [];
        $scope.productCategories = [];
        $scope.selectedCategory = {};
        $scope.categoryShow = true;
        $scope.questionShow = true;
        $scope.caseShow = true;
        $scope.periodShow = true;
        $scope.selectPeriod = {unit: "DAY"};
        $scope.selectAgePeriod = {unit: "AGE"};
        $scope.paymentShow = true;
        $scope.jobTreeShow = true;
        $scope.jobTrees = [];
        $scope.selectedJob = {};
        $scope.productJobs = [];
        $scope.insuranceShow = true;
        $scope.selectedInsurance = null;
        $scope.insuranceIds = [];
        $scope.selectedDividendType = null;
        $scope.formShow = true;
        $scope.fileShow = true;
        $scope.cityShow = true;
        $scope.selectedCity = null;
        $scope.productCities = [];
        $scope.selectedArea = null;
        $scope.productAreas = [];
        $scope.agree = {};
        $scope.selectedInsuranceClause = null;
        $scope.productClauses = [];
        $scope.selectedInsuranceClaim = null;
        $scope.productClaims = [];
        $scope.formGroupsShow = true;
        $scope.formGroups = [];
        $scope.formGroupNames = [];
        $scope.selectedFormGroup = null;
        $scope.selectedFormGroups = [];
        $scope.formGroup = {};
        $scope.liabilityGroupFieldNames = [];
        $scope.selectedElement = null;
        $scope.displayAsList = [{displayName: "枚举型", value: "radio"}, {displayName: "下拉型", value: "selection"}];
        $scope.provinces = [];
        $scope.productProvinces = [];
        $scope.selectedProvinceIds = [];
        $scope.jobIds = [];
        $scope.jobs = [];
        $scope.firstLevelIds = [];
        $scope.secondLevelIds = [];
        $scope.selectBoolean = {
            selectTrue: true,
            selectFalse: false
        };

        $scope.getSerial();
        $scope.getVendor();
        $scope.initProvinces();
        $scope.initFormGroups();
        $scope.initAreas();
    };

    $scope.publicVisibleType = [
        {name: "只有个人用户可见", value: "ONLY_CUSTOMER"},
        {name: "只有分销商可见", value: "ONLY_DEALER"},
        {name: "全部可见", value: "ALL"}
    ];
    $scope.periodTypes = [
        {name: "固定", value: "FIXED"},
        {name: "用户输入", value: "USER_INPUT"},
        {name: "用户填写", value: "USER_SELECTION"},
        {name: "年龄段", value: "AGE_DURATION"}
    ];
    $scope.periodUnits = [
        {name: "天", value: "DAY"},
        {name: "月", value: "MONTH"},
        {name: "年", value: "YEAR"},
        {name: "岁", value: "AGE"},
        {name: "一生", value: "LIFE"}
    ];
    $scope.periodStartTimeTypes = [
        {name: "用户输入", value: "USER_INPUT"},
        {name: "默认", value: "DEFAULT"},
        {name: "最近", value: "LATEST"}
    ];
    $scope.paymentMethods = [
        {name: "固定", value: "FIXED"},
        {name: "不固定", value: "IRREGULAR"},
        {name: "按年缴", value: "YEAR"},
        {name: "按年龄缴", value: "AGE"}
    ];
    $scope.jobLevels = [];
    $scope.productType = [
        {name: "主产品", value: "MAIN"},
        {name: "关联产品", value: "ASSOCIATE"}
    ];
    $scope.liabilityTypes = [
        {name: "客户填写", value: "USER_INPUT"},
        {name: "客户单选选项", value: "USER_SELECTION"},
        {name: "总份数选择", value: "UNIT_SELECTION"},
        {name: "计算公式", value: "FORMULA"},
        {name: "固定值", value: "FIXED"}
    ];
    $scope.dividendTypes = [
        {name: "累积生息", value: "HL01"},
        {name: "现金领取", value: "HL02"},
        {name: "抵交保费", value: "HL03"},
        {name: "购买增额交清保险", value: "HL04"}
    ];
    $scope.displayAsList = [
        {displayName: "枚举型", value: "radio"},
        {displayName: "下拉型", value: "selection"}
    ];

    $scope.categoryQuery = {page: 1, limit: 99999, order: "", name: null};
    $scope.getCategory = function () {
        $http.put("/admin/ajax/insurance/category/find", $scope.categoryQuery)
            .then(function (response) {
                $scope.categories = response.data.items;
            })
    };
    $scope.loadCategory = function () {
        $scope.getCategory();
    };
    $scope.selectCategory = function (cate) {
        $scope.selectedCategory = cate;
    };
    $scope.canAddCategory = function () {
        if ($scope.selectedCategory.name == null || !$scope.selectedCategory.name) {
            return false;
        }
        var list = $scope.data.product.insuranceCategoryIds;
        for (var i = 0; i < list.length; i++) {
            if ($scope.selectedCategory.id == list[i])return false;
        }
        return true;
    };
    $scope.deleteCategory = function (index) {
        $scope.productCategories.splice(index, 1);
        $scope.data.product.insuranceCategoryIds.splice(index, 1);
    };
    $scope.addProductCategory = function () {
        if (!$scope.canAddCategory())return false;
        $scope.productCategories.push($scope.selectedCategory);
        $scope.data.product.insuranceCategoryIds.push($scope.selectedCategory.id);
        $scope.categoryQuery.name = null;
        $scope.selectedCategory = {};
    };
    $scope.vendorQuery = {page: 1, limit: 99999, order: "", name: null};
    $scope.getVendor = function () {
        $scope.vendorList = [];
        $http.put("/admin/ajax/vendor/find", $scope.vendorQuery)
            .then(function (response) {
                $scope.vendorList = response.data.items;
            });
    };
    $scope.clearSearchVendor = function () {
        $scope.vendorQuery.name = null;
        $scope.getVendor();
    };
    $scope.serialQuery = {page: 1, limit: 99999, order: "", name: null};
    $scope.getSerial = function () {
        $http.put("/admin/ajax/product/serial/find", $scope.serialQuery)
            .then(function (response) {
                $scope.serialList = response.data.items;
            });
    };
    $scope.clearSearchSerial = function () {
        $scope.serialQuery.name = null;
        $scope.getSerial();
    };

    $scope.initProvinces = function () {
        $http.get("/admin/ajax/province")
            .then(function (response) {
                for (var i = 0; i < response.data.length; i++) {
                    var p = response.data[i];
                    p.show = false;
                    $scope.provinces.push(p);
                    $scope.productProvinces.push(angular.copy(p));
                    $scope.selectedProvinceIds.push($scope.provinces[i].id);
                    $scope.getCities(i);
                }
            });
    };
    $scope.getCities = function (i) {
        $http.get("/admin/ajax/province/" + $scope.provinces[i].id)
            .then(function (response) {
                $scope.provinces[i].cities = response.data;
                $scope.productProvinces[i].cities = response.data;
                for (var j = 0; j < response.data.length; j++) {
                    $scope.data.product.insuranceCityIds.push(response.data[j].id);
                }
            });
    };
    $scope.cityAllSelect = function (province) {
        if (!($scope.selectedProvinceIds.indexOf(province.id) > -1))return false;
        if (province.cities == null || !province.cities)return false;
        for (var i = 0; i < province.cities.length; i++) {
            if ($scope.data.product.insuranceCityIds.indexOf(province.cities[i].id) < 0)return false;
        }
        return true;
    };
    $scope.toggleProvince = function (province) {
        var index = $scope.selectedProvinceIds.indexOf(province.id);
        if (index > -1) {
            for (var i = 0; i < province.cities.length; i++) {
                $scope.removeCity(province.cities[i].id);
            }
            $scope.selectedProvinceIds.splice(index, 1);
            $scope.productProvinces.splice(index, 1);
        } else {
            for (var j = 0; j < province.cities.length; j++) {
                $scope.data.product.insuranceCityIds.push(province.cities[j].id);
            }
            $scope.productProvinces.push(province);
            $scope.selectedProvinceIds.push(province.id);
        }
    };
    $scope.removeCity = function (id) {
        var index = $scope.data.product.insuranceCityIds.indexOf(id);
        if (index > -1) {
            $scope.data.product.insuranceCityIds.splice(index, 1);
        }
    };
    $scope.existsCity = function (city) {
        return $scope.data.product.insuranceCityIds.indexOf(city.id) > -1;
    };
    $scope.toggleCity = function (city) {
        var index = $scope.data.product.insuranceCityIds.indexOf(city.id);
        if (index > -1) {
            $scope.data.product.insuranceCityIds.splice(index, 1);
        } else {
            $scope.data.product.insuranceCityIds.push(city.id);
        }
    };
    $scope.selectAllCity = function () {
        $scope.productProvinces = angular.copy($scope.provinces);
        for (var i = 0; i < $scope.provinces.length; i++) {
            var p = $scope.provinces[i];
            $scope.selectedProvinceIds.push(p.id);
            for (var j = 0; j < p.cities.length; j++) {
                $scope.data.product.insuranceCityIds.push(p.cities[j].id);
            }
        }
    };
    $scope.removeAllCity = function () {
        $scope.data.product.insuranceCityIds = [];
        $scope.productProvinces = [];
        $scope.selectedProvinceIds = [];
    };

    $scope.initFormGroups = function () {
        $http.put("/admin/ajax/form-group/find", {page: 1, limit: 999999})
            .then(function (response) {
                $scope.formGroups = response.data.items;
                if ($scope.formGroups != null && $scope.formGroups.length > 0) {
                    for (var i = 0; i < $scope.formGroups.length; i++) {
                        $scope.formGroups[i].show = true;
                        $scope.data.product.formGroups.push(angular.copy($scope.formGroups[i]));
                        $scope.data.product.formGroups[i].selectedFieldNames = [];
                        $scope.selectedFormGroups.push($scope.formGroups[i].name);
                        $scope.getFormField(i);
                    }
                }
            });
    };
    $scope.getFormField = function (i) {
        var g = $scope.formGroups[i];
        $http.put("/admin/ajax/form-field/find/" + g.id, {page: 1, limit: 999999})
            .then(function (response) {
                var fields = response.data.items;
                var list = [];
                var selectedFieldNames = [];
                for (var j = 0; j < fields.length; j++) {
                    if (fields[j].options == null) {
                        fields[j].options = {};
                    }
                    if (!fields[j].editable) fields[j].editable = true;
                    if (!fields[j].multiple) fields[j].multiple = false;
                    fields[j].pdp = false;
                    fields[j].pdpEditable = false;
                    fields[j].plp = false;
                    fields[j].plpEditable = false;
                    selectedFieldNames.push(fields[j].name);
                    var f = angular.copy(fields[j]);
                    list.push(f);
                }
                $scope.formGroups[i].fields = fields;
                $scope.data.product.formGroups[i].fields = list;
                $scope.data.product.formGroups[i].selectedFieldNames = angular.copy(selectedFieldNames);
            });
    };

    $scope.toast = function (message) {
        var toast = $mdToast.simple()
            .textContent(message)
            .position("right top")
            .hideDelay(2000);
        $mdToast.show(toast);
    };
    $scope.addNewQuestion = function () {
        var question = {question: "", answer: ""};
        $scope.data.product.questions.push(question);
    };
    $scope.deleteQuestion = function (index) {
        $scope.data.product.questions.splice(index, 1);
    };
    $scope.upQuestion = function (index) {
        var q = angular.copy($scope.data.product.questions[index]);
        $scope.data.product.questions[index] = $scope.data.product.questions[index - 1];
        $scope.data.product.questions[index - 1] = q;
    };
    $scope.downQuestion = function (index) {
        var q = angular.copy($scope.data.product.questions[index + 1]);
        $scope.data.product.questions[index + 1] = $scope.data.product.questions[index];
        $scope.data.product.questions[index] = q;
    };
    $scope.addNewCase = function () {
        $scope.data.product.cases[$scope.data.product.cases.length] = "";
    };
    $scope.deleteCase = function (index) {
        $scope.data.product.cases.splice(index, 1);
    };
    $scope.upCase = function (index) {
        var q = angular.copy($scope.data.product.cases[index]);
        $scope.data.product.cases[index] = $scope.data.product.cases[index - 1];
        $scope.data.product.cases[index - 1] = q;
    };
    $scope.downCase = function (index) {
        var q = angular.copy($scope.data.product.cases[index + 1]);
        $scope.data.product.cases[index + 1] = $scope.data.product.cases[index];
        $scope.data.product.cases[index] = q;
    };
    $scope.changePeriodType = function () {
        var type = $scope.data.product.period.type;
    };
    $scope.changePeriodInputUnit = function () {
        var unit = $scope.data.product.period.inputUnit;
        $scope.data.product.period.inputMin.unit = unit;
        $scope.data.product.period.inputMax.unit = unit;
    };
    $scope.changePeriodFixedValueUnit = function () {
        if ($scope.data.product.period.fixedValue.unit == 'LIFE') {
            $scope.data.product.period.fixedValue.value = 1;
        }
    };
    $scope.changePeriodValueUnit = function (period) {
        if (period.unit == 'LIFE') {
            period.value = 1;
        }
    };
    $scope.changeSelectPeriodUnit = function () {
        if ($scope.selectPeriod.unit == 'LIFE') {
            $scope.selectPeriod.value = 1;
        }
    };
    $scope.addPeriodSelection = function () {
        var a = angular.copy($scope.selectPeriod);
        if (a.displayName == null || !a.displayName) {
            alert("显示名称必填");
            return false;
        }
        if (a.value == null || !a.value) {
            alert("期间数值必填");
            return false;
        }
        var list = $scope.data.product.period.selections;
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].unit == a.unit && list[i].value == a.value) {
                    alert("期间已存在");
                    return false;
                }
            }
        }
        $scope.data.product.period.selections.push(a);
        $scope.selectPeriod = {unit: "DAY"};
    };
    $scope.deletePeriodSelection = function (index) {
        $scope.data.product.period.selections.splice(index, 1);
    };
    $scope.addPeriodAgeSelection = function () {
        var a = angular.copy($scope.selectAgePeriod);
        if (a.displayName == null || !a.displayName) {
            alert("显示名称必填");
            return false;
        }
        if (a.value == null || !a.value) {
            alert("期间数值必填");
            return false;
        }
        var list = $scope.data.product.period.ages;
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].unit == a.unit && list[i].value == a.value) {
                    alert("期间已存在");
                    return false;
                }
            }
        }
        $scope.data.product.period.ages.push(a);
        $scope.selectAgePeriod = {unit: "AGE"};
    };
    $scope.deletePeriodAgeSelection = function (index) {
        $scope.data.product.period.ages.splice(index, 1);
    };
    $scope.existsPayment = function (method, list) {
        return list.indexOf(method.value) > -1;
    };
    $scope.togglePaymentMethod = function (method, list) {
        var index = list.indexOf(method.value);
        if (index > -1) {
            list.splice(index, 1);
            switch (method.value) {
                case "FIXED":
                    $scope.data.product.payment.fixedPeriods = [];
                    break;
                case "IRREGULAR":
                    $scope.data.product.payment.irregularPeriods = [];
                    break;
                case "YEAR":
                    $scope.data.product.payment.yearPeriods = [];
                    break;
                case "AGE":
                    $scope.data.product.payment.agePeriods = [];
                    break;
                default :
                    break;
            }
        } else {
            list.push(method.value);
        }
    };
    $scope.addNewPaymentPeriod = function (unit, list) {
        var period = {displayName: null, unit: unit, value: 1};
        list.push(period);
    };
    $scope.deletePaymentPeriod = function (index, list) {
        list.splice(index, 1);
    };
    $scope.jobTreeQuery = {page: 1, limit: 99999, order: "", name: null};
    $scope.getJobTree = function () {
        $http.put("/admin/ajax/job-tree/find", $scope.jobTreeQuery)
            .then(function (response) {
                $scope.jobTrees = response.data.items;
            });
    };
    $scope.loadJobTree = function () {
        $scope.getJobTree();
    };
    $scope.selectJobTree = function (jobTree) {
        $scope.data.product.insuranceJobTreeId = jobTree.id;
        $scope.data.product.jobRestricted = false;
        $scope.data.product.jobLevels = [];
        $scope.data.product.jobIds = [];
        $http.get("/admin/ajax/job/tree/" + jobTree.id)
            .then(function (response) {
                $scope.jobTreeData = response.data;
                for (var i = 0; i < $scope.jobTreeData.length; i++) {
                    $scope.jobTreeData[i].show = false;
                    $scope.jobTreeData[i].loaded = false;
                    $scope.firstLevelIds.push($scope.jobTreeData[i].job.id);
                    for (var j = 0; j < $scope.jobTreeData[i].children.length; j++) {
                        $scope.jobTreeData[i].children[j].show = false;
                        $scope.jobTreeData[i].children[j].loaded = false;
                        $scope.secondLevelIds.push($scope.jobTreeData[i].children[j].job.id);
                        for (var k = 0; k < $scope.jobTreeData[i].children[j].children.length; k++) {
                            $scope.data.product.jobIds.push($scope.jobTreeData[i].children[j].children[k].job.id);
                            $scope.jobIds.push($scope.jobTreeData[i].children[j].children[k].job.id);
                            $scope.jobs.push($scope.jobTreeData[i].children[j].children[k].job);
                            var job = $scope.jobTreeData[i].children[j].children[k].job;
                            if ($scope.jobLevels.indexOf(job.riskLevel) < 0) {
                                $scope.addJobLevel(job.riskLevel);
                            }
                        }
                    }
                }
                $scope.data.product.jobLevels = angular.copy($scope.jobLevels);
            });
    };
    $scope.addJobLevel = function (level) {
        if ($scope.jobLevels.length == 0) {
            $scope.jobLevels.push(level);
        }
        if ($scope.jobLevels[0] > level) {
            $scope.jobLevels.splice(0, 0, level);
            return;
        }
        if ($scope.jobLevels[$scope.jobLevels.length - 1] < level) {
            $scope.jobLevels.splice($scope.jobLevels.length, 0, level);
            return;
        }
        for (var i = 0; i < $scope.jobLevels.length - 1; i++) {
            if ($scope.jobLevels[i] < level && level < $scope.jobLevels[i + 1]) {
                $scope.jobLevels.splice(i + 1, 0, level);
                return;
            }
        }
    };
    $scope.existsJobLevel = function (level, list) {
        return list.indexOf(level) > -1;
    };
    $scope.toggleJobLevel = function (level, list) {
        var index = list.indexOf(level);
        if (index > -1) {
            list.splice(index, 1);
        } else {
            list.push(level);
        }
        for (var i = 0; i < $scope.jobs.length; i++) {
            $scope.filterJob($scope.jobs[i]);
        }
    };
    $scope.filterJob = function (job) {
        if ($scope.data.product.jobLevels.indexOf(job.riskLevel) > -1) {
            var index = $scope.data.product.jobIds.indexOf(job.id);
            if (index < 0) {
                $scope.data.product.jobIds.push(job.id);
            }
        } else {
            var w = $scope.data.product.jobIds.indexOf(job.id);
            if (w > -1) {
                $scope.data.product.jobIds.splice(w, 1);
            }
        }
    };
    $scope.changeJobRestricted = function (restricted) {
        if (!restricted) {
            $scope.data.product.jobLevels = [];
            $scope.data.product.jobIds = [];
        } else {
            $scope.data.product.jobLevels = angular.copy($scope.jobLevels);
            $scope.data.product.jobIds = angular.copy($scope.jobIds);
        }
    };
    $scope.showJob = function (job) {
        if (!job.loaded) {
            job.loaded = true;
        }
        job.show = !job.show;
    };
    $scope.allSecondLevelSelect = function (firstLevel) {
        for (var i = 0; i < firstLevel.children.length; i++) {
            if (!$scope.allThirdLevelSelect(firstLevel.children[i]))return false;
        }
        return true;
    };
    $scope.allThirdLevelSelect = function (secondLevel) {
        for (var i = 0; i < secondLevel.children.length; i++) {
            if ($scope.data.product.jobLevels.indexOf(secondLevel.children[i].job.riskLevel) < 0)continue;
            if (!$scope.existsThirdLevel(secondLevel.children[i]))return false;
        }
        return true;
    };
    $scope.existsThirdLevel = function (thirdLevel) {
        return $scope.data.product.jobIds.indexOf(thirdLevel.job.id) > -1;
    };
    $scope.toggleThirdLevel = function (thirdLevel) {
        var index = $scope.data.product.jobIds.indexOf(thirdLevel.job.id);
        if (index > -1) {
            $scope.data.product.jobIds.splice(index, 1);
        } else {
            $scope.data.product.jobIds.push(thirdLevel.job.id);
        }
    };
    $scope.toggleSecondLevel = function (secondLevel) {
        var index = $scope.secondLevelIds.indexOf(secondLevel.job.id);
        if (index > -1) {
            for (var i = 0; i < secondLevel.children.length; i++) {
                $scope.removeThirdLevel(secondLevel.children[i]);
            }
            $scope.secondLevelIds.splice(index, 1);
        } else {
            for (var j = 0; j < secondLevel.children.length; j++) {
                if ($scope.data.product.jobLevels.indexOf(secondLevel.children[j].job.riskLevel) < 0)continue;
                if ($scope.data.product.jobIds.indexOf(secondLevel.children[j].job.id) < 0) {
                    $scope.data.product.jobIds.push(secondLevel.children[j].job.id);
                }
            }
            $scope.secondLevelIds.push(secondLevel.job.id);
        }
    };
    $scope.toggleFirstLevel = function (firstLevel) {
        var index = $scope.firstLevelIds.indexOf(firstLevel.job.id);
        if (index > -1) {
            for (var i = 0; i < firstLevel.children.length; i++) {
                $scope.removeSecondLevel(firstLevel.children[i]);
            }
            $scope.firstLevelIds.splice(index, 1);
        } else {
            for (var j = 0; j < firstLevel.children.length; j++) {
                var second = firstLevel.children[j];
                if ($scope.secondLevelIds.indexOf(second.job.id) < 0) {
                    $scope.secondLevelIds.push(firstLevel.children[j].job.id);
                }
                for (var k = 0; k < firstLevel.children[j].children.length; k++) {
                    var job = firstLevel.children[j].children[k].job;
                    if ($scope.data.product.jobLevels.indexOf(job.riskLevel) < 0)continue;
                    if ($scope.data.product.jobIds.indexOf(job.id) < 0) {
                        $scope.data.product.jobIds.push(job.id);
                    }
                }
            }
            $scope.firstLevelIds.push(firstLevel.job.id);
        }
    };
    $scope.removeSecondLevel = function (secondLevel) {
        for (var i = 0; i < secondLevel.children.length; i++) {
            $scope.removeThirdLevel(secondLevel.children[i]);
        }
        var index = $scope.secondLevelIds.indexOf(secondLevel.job.id);
        if (index > -1) {
            $scope.secondLevelIds.splice(index, 1);
        }
    };
    $scope.removeThirdLevel = function (thirdLevel) {
        var index = $scope.data.product.jobIds.indexOf(thirdLevel.job.id);
        if (index > -1) {
            $scope.data.product.jobIds.splice(index, 1);
        }
    };

    $scope.insuranceQuery = {page: 1, limit: 99999, order: "", name: null};
    $scope.getInsurances = function () {
        $http.put("/admin/ajax/insurance/find", $scope.insuranceQuery)
            .then(function (response) {
                $scope.insurances = response.data.items;
            });
    };
    $scope.loadInsurance = function () {
        $scope.getInsurances();
    };
    $scope.selectInsurance = function (insurance) {
        $scope.selectedInsurance = angular.copy(insurance);
        $scope.selectedInsurance.insuranceId = insurance.id;
    };
    $scope.addInsurance = function () {
        if (!$scope.canAddInsurance()) {
            return false;
        }
        if ($scope.data.product.insurances.length == 0) {
            $scope.data.product.formGroups.push({
                name: "liability",
                displayName: "保险责任",
                fields: []
            });
        }
        $scope.insuranceIds.push($scope.selectedInsurance.id);
        $scope.selectedInsurance.optional = true;
        $scope.selectedInsurance.insurnaceId = $scope.selectedInsurance.id;
        $scope.data.product.insurances.push($scope.selectedInsurance);
        $scope.getLiabilityNames($scope.data.product.insurances.length - 1);
        $scope.insuranceQuery.name = null;
        $scope.selectedInsurance = null;
    };

    $scope.getLiabilityNames = function (index) {
        var insurance = $scope.data.product.insurances[index];
        for (var i = 0; i < insurance.liabilities.length; i++) {
            $scope.getLiabilityName(insurance.liabilities[i].insuranceLiabilityId, index, i);
        }
    };
    $scope.getLiabilityName = function (id, index, i) {
        $http.get("/admin/ajax/insurance/liability/" + id)
            .then(function (response) {
                var l = response.data;
                $scope.data.product.insurances[index].liabilities[i].name = l.name;
                if ($scope.liabilityGroupFieldNames.indexOf(l.id) < 0) {
                    $scope.liabilityGroupFieldNames.push(l.id);
                    var field = {
                        name: l.id,
                        displayName: l.name,
                        editable: false,
                        options: {}
                    };
                    for (var k = 0; k < $scope.data.product.formGroups.length; k++) {
                        var g = $scope.data.product.formGroups[k];
                        if (g.name == "liability") {
                            g.fields.push(field);
                        }
                    }
                }
            });
    };
    $scope.deleteInsurance = function (index) {
        $scope.insuranceIds.splice(index, 1);
        $scope.data.product.insurances.splice(index, 1);
        var list = [];
        for (var i = 0; i < $scope.data.product.insurances.length; i++) {
            var ins = $scope.data.product.insurances[i];
            var liability = ins.liabilities;
            for (var j = 0; j < liability.length; j++) {
                list.push(liability[j].insuranceLiabilityId);
            }
        }
        var plp = angular.copy($scope.data.product.plp);
        var newPlp = [];
        for (i = 0; i < plp.length; i++) {
            var f = plp[i];
            if (f.group != 'liability') {
                newPlp.push(f);
                continue;
            }
            if (list.indexOf(f.field) > -1) {
                newPlp.push(f);
            }
        }
        $scope.data.product.plp = angular.copy(newPlp);
        var pdp = angular.copy($scope.data.product.pdp);
        var newPdp = [];
        for (i = 0; i < pdp.length; i++) {
            f = pdp[i];
            if (f.group != 'liability') {
                newPdp.push(f);
                continue;
            }
            if (list.indexOf(f.field) > -1) {
                newPdp.push(f);
            }
        }
        $scope.data.product.pdp = angular.copy(newPdp);
        var fg = $scope.data.product.formGroups;
        if ($scope.data.product.insurances.length == 0) {
            for (j = 0; j < fg.length; j++) {
                if (fg[j].name == 'liability') {
                    $scope.data.product.formGroups.splice(j, 1);
                    break;
                }
            }
        } else {
            for (j = 0; j < fg.length; j++) {
                if (fg[j].name == 'liability') {
                    var oldFields = $scope.data.product.formGroups[j].fields;
                    var newFields = [];
                    for (var q = 0; q < oldFields.length; q++) {
                        if (list.indexOf(oldFields[q].name) > -1) {
                            newFields.push(oldFields[q]);
                        }
                    }
                    $scope.data.product.formGroups[j].fields = newFields;
                }
            }
        }
    };
    $scope.canAddInsurance = function () {
        if ($scope.selectedInsurance == null || $scope.selectedInsurance.name == null || !$scope.selectedInsurance.name) {
            return false;
        }
        return !($scope.insuranceIds.indexOf($scope.selectedInsurance.insuranceId) > -1);
    };
    $scope.changeLiabilityType = function (amount) {
        switch (amount.type) {
            case "USER_INPUT":
                amount.inputMax = null;
                amount.inputMin = null;
                amount.inputIncrementUnit = null;
                break;
            case "USER_SELECTION":
                amount.selections = [];
                break;
            case "UNIT_SELECTION":
                amount.maxUnits = null;
                amount.minUnits = null;
                break;
            case "FORMULA":
                amount.formulaName = null;
                break;
            case "FIXED":
                amount.fixedValue = null;
                break;
            default:
                return;
        }
    };
    $scope.selectDividendType = function (type) {
        $scope.selectedDividendType = type;
    };
    $scope.addDividendType = function () {
        if (!$scope.canAddDividendType()) {
            return false;
        }
        $scope.data.product.insuranceDividendTypes.push($scope.selectedDividendType);
        $scope.selectedDividendType = null;
    };
    $scope.canAddDividendType = function () {
        if ($scope.selectedDividendType == null || !$scope.selectedDividendType) {
            return false;
        }
        var list = $scope.data.product.insuranceDividendTypes;
        if (list.length > 0)return false;
        return !(list.indexOf($scope.selectedDividendType) > -1);
    };
    $scope.deleteDividendType = function (index) {
        $scope.data.product.insuranceDividendTypes.splice(index, 1);
    };
    $scope.initAreas = function () {
        $http.get("/admin/ajax/area")
            .then(function (response) {
                $scope.areas = response.data;
                for (var i = 0; i < $scope.areas.length; i++) {
                    $scope.data.product.insuranceAreaIds.push($scope.areas[i].id);
                }
            });
    };
    $scope.existsArea = function (area) {
        return $scope.data.product.insuranceAreaIds.indexOf(area.id) > -1;
    };
    $scope.toggleArea = function (area) {
        var index = $scope.data.product.insuranceAreaIds.indexOf(area.id);
        if (index > -1) {
            $scope.data.product.insuranceAreaIds.splice(index, 1);
        } else {
            $scope.data.product.insuranceAreaIds.push(area.id);
        }
    };
    $scope.selectAllArea = function () {
        for (var i = 0; i < $scope.areas.length; i++) {
            $scope.data.product.insuranceAreaIds.push($scope.areas[i].id);
        }
    };
    $scope.removeAllArea = function () {
        $scope.data.product.insuranceAreaIds = [];
    };
    $scope.uploadPriceTableUrl = function (files) {
        var url = "/admin/ajax/file/upload";
        if (files && files.length) {
            var f = files[0];
            Upload.upload({
                url: url,
                data: {file: f}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.product.priceTableUrl = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {
            });
        }
    };
    $scope.uploadCustomerNotificationUrl = function (files) {
        var url = "/admin/ajax/file/upload";
        if (files && files.length) {
            var f = files[0];
            Upload.upload({
                url: url,
                data: {file: f}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.product.customerNotificationUrl = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {
            });
        }
    };
    $scope.uploadAgreeUrl = function (files) {
        var url = "/admin/ajax/file/upload";
        if (files && files.length) {
            var f = files[0];
            Upload.upload({
                url: url,
                data: {file: f}
            }).then(function (response) {
                $scope.toast("上传成功");
                $scope.data.product.agreeUrls[0] = {};
                $scope.data.product.agreeUrls[0].name = "投保声明";
                $scope.data.product.agreeUrls[0].url = response.data.path;
            }, function (response) {
                console.log("error status:" + response.status);
            }, function (evt) {
            });
        }
    };
    $scope.canAddAgreeUrl = function () {
        return ($scope.agree.name != null && $scope.agree.url != null);
    };
    $scope.addAgreeUrl = function () {
        if (!$scope.canAddAgreeUrl())return false;
        $scope.data.product.agreeUrls.push($scope.agree);
        $scope.agree = {name: null, url: null};
    };
    $scope.deleteAgreeUrl = function (index) {
        $scope.data.product.agreeUrls.splice(index, 1);
    };
    $scope.insuranceClauseQuery = {page: 1, limit: 999999, name: null};
    $scope.loadInsuranceClause = function () {
        $http.put("/admin/ajax/clause/find", $scope.insuranceClauseQuery)
            .then(function (response) {
                $scope.insuranceClauses = response.data.items;
            });
    };
    $scope.selectInsuranceClause = function (insuranceClause) {
        $scope.selectedInsuranceClause = insuranceClause;
    };
    $scope.canAddInsuranceClause = function () {
        if ($scope.selectedInsuranceClause == null || !$scope.selectedInsuranceClause.name) {
            return false;
        }
        return !($scope.data.product.insuranceClauseIds.indexOf($scope.selectedInsuranceClause.id) > -1);
    };
    $scope.addInsuranceClause = function () {
        if (!$scope.canAddInsuranceClause())return false;
        $scope.data.product.insuranceClauseIds.push($scope.selectedInsuranceClause.id);
        $scope.productClauses.push($scope.selectedInsuranceClause);
        $scope.selectedInsuranceClause = null;
    };
    $scope.deleteInsuranceClause = function (index) {
        $scope.data.product.insuranceClauseIds.splice(index, 1);
        $scope.productClauses.splice(index, 1);
    };
    $scope.insuranceClaimQuery = {page: 1, limit: 999999, title: null};
    $scope.loadInsuranceClaim = function () {
        $http.put("/admin/ajax/insurance/claim/find", $scope.insuranceClaimQuery)
            .then(function (response) {
                $scope.insuranceClaims = response.data.items;
            });
    };
    $scope.selectInsuranceClaim = function (insuranceClaim) {
        $scope.selectedInsuranceClaim = insuranceClaim;
    };
    $scope.canAddInsuranceClaim = function () {
        if ($scope.selectedInsuranceClaim == null || !$scope.selectedInsuranceClaim.name) {
            return false;
        }
        return !($scope.data.product.insuranceClaimIds.indexOf($scope.selectedInsuranceClaim.id) > -1);
    };
    $scope.addInsuranceClaim = function () {
        if (!$scope.canAddInsuranceClaim())return false;
        $scope.data.product.insuranceClaimIds.push($scope.selectedInsuranceClaim.id);
        $scope.productClaims.push($scope.selectedInsuranceClaim);
        $scope.selectedInsuranceClaim = null;
    };
    $scope.deleteInsuranceClaim = function (index) {
        $scope.data.product.insuranceClaimIds.splice(index, 1);
        $scope.productClaims.splice(index, 1);
    };


    $scope.goTop = function () {
        $location.hash("productTop");
        $anchorScroll();
    };
    $scope.existsGroup = function (groupName) {
        return $scope.selectedFormGroups.indexOf(groupName) > -1;
    };
    $scope.toggleGroup = function (group) {
        var index = $scope.selectedFormGroups.indexOf(group.name);
        if (index > -1) {
            $scope.data.product.formGroups.splice(index, 1);
            $scope.selectedFormGroups.splice(index, 1);
            $scope.selectedElement = null;
        } else {
            var g = angular.copy(group);
            g.selectedFieldNames = [];
            for (var i = 0; i < group.fields.length; i++) {
                g.selectedFieldNames.push(group.fields[i].name);
            }
            $scope.data.product.formGroups.push(g);
            $scope.selectedFormGroups.push(g.name);
            $scope.selectGroup(g);
        }
    };
    $scope.existsField = function (fieldName, groupName) {
        if (!$scope.existsGroup(groupName))return false;
        var g = $scope.getGroupIndexByName(groupName);
        return $scope.data.product.formGroups[g].selectedFieldNames.indexOf(fieldName) > -1;
    };
    $scope.getGroupIndexByName = function (name) {
        for (var i = 0; i < $scope.data.product.formGroups.length; i++) {
            if ($scope.data.product.formGroups[i].name == name)return i;
        }
        return -1;
    };
    $scope.getFieldIndexByName = function (gIndex, name) {
        var fields = $scope.data.product.formGroups[gIndex].fields;
        for (var i = 0; i < fields.length; i++) {
            if (fields[i].name == name)return i;
        }
        return -1;
    };
    $scope.toggleField = function (field, group) {
        if (!$scope.existsGroup(group.name)) {
            var g = angular.copy(group);
            g.selectedFieldNames = [];
            $scope.data.product.formGroups.push(g);
            $scope.selectedFormGroups.push(g.name);
        }
        var gIndex = $scope.getGroupIndexByName(group.name);
        var index = $scope.data.product.formGroups[gIndex].selectedFieldNames.indexOf(field.name);
        if (index > -1) {
            $scope.data.product.formGroups[gIndex].fields.splice(index, 1);
            $scope.data.product.formGroups[gIndex].selectedFieldNames.splice(index, 1);
            if ($scope.data.product.formGroups[gIndex].selectedFieldNames.length == 0) {
                $scope.data.product.formGroups.splice(gIndex, 1);
                $scope.selectedFormGroups.splice(gIndex, 1);
            }
            $scope.selectedElement = null;
        } else {
            $scope.data.product.formGroups[gIndex].fields.push(field);
            $scope.data.product.formGroups[gIndex].selectedFieldNames.push(field.name);
            $scope.selectField(group, field);
        }
    };
    $scope.selectGroup = function (group) {
        $scope.selectedElement = {};
        $scope.selectedElement.type = "group";
        $scope.selectedElement.data = group;
        $scope.selectedElement.gIndex = $scope.getGroupIndexByName(group.name);
    };
    $scope.selectField = function (group, field) {
        var gIndex = $scope.getGroupIndexByName(group.name);
        if (gIndex == -1) {
            return;
        }
        var fIndex = $scope.getFieldIndexByName(gIndex, field.name);
        if (fIndex == -1) {
            return;
        }
        $scope.selectedElement = {};
        $scope.selectedElement.type = "field";
        $scope.selectedElement.data = field;
        $scope.selectedElement.gIndex = gIndex;
        $scope.selectedElement.fIndex = fIndex;
        $http.get("/admin/ajax/enum-type/type/" + field.type + "/values")
            .then(function (response) {
                if (response.data.length == 0)return;
                $scope.selectedElement.data.options.constants = [];
                for (var i = 0; i < response.data.length; i++) {
                    var c = {displayName: response.data[i].name, value: response.data[i].value};
                    $scope.selectedElement.data.options.constants.push(c);
                }
                if ($scope.data.product.formGroups[gIndex].fields[fIndex].options.constants == null || $scope.data.product.formGroups[gIndex].fields[fIndex].options.constants.length <= 0) {
                    $scope.data.product.formGroups[gIndex].fields[fIndex].options.constants = angular.copy($scope.selectedElement.data.options.constants);
                }
            });
    };
    $scope.existsConstant = function (constant) {
        var constants = $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants;
        if (!constants)return false;
        for (var i = 0; i < constants.length; i++) {
            if (constants[i].value == constant.value)return true;
        }
        return false;
    };
    $scope.isAllConstantsChecked = function () {
        var constants = $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants;
        return constants && $scope.selectedElement.data.options.constants && constants.length === $scope.selectedElement.data.options.constants.length;
    };
    $scope.isConstantsIndeterminate = function () {
        var constants = $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants;
        return constants && constants.length != 0 && $scope.selectedElement.data.options.constants && constants.length != $scope.selectedElement.data.options.constants.length;
    };
    $scope.toggleAllConstants = function () {
        var constants = $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants;
        if (constants.length === $scope.selectedElement.data.options.constants.length) {
            $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants = [];
        } else if (constants.length === 0 || constants.length > 0) {
            $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants = angular.copy($scope.selectedElement.data.options.constants);
        }
    };
    $scope.toggleConstant = function (constant) {
        var constants = $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants;
        var idx = -1;
        for (var i = 0; i < constants.length; i++) {
            if (constants[i].value == constant.value) {
                idx = i;
                break;
            }
        }
        if (idx > -1) {
            $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants.splice(idx, 1);
        } else {
            $scope.data.product.formGroups[$scope.selectedElement.gIndex].fields[$scope.selectedElement.fIndex].options.constants.push(constant);
        }
    };

    $scope.check = function (product) {
        if (!product.displayName) {
            $scope.toast("产品名称必填");
            return false;
        }
        if (product.displayOrder == null) {
            $scope.toast("排序必填");
            return false;
        }
        if (product.insuranceVendorId == null) {
            $scope.toast("供应商必填");
            return false;
        }
        if (product.maxUnits == null || !product.maxUnits) {
            $scope.toast("最大购买份数必填");
            return false;
        }
        if (product.minUnits == null || !product.minUnits) {
            $scope.toast("最小购买份数必填");
            return false;
        }
        if (product.maxUnits < product.minUnits) {
            $scope.toast("最大份数必须大于最小份数");
            return false;
        }
        if (product.planCode == null || !product.planCode) {
            $scope.toast("保险计划代码必填");
            return false;
        }
        if (product.discount == null || !product.discount) {
            return false;
        }
        if (product.planCode == null || !product.planCode) {
            return false;
        }
        if (product.period.type == 'FIXED') {
            var fix = product.period.fixedValue;
            if ((fix.displayName == null || !fix.displayName) || fix.value == null || !fix.value) {
                $scope.toast("固定期限值必填");
                return false;
            }
        } else if (product.period.type == 'USER_INPUT') {
            var inputMin = product.period.inputMin;
            var inputMax = product.period.inputMax;
            if (inputMin.displayName == null || !inputMin.displayName || inputMin.value == null || !inputMin.value) {
                $scope.toast("最小输入值必填");
                return false;
            }
            if (inputMax.displayName == null || !inputMax.displayName || inputMax.value == null || !inputMax.value) {
                $scope.toast("最大输入值必填");
                return false;
            }
            if (inputMax < inputMin) {
                $scope.toast("最大值必须大于最小值");
                return false;
            }
        } else if (product.period.type == 'USER_SELECTION') {
            var s = product.period.selections;
            if (s == null || s.length < 1) {
                $scope.toast("至少要有一个保障期间选项");
                return false;
            }
        } else if (product.period.type == 'AGE_DURATION') {
            var a = product.period.ages;
            if (a == null || a.length < 1) {
                $scope.toast("至少要有一个年龄选项");
                return false;
            }
        }
        if (product.period.startTimeType == 'LATEST') {
            var e = product.period.earliestInsuranceTime;
            if (e.displayName == null || !e.displayName || e.value == null || !e.value) {
                $scope.toast("起始期间必填");
                return false;
            }
        }
        if (product.payment.methods == null || product.payment.methods.length < 1) {
            $scope.toast("缴费期间必填");
            return false;
        } else {
            var p = product.payment;
            var m = product.payment.methods;
            if (m.indexOf('YEAR') > -1 && (p.yearPeriods == null || p.yearPeriods.length < 1)) {
                $scope.toast("按年缴费期间必填");
                return false;
            }
            if (m.indexOf('AGE') > -1 && (p.agePeriods == null || p.agePeriods.length < 1)) {
                $scope.toast("按年龄缴费期间必填");
                return false;
            }
        }
        if (product.policyHolderMaxAge == null) {
            $scope.toast("最大投保人年龄所有字段必填");
            return false;
        } else {
            if (product.policyHolderMaxAge.displayName == null || !product.policyHolderMaxAge.displayName) {
                $scope.toast("最大投保人年龄所有字段必填");
                return false;
            }
            if (product.policyHolderMaxAge.value == null || !product.policyHolderMaxAge.value) {
                $scope.toast("最大投保人年龄所有字段必填");
                return false;
            }
            if (product.policyHolderMaxAge.unit == null || !product.policyHolderMaxAge.unit) {
                $scope.toast("最大投保人年龄所有字段必填");
                return false;
            }
        }
        if (product.policyHolderMinAge == null) {
            $scope.toast("最小投保人年龄所有字段必填");
            return false;
        } else {
            if (product.policyHolderMinAge.displayName == null || !product.policyHolderMinAge.displayName) {
                $scope.toast("最小投保人年龄所有字段必填");
                return false;
            }
            if (product.policyHolderMinAge.value == null || !product.policyHolderMinAge.value) {
                $scope.toast("最小投保人年龄所有字段必填");
                return false;
            }
            if (product.policyHolderMinAge.unit == null || !product.policyHolderMinAge.unit) {
                $scope.toast("最小投保人年龄所有字段必填");
                return false;
            }
        }
        if (product.insuredMaxAge == null) {
            $scope.toast("最大被保人年龄所有字段必填");
            return false;
        } else {
            if (product.insuredMaxAge.displayName == null || !product.insuredMaxAge.displayName) {
                $scope.toast("最大被保人年龄所有字段必填");
                return false;
            }
            if (product.insuredMaxAge.value == null || !product.insuredMaxAge.value) {
                $scope.toast("最大被保人年龄所有字段必填");
                return false;
            }
            if (product.insuredMaxAge.unit == null || !product.insuredMaxAge.unit) {
                $scope.toast("最大被保人年龄所有字段必填");
                return false;
            }
        }
        if (product.insuredMinAge == null) {
            $scope.toast("最小被保人年龄所有字段必填");
            return false;
        } else {
            if (product.insuredMinAge.displayName == null || !product.insuredMinAge.displayName) {
                $scope.toast("最小被保人年龄所有字段必填");
                return false;
            }
            if (product.insuredMinAge.value == null || !product.insuredMinAge.value) {
                $scope.toast("最小被保人年龄所有字段必填");
                return false;
            }
            if (product.insuredMinAge.unit == null || !product.insuredMinAge.unit) {
                $scope.toast("最小被保人年龄所有字段必填");
                return false;
            }
        }
        return true;
    };
    $scope.save = function () {
        if (!$scope.check($scope.data.product)) {
            return false;
        }
        var min = 99;
        var max = 0;
        var g;
        var j;
        var f;
        for (var i = 0; i < $scope.jobs.length; i++) {
            var job = $scope.jobs[i];
            if ($scope.data.product.jobIds.indexOf(job.id) > -1) {
                if (job.riskLevel < min) {
                    min = job.riskLevel;
                }
                if (job.riskLevel > max) {
                    max = job.riskLevel;
                }
            }
        }
        $scope.data.product.maxJobLevel = max;
        $scope.data.product.minJobLevel = min;
        for (i = 0; i < $scope.data.product.formGroups.length; i++) {
            g = $scope.data.product.formGroups[i];
            if (g.name == 'liability')continue;
            for (j = 0; j < g.fields.length; j++) {
                f = g.fields[j];
                if (f.pdp) {
                    $scope.data.product.pdp.push({
                        group: g.name, field: f.name, editable: f.pdpEditable
                    });
                }
            }
        }
        for (i = 0; i < $scope.data.product.insurances.length; i++) {
            var insurance = $scope.data.product.insurances[i];
            var liabilityList = [];
            for (j = 0; j < insurance.liabilities.length; j++) {
                var liability = insurance.liabilities[j];
                if (liabilityList.indexOf(liability.insuranceLiabilityId) > -1)continue;
                $scope.data.product.pdp.push({
                    group: 'liability', field: liability.insuranceLiabilityId
                });
                liabilityList.push(liability.insuranceLiabilityId);
            }
        }
        for (i = 0; i < $scope.data.product.formGroups.length; i++) {
            g = $scope.data.product.formGroups[i];
            if (g.name == 'liability')continue;
            for (j = 0; j < g.fields.length; j++) {
                f = g.fields[j];
                if (f.plp) {
                    $scope.data.product.plp.push({
                        group: g.name, field: f.name, editable: f.plpEditable
                    });
                }
            }
        }

        var req = {};
        req.product = $scope.data.product;
        req.claimRequest = {};
        $http.post("/admin/ajax/product/audit", req)
            .then(function (response) {
                $scope.toast("提交创建请求成功");
            });
    };
    $scope.init();
};
