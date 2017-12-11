'use strict';

var angular = require('angular');

module.exports = function($scope, $rootScope, $http, $stateParams, $state, Upload, $mdDialog, $mdToast, Console) {
    $scope.isCreateOpen = false;
    $scope.tree = {
        currentNode: null,
        data: [],
        path: [],
        selectedNodes: [],
        delete: function(node) {
            var d = function(parent, target) {
                var i, child;
                for (i = 0; i < parent.children.length; i++) {
                    child = parent.children[i];
                    if (child.directory.id === target.directory.id) {
                        parent.children.splice(i, 1);
                        return true;
                    }
                }

                for (i = 0; i < parent.children.length; i++) {
                    child = parent.children[i];
                    if (d(child, target)) {
                        return true;
                    }
                }
                return false;
            };

            d($scope.tree.data[0], node);
        },
        file: {
            data: [],
            selected: [],
            query: {
                directoryId: null,
                page: 1,
                limit: 1000
            },
            delete: function(id) {
                for (var i = 0; i < $scope.fileTable.data.items.length; i++) {
                    if (id === $scope.fileTable.data.items[i].id) {
                        $scope.fileTable.data.items.splice(i, 1);
                    }
                }
            },
            batchDelete: function(ids) {
                for (var i = 0; i < ids.length; i++) {
                    $scope.fileTable.delete(ids[i]);
                }
            },
            reloadFiles: function() {
                $scope.tree.file.query.directoryId = $scope.tree.currentNode.directory.parentId;
                $http.put('/admin/ajax/file/find', $scope.tree.file.query).then(function(response) {
                    $scope.tree.file.data = response.data;
                    $scope.tree.file.selected = [];
                });
            },
            uploadFiles: function(files) {
                var url = '/admin/ajax/file/upload?directoryId=' + $scope.tree.currentNode.directory.id;
                if (files && files.length) {
                    for (var i = 0; i < files.length; i++) {
                        var f = files[i];
                        Upload.upload({
                            url: url,
                            data: {file: f}
                        }).then(function() {
                            $scope.tree.file.reloadFiles();
                        }, function(resp) {
                            console.log('Error status: ' + resp.status);
                        }, function(evt) {
                        });
                    }
                }
            },
            select: function(file) {
                file.selected = !file.selected;
                if (file.selected) {
                    $scope.tree.file.selected.push(file);
                } else {
                    for (var i = 0; i < $scope.tree.file.selected.length; i++) {
                        if ($scope.tree.file.selected[i].id === file.id) {
                            $scope.tree.file.selected.splice(i, 1);
                        }
                    }
                }
            },
            show: function(file) {
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
                    controller: function($scope, file, update, del) {
                        $scope.file = file;
                        $scope.update = update;
                        $scope.del = del;
                        $scope.close = function() {
                            $mdDialog.hide();
                        };
                        $scope.updateFile = function(file) {
                            $scope.update(file);
                            $scope.close();
                        };
                        $scope.deleteFile = function(file) {
                            $scope.del(file);
                            $scope.close();
                        }
                    }
                });
            }

        }
    };

    $scope.selectDefaultDirectory = function() {
        $scope.tree.currentNode = $scope.tree.data[0];
        $scope.tree.path.push($scope.tree.currentNode);
    };

    $scope.selectPathDirectory = function(paths) {
        for (var pathIndex = 0; pathIndex < paths.length; pathIndex++) {
            if ($scope.tree.currentNode) {
                if ($scope.tree.currentNode.children) {
                    for (var index = 0; index < $scope.tree.currentNode.children.length; index++) {
                        if ($scope.tree.currentNode.children[index].directory.id === paths[pathIndex]) {
                            $scope.tree.currentNode = $scope.tree.currentNode.children[index];
                            $scope.tree.path.push($scope.tree.currentNode);
                            break;
                        }
                    }
                }
            } else {
                for (var index = 0; index < $scope.tree.data.length; index++) {
                    if ($scope.tree.data[index].directory.id === paths[pathIndex]) {
                        $scope.tree.currentNode = $scope.tree.data[index];
                        $scope.tree.path.push($scope.tree.currentNode);
                        break;
                    }
                }
            }
        }
    };

    $scope.reload = function() {
        $http.get('/admin/ajax/file/directory/tree')
            .then(function(response) {
                $scope.tree.data = response.data;
                $scope.selectDefaultDirectory();
                if ($stateParams.path) {
                    var paths = $stateParams.path.split("/");
                    $scope.selectPathDirectory(paths);
                }
                $scope.tree.file.reloadFiles();
            });
    };

    $scope.isSelectedDeletable = function() {
        return $scope.tree.currentNode && $scope.tree.currentNode.children && $scope.tree.currentNode.children.length == 0;
    };

    $scope.delete = function(event) {
        var deletingFolders = "";
        var deletingFiles = "";
        $scope.tree.selectedNodes.forEach(function(directory) {
            if (deletingFolders) {
                deletingFolders += ",";
            }
            deletingFolders += directory.name;
        });
        $scope.tree.file.selected.forEach(function(file) {
            if (deletingFiles) {
                deletingFiles += ",";
            }
            deletingFiles += file.title;
        });
        var confirm = $mdDialog.confirm()
            .title('确认删除目录[' + deletingFolders + "]，文件[" + deletingFiles + "]")
            .ariaLabel('Delete Directory')
            .theme('white')
            .targetEvent(event)
            .ok('确认')
            .cancel('取消');

        $mdDialog.show(confirm).then(function() {
            $scope.tree.selectedNodes.forEach(function(directory) {
                $http.delete('/admin/ajax/file/directory/' + directory.id).then(function() {
                    $scope.reload();
                });
            });
            $scope.tree.file.selected.forEach(function(file) {
                $http.delete('/admin/ajax/file/' + file.id).then(function() {
                    $scope.tree.file.reloadFiles();
                });
            })
        });
    };

    $scope.createDirectory = function(event) {
        var selectedNode = $scope.tree.currentNode;

        $mdDialog.show({
            controller: function($scope, $http) {
                $scope.selectedDirectory = selectedNode;
                $scope.directory = {
                    parentId: $scope.selectedDirectory.directory.id
                };

                $scope.save = function() {
                    $http.post('/admin/ajax/file/directory', $scope.directory)
                        .then(function(response) {
                            if (!selectedNode.children) {
                                selectedNode.children = [];
                            }
                            selectedNode.children.push({directory: response.data, children: []});
                            $mdDialog.cancel();
                        });
                };

                $scope.cancel = function() {
                    $mdDialog.cancel();
                }
            },
            templateUrl: '/admin/file/view/directory.update.html',
            parent: angular.element(".page"),
            targetEvent: event,
            clickOutsideToClose: true
        });
    };

    $scope.enterNode = function(selectedNode) {
        $scope.tree.currentNode = selectedNode;
        $scope.tree.selectedNodes = [];
        var existIndex = -1;
        $scope.tree.path.forEach(function(node, index) {
            if (node.directory.id === selectedNode.directory.id) {
                existIndex = index;
            }
        });
        $scope.$broadcast('directoryChange', selectedNode);
        if (existIndex >= 0) {
            $scope.tree.path.splice(existIndex + 1);
        } else {
            $scope.tree.path.push(selectedNode);
        }
        var path = "";
        $scope.tree.path.forEach(function(node, index) {
            if (path) {
                path += "/";
            }
            path += node.directory.id;
        });

        $state.go('console.file', {path: path});
    };

    $scope.selectNode = function(selectedNode) {
        selectedNode.selected = !selectedNode.selected;
        if (selectedNode.selected) {
            $scope.tree.selectedNodes.push(selectedNode.directory);
        } else {
            for (var i = 0; i < $scope.tree.selectedNodes.length; i++) {
                if ($scope.tree.selectedNodes[i].id === selectedNode.directory.id) {
                    $scope.tree.selectedNodes.splice(i, 1);
                }
            }
        }
    };

    $scope.isImage = function(path) {
        return path.match(/\.(jpeg|jpg|gif|png)$/) != null;
    };

    $scope.reload();
};
