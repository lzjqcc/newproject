'use strict';

/**
 * Config for the router
 */
angular.module('app')

        .config(
                ['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
                    function ($stateProvider, $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {

                        $urlRouterProvider.otherwise('/dashboard');


                        //工作台
                        $stateProvider
                                .state('dashboard', {
                                    url: '/dashboard',
                                    templateUrl: 'template/user/dashboard.html'
                                })
                                .state('users', {
                                    url: '/users',
                                    templateUrl: "tpl/layouts/users.html"
                                })
                                .state('users.user', {
                                    url: '/{user_id}',
                                    templateUrl: "tpl/entities/users/home.html"
                                })
                                .state('users.user.page', {
                                    url: '/{entity_type}/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                                    }
                                })
                                .state('users.create', {
                                    url: '/create',
                                    templateUrl: "tpl/entities/users/create.html"
                                })
                                .state('affairs', {
                                    url: '/affairs',
                                    templateUrl: "tpl/layouts/affairs.html"
                                })
                                .state('affairs.entity', {
                                    url: '/:entity_type/:entity_id',
                                    views: {
                                        '': {
                                            templateUrl: function ($stateParams) {
                                                return 'tpl/entities/' + $stateParams.entity_type + '/actlist.html';
                                            }
                                        },
                                        'entity': {
                                            templateUrl: function ($stateParams) {
                                                return 'tpl/entities/' + $stateParams.entity_type + '/page.html';
                                            }
                                        }
                                    }
                                })
                                .state('messages', {
                                    url: '/messages',
                                    templateUrl: "tpl/layouts/message.html",
                                    resolve: load(['js/app/message/message.js', 'moment'])
                                })
                                .state('messages.GROUP', {
                                    url: '/GROUP/:session_id',
                                    templateUrl: "tpl/layouts/message_group.html"
                                })
                                .state('messages.C2C', {
                                    url: '/C2C/:session_id',
                                    templateUrl: 'tpl/layouts/message_C2C.html',
                                    resolve: load(['js/app/message/message.js', 'moment'])
                                })
                                .state('stores', {
                                    url: '/stores',
                                    templateUrl: "tpl/layouts/stores.html"
                                })
                                .state('stores.store', {
                                    url: '/{store_id}',
                                    templateUrl: "tpl/entities/stores/home.html"
                                })
                                .state('stores.store.page', {
                                    url: '/{entity_type}/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                                    }
                                })
                                .state('stores.create', {
                                    url: '/create',
                                    templateUrl: "tpl/entities/stores/create.html"
                                })
                                .state('distributors', {
                                    url: '/distributors',
                                    templateUrl: "tpl/layouts/distributors.html"
                                })
                                .state('distributors.distributor', {
                                    url: '/{distributor_id}',
                                    templateUrl: "tpl/entities/distributors/home.html"
                                })
                                .state('distributors.distributor.page', {
                                    url: '/{entity_type}/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                                    }
                                })
                                .state('distributors.create', {
                                    url: '/create',
                                    templateUrl: "tpl/entities/distributors/create.html"
                                });


                        //业务系统
                        $stateProvider
                                .state('orders', {
                                    url: '/orders',
                                    templateUrl: "tpl/layouts/orders.html"
                                })
                                .state('orders.create', {
                                    url: '/create',
                                    templateUrl: "tpl/entities/orders/create.html"
                                })
                                .state('orders.list', {
                                    url: '/{status}',
                                    templateUrl: "tpl/entities/orders/list.html"
                                })
                                .state('orders.order', {
                                    url: '/{order_id}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/orders/home.html";
                                    }
                                })
                                .state('orders.order.page', {
                                    url: '/{entity_type}/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                                    }
                                })
                                .state('customers', {
                                    url: '/customers',
                                    templateUrl: "tpl/layouts/customers.html"
                                })
                                .state('customers.create', {
                                    url: '/create',
                                    templateUrl: "tpl/entities/customers/create.html"
                                })
                                .state('customers.list', {
                                    url: '/{status}',
                                    templateUrl: "tpl/entities/customers/list.html"
                                });

                        //业务系统
                        $stateProvider
                                .state('risks', {
                                    url: '/risks',
                                    templateUrl: "tpl/layouts/risks.html"
                                })
                                .state('risks.page', {
                                    url: '/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/risks/" + $stateParams.view_type + ".html";
                                    }
                                });


                        //财务系统
                        $stateProvider
                                .state('widthdraws', {
                                    url: '/widthdraws',
                                    templateUrl: "tpl/layouts/widthdraws.html"
                                })
                                .state('widthdraws.list', {
                                    url: '/{status}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/widthdraws/list.html";
                                    }
                                })
                                .state('repayments', {
                                    url: '/repayments',
                                    templateUrl: "tpl/layouts/repayments.html"
                                })
                                .state('repayments.list', {
                                    url: '/{status}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/repayments/list.html";
                                    }
                                })
                                .state('lendouts', {
                                    url: '/lendouts',
                                    templateUrl: "tpl/layouts/lendouts.html"
                                })
                                .state('lendouts.list', {
                                    url: '/{status}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/lendouts/list.html";
                                    }
                                });


                        //管理系统
                        $stateProvider
                                .state('investors', {
                                    url: '/investors',
                                    templateUrl: "tpl/layouts/investors.html"
                                });

                        //开发与配置
                        $stateProvider
                                .state('products', {
                                    url: '/products',
                                    templateUrl: "tpl/layouts/products.html"
                                })
                                .state('products.product', {
                                    url: '/{product_id}',
                                    templateUrl: "tpl/entities/products/home.html"
                                })
                                .state('products.product.page', {
                                    url: '/{view_type}',
                                    templateUrl: function ($stateParams) {
                                        return  "tpl/entities/products/" + $stateParams.view_type + ".html";
                                    }
                                })
                                .state('entities', {
                                    url: '/entities',
                                    cache:false,
                                    templateUrl: "tpl/layouts/entities.html"
                                })
                                .state('entities.entity', {
                                    url: '/{entity_type}',
                                    cache:false,
                                    templateUrl: function ($stateParams) {
                                        return  "template/" + $stateParams.entity_type + "/configs.html";
                                    }
                                })
                                .state('entities.entity.page', {
                                    url: '/{view_type}',
                                    cache:false,
                                    templateUrl: function ($stateParams) {
                                        return  "template/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                                    },
                                    resolve: load(["xeditable"])
                                })
                                 .state('fees', {
                                    url: '/fees',
                                    templateUrl: "tpl/layouts/fees.html"
                                })
                                .state('fees.fee', {
                                    url: '/{fee_id}',
                                    templateUrl: "tpl/entities/fees/form.html"
                                });

                        function load(srcs, callback) {
                            return {
                                deps: ['$ocLazyLoad', '$q',
                                    function ($ocLazyLoad, $q) {
                                        var deferred = $q.defer();
                                        var promise = false;
                                        srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                                        if (!promise) {
                                            promise = deferred.promise;
                                        }
                                        angular.forEach(srcs, function (src) {
                                            promise = promise.then(function () {
                                                if (JQ_CONFIG[src]) {
                                                    return $ocLazyLoad.load(JQ_CONFIG[src]);
                                                }
                                                angular.forEach(MODULE_CONFIG, function (module) {
                                                    if (module.name == src) {
                                                        name = module.name;
                                                    } else {
                                                        name = src;
                                                    }
                                                });
                                                return $ocLazyLoad.load(name);
                                            });
                                        });
                                        deferred.resolve();
                                        return callback ? promise.then(function () {
                                            return callback();
                                        }) : promise;
                                    }]
                            }
                        }


                    }
                ]
                );
