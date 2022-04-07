'use strict';

app
    .config(["$stateProvider", function ($stateProvider) {
        $stateProvider
            .state('site.login', {
                url: '/login',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'login.html',
                        controller: 'LoginController'
                    }
                }
            });
    }])
    .controller('LoginController', ["$rootScope", "$scope", "$state", "Auth", "Principal", function ($rootScope, $scope, $state, Auth, Principal) {
        $scope.user = {};
        $scope.errors = {};
        // TODO : this is temporary fix for login form isuue with old app url in pot.constants.js
        // TODO: make $scope.showLoginForm = false; this is final fix for oracle_with_new_ui
        $scope.showLoginForm = true;

        $scope.rememberMe = false;
        $scope.login = function () {
        	console.log('   from login ...........');
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                clientCode: $scope.clientCode,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                setTimeout(() => {
                    $rootScope.$broadcast('loginsuccess');
                    $state.go('enterprise');
                }, 500);
               
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
       $scope.Reset=function(){
    	   $scope.username=null,
    	   $scope.password=null,
    	   $scope.clientCode=null
       };

        $rootScope.$on('loginsuccess', function (event, args) {
            $scope.showLoginForm = false;
        });

        $rootScope.$on("tokenExpired", function () {
            $scope.showLoginForm = true;
        });
        
        // Check user login status
        Principal.identity(true).then(function(){
            $state.go('home');
        });
    }]);
