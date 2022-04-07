'use strict';

app
    .config(["$stateProvider", function ($stateProvider) {
        $stateProvider
            .state('forgotpassword', {
                url: '/forgotpassword',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'forgotpassword.html',
                        controller: 'NewForGotPasswordController'
                    }
                }
            });
    }])
    .controller('NewForGotPasswordController', ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        // $scope.resetpassword = function () {
        //     $scope.emailOrMobile;
        // }
    }]);

    // After clicking on reset password button, mail to be generated
    // link should be sent to mail, in that link we will be providing
    // with "New password" setting fields
    // Those HTML pages are designed and named as
    // "resetnewpassword.html" ----> "resetnewpasswordcontroller.js"