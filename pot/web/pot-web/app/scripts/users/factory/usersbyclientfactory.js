'use strict';
app.factory('UsersByClientPopupService', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "UserService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, UserService, GenericAlertService) {
	var usersByClientServicePopup = [];
	var usersByClientService = {};
	usersByClientService.userDetailsList = function(userByClientTO, employeeFlag) {
		var deferred = $q.defer();
		usersByClientServicePopup = ngDialog.open({
			template : 'views/users/usersbyclient.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.EmpDetails = userByClientTO;
				$scope.employeeFlag = employeeFlag
				$scope.userlistpopup = function(userByClientTO, employeeFlag) {
					var returnPopObj = {
						"userByClientTO" : userByClientTO,
						"employeeFlag" : employeeFlag
					};
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	}, usersByClientService.getUserDetailsByClient = function(req) {
		var deferred = $q.defer();
		var userListPromise = UserService.getUsersByClientId(req)
		userListPromise.then(function(data) {
			var employeeFlag = false;
			var userByClientTO = [];
			if (data.labelKeyTOs == []) {
				employeeFlag = true;
			}
			userByClientTO = data.labelKeyTOs;
			var usersByClientPopUp = usersByClientService.userDetailsList(userByClientTO, employeeFlag);
			usersByClientPopUp.then(function(data) {
				var returnPopObj = {
					"userByClientTO" : data.userByClientTO,
					"employeeFlag" : data.employeeFlag
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Employee List Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Employee List Details", "Error");
		});
		return deferred.promise;
	}
	return usersByClientService;
}]);