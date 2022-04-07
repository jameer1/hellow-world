'use strict';

app.run( ["$rootScope", "$location", "$http", "$state", "Restangular", "ngDialog", "Auth", "Principal", "appUrl", "localStorageService", "TokenExpirationService", function ($rootScope, $location, $http, $state, Restangular, ngDialog, Auth, Principal, appUrl,
         localStorageService, TokenExpirationService) {
        Restangular.setBaseUrl(appUrl.appurl);
        $rootScope.$on('$stateChangeStart', function (event, toState,
            toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;

        });
        $rootScope.$on("$stateChangeSuccess", function (event, toState,
            toParams, fromState, fromParams) {
            $rootScope.previousState_name = fromState.name;
        });
        $rootScope.back = function () {
            if ($rootScope.previousState_name === 'activate') {
                $state.go(previousState_name);
            } else {
                $state.go($rootScope.previousState_name,
                    $rootScope.previousState_params);
            }
        };

        Restangular.addFullRequestInterceptor(function (element, operation, route, url, headers, params, httpConfig) {
            _.contains = _.includes;
           /* if (Principal.isIdentityResolved()) {
                headers["pottoken"] = Principal.potToken();

            } else {
                Principal.identity().then(function (account) {
                    if (account.token) {
                        headers["pottoken"] = account.token;
                    }
                });
            } */
            headers["pottoken"] = localStorageService.get('pottoken');

            return {
                element: element,
                headers: headers,
                params: params,
                httpConfig: httpConfig
            };
        });

        /**
         *  Global error handler for Restangular
         */
        Restangular.setErrorInterceptor(function(response, deferred, responseHandler) {
            if(response.status === 401) {
                TokenExpirationService.validateTokenExpiration(response);
                return false; // error handled
            }
            return true; // error not handled
        });

    }]).config(["$stateProvider", "$urlRouterProvider", "$httpProvider", "$locationProvider", "$translateProvider", "appUrlProvider", function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, appUrlProvider) {
        appUrlProvider.getAppURL();
        //	$locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
        })


    }]);
