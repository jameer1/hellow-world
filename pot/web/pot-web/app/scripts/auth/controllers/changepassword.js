'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state('changepawd', {
		url : '/changepawd',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'changepassword.html',
				controller : 'ChangePasswordController'
			}
		}
	});
}]).controller('ChangePasswordController', ["$rootScope", "$scope", "$state", "Auth", "changePasswordService", "GenericAlertService", function($rootScope, $scope, $state, Auth, changePasswordService, GenericAlertService) {
	$scope.user = {};
	$scope.errors = {};

	$scope.rememberMe = false;
	$scope.changepassword = function() {
		if ($scope.passwordReq.newPassword != $scope.passwordReq.confirmPassword) {
			$scope.passwordReq.confirmPassword = null;
			$scope.passwordReq.newPassword = null;
			GenericAlertService.alertMessage('Entered  password is incorrect please try again', "Warning");
			return;
		}
		var req = {
			"oldPassWord" : $scope.passwordReq.oldPassword,
			"newPassWord" : $scope.passwordReq.newPassword
		}
		changePasswordService.changeUserPassword(req).then(function(data) {
			GenericAlertService.alertMessage('Password is updated Successfully', "Info");
			$scope.Reset();
		}, function(error) {
			GenericAlertService.alertMessage('Please enter valid password', "Warning");
			$scope.passwordReq.oldPassword = null;
		})
	}
	$scope.Reset = function() {
		$scope.passwordReq.oldPassword = null, 
		$scope.passwordReq.newPassword = null,
		$scope.passwordReq.confirmPassword = null
	};
}]);
