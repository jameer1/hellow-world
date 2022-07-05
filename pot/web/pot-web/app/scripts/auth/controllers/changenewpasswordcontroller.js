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
                controller: 'ChangenewpasswordController'
            }
        }
    });
}]).controller('ChangenewpasswordController', ["$rootScope", "$scope", "$state", "Auth", "changePasswordService", "GenericAlertService", function ($rootScope, $scope, $state, Auth,changePasswordService, GenericAlertService) {

    $(".toggle-password,.toggle-password1,.toggle-password2").click(function () {

        $(this).toggleClass("fa-eye fa-eye-slash");
        var input = $($(this).attr("toggle"));
        if (input.attr("type") == "password") {
            input.attr("type", "text");
        } else {
            input.attr("type", "password");
        }
    });
    $scope.reset=function (){
	
	    $scope.ChangeNewPwd.currpassword = null,
	    $scope.ChangeNewPwd.newpassword = null,
	    $scope.ChangeNewPwd.verifypassword = null
		}
    $scope.changepassword = function () {
	
	console.log($rootScope.changePasswordData)
	
	if ($scope.ChangeNewPwd.newpassword != $scope.ChangeNewPwd.verifypassword) {
			$scope.ChangeNewPwd.verifypassword = null;
			$scope.ChangeNewPwd.newpassword = null;
			GenericAlertService.alertMessage('Entered  password is incorrect please try again', "Warning");
			return;
		}
		$scope.datas=[];
		$scope.datas=$rootScope.changePasswordData;
		console.log($scope.datas)
		var req = {
			
			"oldPwd": $scope.ChangeNewPwd.currpassword,
			"currentPwd": $scope.ChangeNewPwd.newpassword,
			"password": $scope.datas.password,
			"username": $scope.datas.username,
			"authorities": $scope.datas.authorities,
			"accountNonExpired": $scope.datas.accountNonExpired,
			"accountNonLocked": $scope.datas.accountNonLocked,
			"credentialsNonExpired": $scope.datas.credentialsNonExpired,
			"enabled": $scope.datas.enabled,
			"userId": $scope.datas.userId,
			"email": $scope.datas.email,
			"name": $scope.datas.name,
			"displayName": $scope.datas.displayName,
			"displayRole": $scope.datas.displayRole,
			"designation": $scope.datas.designation,
			"token": $scope.datas.token,
			"clientId": $scope.datas.clientId,
			"clientCode": $scope.datas.clientCode,
			"registeredUsers": $scope.datas.registeredUsers,
			"adminClientId": $scope.datas.adminClientId
			
		}
		console.log(req)
		changePasswordService.changeUserPassword(req).then(function(data) {
			GenericAlertService.alertMessage('Password is updated Successfully', "Info");
		       setTimeout(() => {
                    $rootScope.$broadcast('loginsuccess');
                    $state.go('enterprise');
                }, 500);
			
			
			$scope.Reset();
		}, function(error) {
			GenericAlertService.alertMessage('Please enter valid password', "Warning");
			$scope.ChangeNewPwd.currpassword = null;
		})

    }
  
}]);
