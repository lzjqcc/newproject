'use strict';
/* Controllers */

angular.module('app')
    .controller('AppCtrl', ['$scope', '$http', '$translate', '$localStorage', '$window', '$rootScope', 'imService', 'toaster',
        function ($scope, $http, $translate, $localStorage, $window, $rootScope, imService, toaster) {

            $rootScope.myinfo = {};

            $http.get('/rest/users/' + window.myid).success(function (response) {
                $rootScope.myinfo = response;
                console.log(response);
            });

            $rootScope.sessions     = {};
            $rootScope.groups       = {};
            $rootScope.friends      = {};

            $rootScope.toasterSuccess = function (title, text) {
                toaster.pop('success', title, text);
            };

            $rootScope.toasterError = function (title, text) {
                toaster.pop('error', title, text);
            };

            $rootScope.toasterWarning = function (title, text) {
                toaster.pop('warning', title, text);
            };

            $rootScope.toasterInfo = function (title, text) {
                toaster.pop('info', title, text);
            };
            
            /*
             * 封装 sweetalert，减少代码量
             * @author 金杭
             * @date   2017/07/20
             * @param  {object} options 
             *    options属性： 
             *    title     弹出框的标题            string
             *    text      弹出框的具体内容    string
             *    type      弹出框 的类型           string
             *    callback  确认后执行的内容    function
             * @return 无
             * */
            $rootScope.sweetConfirm = function(options){
            	swal({
      			  title: options.title,
      			  text: options.text,
      			  type: options.type,
      			  showCancelButton: true,
      			  confirmButtonColor: "#F05050",
      			  cancelButtonText:"不，让我想想",
      			  confirmButtonText: "是，我想好了",
      			  closeOnConfirm: true
      			},
      			function(){
      				options.callback();
      			});
            }

            $rootScope.global_configs = {
                isEnabled: [
                    {key: 0, name: '不启用'},
                    {key: 1, name: '启用'}
                ],
                product_types: {
                    credit_load: '信用贷',
                    mortgage_load: '抵押',
                    pledge_load: '质押'
                },
                repayment_types: {
                    dengerbenxi: '等额本息',
                    dengerbenjin: '等额本金',
                    xianxihouben: '先息后本'
                },
                customer_types: {
                    personal: '个人',
                    enterprise: '企业'
                },
                time_units: {
                    day: '天',
                    week: '周',
                    month: '月'
                }
            };
            //imService.webim_login();

            //两秒后取好友
            setTimeout(function () {
                //$rootScope.toasterInfo();
                //imService.get_my_friend();
                //imService.get_my_group();
            }, 3000);

            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            if (isIE) {
                angular.element($window.document.body).addClass('ie');
            }
            if (isSmartDevice($window)) {
                angular.element($window.document.body).addClass('smart')
            }
            // config
            $scope.app = {
                name: '小金平台',
                version: '1.0.0',
                // for chart colors
                color: {
                    primary: '#7266ba',
                    info: '#23b7e5',
                    success: '#27c24c',
                    warning: '#fad733',
                    danger: '#f05050',
                    light: '#e8eff0',
                    dark: '#3a3f51',
                    black: '#1c2b36'
                },
                settings: {
                    themeID: 13,
                    navbarHeaderColor: "bg-dark",
                    navbarCollapseColor: "bg-white-only",
                    asideColor: "bg-dark",
                    headerFixe: true,
                    asideFixed: true,
                    asideFolded: false,
                    asideDock: false,
                    container: false
                }
            }


            // save settings to local storage
            if (angular.isDefined($localStorage.settings)) {
                $scope.app.settings = $localStorage.settings;
            } else {
                $localStorage.settings = $scope.app.settings;
            }
            $scope.$watch('app.settings', function () {
                if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                    // aside dock and fixed must set the header fixed.
                    $scope.app.settings.headerFixed = true;
                }
                // for box layout, add background image
                $scope.app.settings.container ? angular.element('html').addClass('bg') : angular.element('html').removeClass('bg');
                // save to local storage
                $localStorage.settings = $scope.app.settings;
            }, true);
            // angular translate
            $scope.lang       = {isopen: false};
            $scope.langs      = {en: 'English', de_DE: 'German', it_IT: 'Italian'};
            $scope.selectLang = $scope.langs[$translate.proposedLanguage()] || "English";
            $scope.setLang    = function (langKey, $event) {
                // set the current lang
                $scope.selectLang = $scope.langs[langKey];
                // You can change the language during runtime
                $translate.use(langKey);
                $scope.lang.isopen = !$scope.lang.isopen;
            };
            function isSmartDevice($window) {
                // Adapted from http://www.detectmobilebrowsers.com
                var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
            }


        }])
    .run(['$rootScope', '$state', '$stateParams', '$log', function ($rootScope, $state, $log, $stateParams) {


        $rootScope.$state       = $state;
        $rootScope.$stateParams = $stateParams;
//                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//                    $log.debug('successfully changed states');
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });
//
//                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
//                    $log.error('The request state was not found: ' + unfoundState);
//                });
//
//                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
//                    $log.error('An error occurred while changing states: ' + error);
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });


    }
    ]);
