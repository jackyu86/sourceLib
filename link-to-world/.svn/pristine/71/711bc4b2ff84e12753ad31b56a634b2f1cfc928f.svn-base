'use strict';

var angular = require('angular');
module.exports = function ($scope, $rootScope, $http, $stateParams, $state, $mdToast) {
    $scope.selectedCategory = {};
    $scope.selectedProduct = [];
    $scope.categoryList = [];
    $scope.surrenderMarks = {};
    $scope.bankAccountMarks = {};
    $scope.rates = {};
    $scope.tree = {};
    $scope.processor = {
        buildCategory: function (categoryList) {
            var flag = false;
            categoryList.forEach(function (category) {
                category.show = false;
                if (category.productList) {
                    category.productList.forEach(function (product) {
                        $scope.surrenderMarks[product.name] = product.surrenderMark;
                        $scope.bankAccountMarks[product.name] = product.bankAccountMark;
                        $scope.rates[product.name] = product.rate;
                        if (product.checked) {
                            this.addProduct(product);
                        }
                        category.show = true;
                        flag = true;
                    }.bind(this));
                }
                if (category.children) {
                    var result = this.buildCategory(category.children);
                    if (result) {
                        category.show = true;
                        flag = true;
                    }
                }
            }.bind(this));
            return flag;
        },
        addCategory: function (category) {
            if (category.productList) {
                category.productList.forEach(function (product) {
                    this.addProduct(product);
                }.bind(this));
            }
            if (category.children) {
                category.children.forEach(function (subCategory) {
                    this.addCategory(subCategory);
                    if (subCategory.show) {
                    }
                }.bind(this));
            }
        },
        removeCategory: function (category) {
            category.productList.forEach(function (product) {
                this.removeProduct(product);
            }.bind(this));
        },
        addProduct: function (product) {
            if ($scope.selectedProduct.indexOf(product.name) >= 0) {
                return;
            }
            $scope.selectedProduct.push(product.name);
            product.categoryList.forEach(function (category) {
                if ($scope.selectedCategory[category]) {
                    $scope.selectedCategory[category].push(product.name);
                } else {
                    $scope.selectedCategory[category] = [product.name];
                }
            })
        },
        removeProduct: function (product) {
            $scope.selectedProduct.splice($scope.selectedProduct.indexOf(product.name), 1);
            product.categoryList.forEach(function (category) {
                if ($scope.selectedCategory[category]) {
                    $scope.selectedCategory[category].splice($scope.selectedCategory[category].indexOf(product.name), 1);
                }
                if ($scope.selectedCategory[category] && $scope.selectedCategory[category].length == 0) {
                    $scope.selectedCategory[category] = undefined;
                }
            })

        }
    };
    $scope.isProductSelected = function (product) {
        return $scope.selectedProduct.indexOf(product.name) >= 0;
    };
    $scope.isCategorySelected = function (category) {
        return $scope.selectedCategory[category.id];
    };
    $scope.checkProduct = function (product) {
        if ($scope.selectedProduct.indexOf(product.name) >= 0) {
            $scope.processor.removeProduct(product);
        } else {
            $scope.processor.addProduct(product);
        }
    };

    $scope.checkCategory = function (category) {
        console.log(category);
        if (!$scope.selectedCategory[category.category.id]) {
            $scope.processor.addCategory(category);
        } else {
            $scope.processor.removeCategory(category);
        }
    };

    $scope.save = function () {
        var productList = [];
        $scope.selectedProduct.forEach(function (productName) {
            var product = {};
            product.productName = productName;
            product.surrenderMark = $scope.surrenderMarks[productName];
            product.bankAccountMark = $scope.bankAccountMarks[productName];
            product.rate = $scope.rates[productName];
            productList.push(product);
        });
        $http.put("/admin/ajax/dealer/" + $stateParams.id + "/products", {productList: productList})
            .then(function () {
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Saved!')
                        .position('top right')
                        .hideDelay(3000)
                );
            }, function (e) {
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Save failed')
                        .position('top right')
                        .highlightClass('md-accent')
                        .hideDelay(3000)
                );
                console.log(e);
            });
    };

    $http.get("/admin/ajax/dealer/" + $stateParams.id + "/products").then(function (response) {
        $scope.categoryList = response.data;
        $scope.processor.buildCategory($scope.categoryList);
        console.log($scope.selectedProduct);
        console.log($scope.selectedCategory);
    }.bind(this), function () {
        $(".form-error").show();
    });

    $scope.goBack = function () {
        history.back();
    }

};