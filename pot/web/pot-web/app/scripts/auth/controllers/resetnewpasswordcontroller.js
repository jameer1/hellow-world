'use strict';

app
    .config(["$stateProvider", function ($stateProvider) {
        $stateProvider
            .state('resetnewpassword', {
                url: '/resetnewpassword',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'resetnewpassword.html',
                        controller: 'NewResetForGotPasswordController'
                    }
                }
            });
    }])
    .controller('NewResetForGotPasswordController', ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        $(".toggle-password,.toggle-password1").click(function () {

            $(this).toggleClass("fa-eye fa-eye-slash");
            var input = $($(this).attr("toggle"));
            if (input.attr("type") == "password") {
                input.attr("type", "text");
            } else {
                input.attr("type", "password");
            }
        });

        $scope.reset = function () {

        }
        $scope.save = function () {

        }
    }]);

    // After clicking on reset password button, mail to be generated
    // link should be sent to mail, in that link we will be providing
    // with "New password" setting fields
    // Those HTML pages are designed and named as
    // "resetnewpassword.html" ----> "resetnewpasswordcontroller.js"