'use strict';

app
    .config(["$stateProvider", function ($stateProvider) {
        $stateProvider
            .state('forgetpwd', {
                url: '/forgetpwd',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'forgetpassword.html',
                        controller: 'ForGetPasswordController'
                    }
                }
            });
    }])
    .controller('ForGetPasswordController', ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = false;
        $scope.changepassword = function () {
        	console.log('   from login ...........');
            Auth.changePassword({
                username: $scope.username,
                password: $scope.password,
                clientCode: $scope.clientCode,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                $rootScope.$broadcast('loginsuccess');
                $state.go('home');
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
       $scope.Reset=function(){
    	   $scope.username=null,
    	   $scope.password=null,
    	   $scope.clientCode=null
       };
    }]);
