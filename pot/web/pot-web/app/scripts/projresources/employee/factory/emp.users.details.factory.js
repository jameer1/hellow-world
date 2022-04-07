'use strict';
app.factory('EmpReqApprUserFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "PotCommonService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService, PotCommonService) {
	var service = {};
	service.getEmpUsers = function(req) {
		var deferred = $q.defer();
		PotCommonService.getEmpUsersOnly(req).then(function(data) {
			var popupData = service.openPopup(data.labelKeyTOs);
			popupData.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting employee details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  employee details", "Error");
		});
		return deferred.promise;
	}, service.openPopup = function(empUsers) {
		var deferred = $q.defer();
		var employeeDetailsPopUp = ngDialog.open({
			template : 'views/projresources/projempreg/emptransfer/empuserspopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.empUsers = empUsers;
				var selectedEmployees = [];
				$scope.selectUser = function(selectedUserTO) {
					deferred.resolve(selectedUserTO);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	}
	return service;
}]);
