'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, $timeout, $mdDialog) {
    $scope.messages = [];
    $scope.selected = [];

    $scope.query = {
        page: 1,
        limit: 20,
    };

    $scope.load = function() {
        $scope.promise = $http.put('/admin/ajax/message/find', $scope.query);
        $scope.promise.then(function(response) {
            $scope.messages = response.data;
        });
    };

    $scope.delete = function(message) {
        var confirm = $mdDialog.confirm()
            .title('Delete message ' + message.content)
            .ariaLabel('Delete Message')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function() {
            $http.delete('/admin/ajax/message/' + message.id).then(function() {
                for (var i = 0; i < $scope.messages.items.length; i++) {
                    if (message.id === $scope.messages.items[i].id) {
                        $scope.messages.items.splice(i, 1);
                        break;
                    }
                }
            });
        });
    };

    $scope.batchDelete = function(selected) {
        var confirm = $mdDialog.confirm()
            .title('Delete ' + selected.length + ' messages?')
            .ariaLabel('Delete Message')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function() {
            var batchDeleteRequest = {
                ids: []
            };
            for (var i = 0; i < selected.length; i++) {
                batchDeleteRequest.ids.push(selected[i].id);
            }

            $http.post('/admin/ajax/message/batch-delete', batchDeleteRequest).then(function() {
                $scope.selected = [];
                for (var j = 0; j < selected.length; j++) {
                    for (var i = 0; i < $scope.messages.items.length; i++) {
                        if (selected[j].id === $scope.messages.items[i].id) {
                            $scope.messages.items.splice(i, 1);
                            break;
                        }
                    }
                }
            });
        });
    };

    $scope.read = function(message) {
        $http.post('/admin/ajax/message/' + message.id + '/read').then(function() {
            message.status = 'READ';
        });
    };

    $scope.batchRead = function(selected) {
        var batchReadRequest = {
            ids: []
        };
        for (var i = 0; i < selected.length; i++) {
            batchReadRequest.ids.push(selected[i].id);
        }
        $http.post('/admin/ajax/message/batch-read', batchReadRequest).then(function() {
            for (var i = 0; i < selected.length; i++) {
                selected[i].status = 'READ';
            }
        });
    };

    $scope.options = {
        autoSelect: true,
        boundaryLinks: false,
        largeEditDialog: false,
        pageSelector: false,
        rowSelection: true,
        index: function() {
            if ($scope.messages.limit) {
                return $scope.messages.page;
            }
            return 0;
        },
        total: function() {
            if ($scope.messages.limit) {
                return $scope.messages.total % $scope.messages.limit === 0 ? parseInt($scope.messages.total / $scope.messages.limit) : parseInt($scope.messages.total / $scope.messages.limit) + 1;
            }
            return 0;
        }
    };

    $scope.load();
};