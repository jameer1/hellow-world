'use strict';

app.config(["$stateProvider", function ($stateProvider) {
    $stateProvider.state('changepassword', {
        url: '/changepassword',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'changenewpassword.html',
                controller: 'ChangeNewPasswordController'
            }
        }
    });
}]).controller('ChangeNewPasswordController', ["$rootScope", "$scope", "$state", "Auth", "changePasswordService", "GenericAlertService", function ($rootScope, $scope, $state, Auth, changePasswordService, GenericAlertService) {

    $(".toggle-password,.toggle-password1,.toggle-password2").click(function () {

        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $($(this).attr("toggle"));
        if (input.attr("type") == "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });
    $scope.changepassword = function () {

    }
    $scope.reset = function () {

    };
}]);
