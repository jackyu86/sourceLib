"use strict";

require([
    'angular',
    'rangy',
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngFileUpload',
    'ngTreeView',
    'ngLoadingBar',
    'uiRouter',
    'ngTextAngular',
    'ngSanitize',
    'rangy',
    'rangy/lib/rangy-selectionsaverestore',
    'mdMaterial',
    'mdDataTable',
    'd3',
    'nvd3',
    'ngNvd3'].concat(Console.modules.map(function(module) {
    return module + '/' + module + '.js'
})), function(angular, rangy) {
    window.rangy = rangy;
    window.taTools = {};

    var module = angular.module("app", [
        'ngAnimate',
        'ngAria',
        'ngCookies',
        'ngMessages',
        'ngFileUpload',
        'angularTreeview',
        'angular-loading-bar',
        'textAngular',
        'ui.router',
        'ngMaterial',
        'md.data.table',
        'nvd3'
    ].concat(Console.modules));

    module.config(function($stateProvider, $urlRouterProvider, $locationProvider, $mdThemingProvider, $provide, cfpLoadingBarProvider, $compileProvider, $httpProvider) {
        $compileProvider.preAssignBindingsEnabled(true);
        cfpLoadingBarProvider.includeSpinner = false;
        $urlRouterProvider.otherwise("/");
        $httpProvider.interceptors.push("loginInterceptor");

        $mdThemingProvider.definePalette('console', {
            '50': '3c434d',
            '100': '3c434d',
            '200': '3c434d',
            '300': '3c434d',
            '400': '3c434d',
            '500': '3c434d',
            '600': '3c434d',
            '700': '3c434d',
            '800': '3c434d',
            '900': '3c434d',
            'A100': '000000',
            'A200': '000000',
            'A400': '000000',
            'A700': '000000',
            'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
            'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
                '200', '300', '400', 'A100'],
            'contrastLightColors': undefined    // could also specify this if default was 'dark'
        });

        $mdThemingProvider.theme('default')
            .primaryPalette('console')
            .accentPalette('console');

        $provide.decorator('taOptions', ['taRegisterTool', '$delegate', '$mdDialog', 'Upload', '$http', function(taRegisterTool, taOptions, $mdDialog, Upload, $http) {
            taRegisterTool('upload-image', {
                iconclass: "fa fa-image",
                action: function($deferred) {
                    var textAngular = this;
                    var savedSelection = rangy.saveSelection();
                    $mdDialog.show({
                        controller: function($scope) {
                            $scope.uploadedFiles = {
                                skip: 0,
                                limit: 12,
                                total: -1
                            };
                            $scope.selectedImages = {};
                            $scope.selectedImageList = [];
                            $scope.list = function() {
                                $http.put("/admin/file/find", {
                                    skip: $scope.uploadedFiles.skip,
                                    limit: $scope.uploadedFiles.limit
                                }).then(function(response) {
                                    $scope.uploadedFiles = response.data;
                                });
                            };
                            $scope.prev = function() {
                                $scope.uploadedFiles.skip -= $scope.uploadedFiles.limit;
                                $scope.list();
                            };
                            $scope.next = function() {
                                $scope.uploadedFiles.skip += $scope.uploadedFiles.limit;
                                $scope.list();
                            };
                            $scope.selectImage = function(file) {
                                if ($scope.selectedImages[file.id]) {
                                    $scope.selectedImages[file.id] = false;
                                    for (var i = 0; i < $scope.selectedImageList.length; i++) {
                                        var data = $scope.selectedImageList[i];
                                        if (data.id == file.id) {
                                            $scope.selectedImageList.splice(i, 1);
                                            break;
                                        }
                                    }
                                } else {
                                    $scope.selectedImages[file.id] = true;
                                    $scope.selectedImageList.push(file);
                                }
                            };
                            $scope.insertImage = function() {
                                $scope.selectedImageList.forEach(function(data) {
                                    rangy.restoreSelection(savedSelection);
                                    textAngular.$editor().wrapSelection('insertImage', data.path, true);
                                });
                                $deferred.resolve();
                                $mdDialog.hide();
                            };
                            $scope.upload = function(file) {
                                Upload.upload({
                                    url: '/admin/file/upload',
                                    data: {file: file}
                                }).then(function(resp) {
                                    rangy.restoreSelection(savedSelection);
                                    textAngular.$editor().wrapSelection('insertImage', resp.data.path, true);
                                    $deferred.resolve();
                                    $mdDialog.hide();
                                }, function(resp) {
                                    $deferred.resolve();
                                    $mdDialog.hide();
                                }, function(evt) {
                                    $deferred.resolve();
                                    $mdDialog.hide();
                                });
                            };

                            $scope.list();
                        },
                        templateUrl: 'module/template/tpl.admin.upload.image.html',

                        parent: angular.element(document.body),
                        clickOutsideToClose: true,
                        fullscreen: false,
                        theme: 'white'
                    }).then(function(response) {
                    }, function() {
                    });
                }
            });
            taOptions.toolbar[1].push('upload-image');
            return taOptions;
        }]);

        $stateProvider
            .state('console', {
                url: "/console",
                template: '<div ui-view flex layout="column"></div>',
                controller: function($rootScope, $scope, site, user) {
                    $rootScope.site = site;
                    $rootScope.user = user;
                },
                resolve: {
                    site: function($http) {
                        try {
                            return $http.get('/admin/ajax/site').then(function(response) {
                                return response.data.items;
                            });

                        } catch (e) {
                            console.log(e);
                        }
                    },
                    user: function($http, $window) {
                        if (Console.user) {
                            //不确定
                            return $http.get('/admin/ajax/user/self').then(function(data) {
                                var user = data.data;

                                user.hasRole = function(role) {
                                    var i = user.roles.length;
                                    while (i) {
                                        if (user.roles[i - 1] === role) {
                                            return true;
                                        }
                                        i--;
                                    }
                                    return false;
                                };

                                user.hasPermission = function(permission) {
                                    if (!permission || user.hasRole('admin')) {
                                        return true;
                                    }

                                    if (!user.permissions) {
                                        return false;
                                    }

                                    var i = user.permissions.length;
                                    while (i) {
                                        if (user.permissions[i - 1] === permission) {
                                            return true;
                                        }
                                        i--;
                                    }
                                    return false;
                                };

                                user.logout = function() {
                                    $http.get('/admin/ajax/user/logout').then(function() {
                                        $window.location.href = '/admin/';
                                    });
                                };

                                return user;
                            });
                        } else {
                            return {
                                username: 'UNKOWN',
                                hasRole: function() {
                                    return false;
                                },
                                hasPermission: function() {
                                    return true;
                                },
                                logout: function() {
                                }
                            };
                        }
                    }
                }
            });
    });

    module.run(['$rootScope', '$http', '$filter', '$state', '$cookies', '$templateCache', 'Console', function($rootScope, $http, $filter, $state, $cookies, $templateCache, Console) {
        $rootScope.$state = $state;
        $rootScope.Console = Console;
    }]);

    module.provider('Console', function() {
        this.Console = angular.extend(Console, {
            messages: {
                items: {},
                get: function(key) {
                    return Console.messages.items[key];
                },
                add: function(messages) {
                    for (var i = 0; i < messages.length; i++) {
                        var message = messages[i];
                        Console.messages.items[message.key] = message.value;
                    }
                }
            },
            menu: {
                focus: 'main',
                autoHide: false,
                selected: null,
                items: [],
                heading: null,
                add: function(item) {
                    var found = false;
                    for (var i = 0; i < Console.menu.items.length; i++) {
                        if (item.priority < Console.menu.items[i].priority) {
                            found = true;
                            Console.menu.items.splice(i, 0, item);
                            break;
                        }
                    }

                    if (!found) {
                        Console.menu.items.push(item);
                    }
                },
                remove: function(state) {
                    for (var i = 0; i < Console.menu.items.length; i++) {
                        var parent = Console.menu.items[i];
                        if (parent.state === state) {
                            Console.menu.items.splice(i, 1);
                            return;
                        } else if (parent.children) {
                            for (var j = 0; j < parent.children.length; j++) {
                                if (parent.children[j].state == state) {
                                    parent.children.splice(j, 1);
                                    return;
                                }
                            }
                        }
                    }
                },
                focusMain: function() {
                    Console.menu.focus = "main";
                },
                focusSecond: function() {
                    Console.menu.focus = "second";
                },
                hasSubMenu: function() {
                    return Console.menu.selected && Console.menu.selected.children && Console.menu.selected.children.length > 0;
                },
                select: function(item) {
                    Console.menu.selected = item;
                    if (Console.menu.hasSubMenu()) {
                        this.focus = "second";
                    }


                    var key = Console.menu.selected.name;
                    Console.menu.heading = Console.messages.get(key);
                    console.log(Console.menu.heading);
                },
                item: function(name) {
                    for (var i = 0; i < Console.menu.items.length; i++) {
                        if (name === Console.menu.module.items[i].name) {
                            return Console.menu.module.items[i];
                        }
                    }
                },
                isItemSelected: function(item) {
                    return Console.menu.selected && Console.menu.selected.name === item.name;
                }
            },
            dashboard: []
        });

        this.$get = function() {
            return this.Console;
        }
    });

    module.directive('menuToggle', ['$timeout', function($timeout, $state) {
        return {
            scope: {
                item: '=',
                menu: '=',
                user: '='
            },
            templateUrl: 'partials/menu-toggle.tmpl.html',
            link: function($scope, element) {
                $scope.$state = $state;

                var parentNode = element[0].parentNode.parentNode.parentNode;
                if (parentNode.classList.contains('parent-list-item')) {
                    var heading = parentNode.querySelector('h2');
                    element[0].firstChild.setAttribute('aria-describedby', heading.id);
                }
            }
        }
    }]);

    module.directive('menuLink', function($state) {
        return {
            scope: {
                item: '=',
                menu: '=',
                user: '='
            },
            templateUrl: 'partials/menu-link.tmpl.html',
            link: function($scope, $element) {
                $scope.$state = $state;

                var controller = $element.parent().controller();
                $scope.focusSection = function() {
                    controller.autoFocusContent = true;
                };
            }
        };
    });

    module.directive('menuHeading', function($state) {
        return {
            scope: {
                menu: '='
            },
            templateUrl: 'partials/menu-heading.tmpl.html',
            link: function($scope, $element) {
            }
        };
    });

    module.directive('invalid', function() {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function(scope, elem, attr, ctrl) {
                var $elem = $(elem);
                var name = $elem.attr('name');
                var formName = $elem.parents('form').attr('name');
                scope.$watch(attr.ngModel, function() {
                    if (scope[formName][name].$dirty) {
                        ctrl.$setValidity('invalid', true);
                    }
                });
            }
        }
    });

    module.directive('mdMsg', function(Console) {
        return function($scope, $element, $attr) {
            var message = Console.messages.get($attr.mdMsg);
            if (message) {
                $element.text(message);
            }
        }
    });

    module.factory('loginInterceptor', ['$q', function($q) {
        return {

            responseError: function(rejection) {
                if (rejection.status === 403) {
                    var loginUrl = "/admin/login";
                    if (rejection.data.fromURL !== undefined) {
                        loginUrl += "?fromURL=" + rejection.data.fromURL;
                    }
                    window.location.href = loginUrl;
                }
                return $q.reject(rejection);
            }
        }
    }]);

    angular.element().ready(function() {
        angular.bootstrap(document.body, ['app']);
        $('.loader').hide();
    });
});