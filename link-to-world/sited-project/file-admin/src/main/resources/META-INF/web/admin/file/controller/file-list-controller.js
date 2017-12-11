'use strict';

var angular = require('angular');

module.exports = function ($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog) {
    $scope.fileTable = {
        options: {
            autoSelect: true,
            boundaryLinks: false,
            largeEditDialog: false,
            pageSelector: false,
            rowSelection: true
        },
        query: {
            page: 1,
            limit: 10
        },
        data: {},
        selected: [],
        searching: false,
        delete: function (id) {
            for (var i = 0; i < $scope.fileTable.data.items.length; i++) {
                if (id === $scope.fileTable.data.items[i].id) {
                    $scope.fileTable.data.items.splice(i, 1);
                }
            }
        },
        batchDelete: function (ids) {
            for (var i = 0; i < ids.length; i++) {
                $scope.fileTable.delete(ids[i]);
            }
        }
    };

    $scope.reloadFiles = function () {
        $http.put('/admin/ajax/file/find', $scope.fileTable.query).then(function (response) {
            $scope.fileTable.data = response.data;
            $scope.fileTable.selected = [];
        });
    };

    $scope.cancelSearching = function () {
        $scope.fileTable.searching = false;
        $scope.fileTable.query.title = null;

        $scope.reloadFiles();
    };

    $scope.updateFile = function (file) {
        $state.go('console.file.update', {id: file.id});
    };

    $scope.deleteFile = function (file) {
        var confirm = $mdDialog.confirm()
            .title('确认删除文件' + file.path)
            .ariaLabel('Delete File')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function () {
            $http.delete('/admin/ajax/file/' + file.id).then(function () {
                $scope.fileTable.delete(file.id);
            });
        });
    };

    $scope.batchDeleteFiles = function () {
        var confirm = $mdDialog.confirm()
            .title('确认删除选中的文件' + $scope.fileTable.selected.length)
            .ariaLabel('Delete File')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        var request = {ids: []};
        for (var i = 0; i < $scope.fileTable.selected.length; i++) {
            request.ids.push($scope.fileTable.selected[i].id);
        }

        $mdDialog.show(confirm).then(function () {
            $http.post('/admin/ajax/file/delete', request).then(function () {
                $scope.fileTable.batchDelete(request.ids);
            });
        });
    };

    $scope.uploadFiles = function (files) {
        var url = '/admin/ajax/file/upload?directoryId=' + $scope.fileTable.query.directoryId;
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var f = files[i];
                Upload.upload({
                    url: url,
                    data: {file: f}
                }).then(function () {
                    $scope.reloadFiles();
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                }, function (evt) {
                });
            }
        }
    };

    $scope.showModal = function (file) {
        $mdDialog.show({
            clickOutsideToClose: true,
            focusOnOpen: false,
            targetEvent: event,
            templateUrl: '/admin/file/view/file.view.html',
            locals: {
                file: file,
                update: $scope.updateFile,
                del: $scope.deleteFile
            },
            controller: function ($scope, file, update, del) {
                $scope.file = file;
                $scope.update = update;
                $scope.del = del;
                $scope.close = function () {
                    $mdDialog.hide();
                };
                $scope.updateFile = function (file) {
                    $scope.update(file);
                    $scope.close();
                };
                $scope.deleteFile = function (file) {
                    $scope.del(file);
                    $scope.close();
                }
            }
        });
    };

    $scope.$on('directoryChange', function (event, directory) {
        $scope.fileTable.query.directoryId = directory.directory.id;
        $scope.reloadFiles();
    });

    $scope.reloadFiles();
};
