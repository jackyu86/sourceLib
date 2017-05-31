'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdDialog) {
    $scope.data = {
        dealerLevel: '-1',
        listingDealer: []
    };
    $scope.orderTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            order: 'id',
            page: 1,
            limit: 10,
            paymentMethod: 'OFFLINE',
            status: 'PAYMENT_PENDING'
        },
        data: {},
        selected: [],
        pay: function (id) {
            var confirm = $mdDialog.confirm()
                .title('订单 ' + id + " 状态 -》 【已支付】？")
                .theme('white')
                .targetEvent(event)
                .ok('确认')
                .cancel('取消');

            $mdDialog.show(confirm).then(function () {
                $http.put("/admin/ajax/order/" + id + "/status", {status: "PAYMENT_COMPLETED"}).then(function () {
                    $scope.reloadOrders();
                })

            });
        }
    };

    $scope.detail = function (order) {
        $state.go("console.order.detail", {orderId: order.id});
    };

    $scope.reloadOrders = function () {
        if ($scope.filter.role === "policy-holder") {
            $scope.orderTable.query.policyHolderName = $scope.filter.userInfo.name;
            $scope.orderTable.query.policyHolderIdType = $scope.filter.userInfo.idType;
            $scope.orderTable.query.policyHolderIdNumber = $scope.filter.userInfo.idNumber;
            $scope.orderTable.query.policyHolderPhone = $scope.filter.userInfo.phone;
            $scope.orderTable.query.policyHolderPhone = $scope.filter.userInfo.email;
        } else {
            $scope.orderTable.query.insuredName = $scope.filter.userInfo.name;
            $scope.orderTable.query.insuredIdType = $scope.filter.userInfo.idType;
            $scope.orderTable.query.insuredIdNumber = $scope.filter.userInfo.idNumber;
            $scope.orderTable.query.insuredPhone = $scope.filter.userInfo.phone;
            $scope.orderTable.query.insuredEmail = $scope.filter.userInfo.email;
        }

        $http.put('/admin/ajax/order/find', $scope.orderTable.query).then(function (response) {
            $scope.orderTable.data = response.data;
            $scope.orderTable.selected = [];
        });
    };

    $scope.filter = {
        categories: [],
        categoryId: "-1",
        products: [],
        userInfo: {},
        init: function () {
            this.initCategory();
            this.initUserInfo();
        },
        initCategory: function () {
            $http.get("/admin/ajax/insurance/category")
                .then(function (response) {
                    this.categories = response.data;
                }.bind(this));
            $scope.$watch('filter.categoryId', function () {
                if (this.categoryId === "-1") {
                    this.products = [];
                    $scope.orderTable.query.productName = null;
                } else {
                    $http.get('/admin/ajax/product/findByCategory?categoryId=' + this.categoryId).then(function (response) {
                        this.products = response.data.items;
                    }.bind(this));
                }
            }.bind(this), false);
        },
        initUserInfo: function () {
            $scope.$watch('filter.role', function () {
                if (this.role === "policy-holder") {
                    $scope.orderTable.query.insuredName = null;
                    $scope.orderTable.query.insuredIdType = null;
                    $scope.orderTable.query.insuredIdNumber = null;
                    $scope.orderTable.query.insuredPhone = null;
                    $scope.orderTable.query.insuredEmail = null;
                } else {
                    $scope.orderTable.query.policyHolderName = null;
                    $scope.orderTable.query.policyHolderIdType = null;
                    $scope.orderTable.query.policyHolderIdNumber = null;
                    $scope.orderTable.query.policyHolderPhone = null;
                    $scope.orderTable.query.policyHolderPhone = null;
                }

            }.bind(this), false);
        }
    };


    $scope.reloadOrders();

    $scope.$watch('data.dealerLevel', function () {
        if ($scope.data.dealerLevel === "-1") {
            $scope.data.listingDealer = [];
            $scope.orderTable.query.dealerId = null;
        } else {
            $http.put('/admin/ajax/dealer/find', {
                page: 1,
                limit: 65535,
                order: 'name',
                level: $scope.data.dealerLevel
            }).then(function (response) {
                $scope.data.listingDealer = response.data.items;
            });
        }
    }, false);


    $scope.filter.init();

};