'use strict';

app.factory('ApproveUserFactory', ["ngDialog", "$q", "PotCommonService", "GenericAlertService", function(ngDialog, $q, PotCommonService, GenericAlertService) {
	var service = {}; 
	service.getProjUsersOnly = function(getReq) {
		var deferred1 = $q.defer();

		PotCommonService.getEmpUsersOnly(getReq).then(function(data) {
			var popupData = service.openUserPopup(data.labelKeyTOs);
			popupData.then(function(data) {
				var returnPopObj = {
					"approverTo" : data
				};
				deferred1.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while gettting Approvers", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while gettting Approvers", 'Error');
		});
		
		return deferred1.promise;

	}, service.openUserPopup = function(userList) {
		var deferred = $q.defer();
		var approverUserPopup = ngDialog.open({
			template : 'views/common/approveusers.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom3',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.userList = userList;
				$scope.selectedUser = function(approverTo) {
					deferred.resolve(approverTo);
					$scope.closeThisDialog();
				}

			} ]
		});
		return deferred.promise;
	};
	return service;

}]);