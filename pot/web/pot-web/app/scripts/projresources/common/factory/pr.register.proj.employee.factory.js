'use strict';
app.factory('EmployeeMasterDetailsFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "GenericAlertService", "EmpRegisterService", function(ngDialog, $q, $filter, $timeout, $rootScope, GenericAlertService,EmpRegisterService) {
	var employeeDetailsPopUp;
	var service = {};
	service.employeeDetails = function(empData) {
		var deferred = $q.defer();
		employeeDetailsPopUp = ngDialog.open({
			template : 'views/projresources/common/projectemployee.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.employeeData = empData;				
				$scope.empPopUp = function(empData) {
					var returnPopObj = {
						"empRegisterDtlTOs" : empData
					};			
					deferred.resolve(returnPopObj);
					$scope.closeThisDialog();
				}
			} ]

		});
		return deferred.promise;
	},

	service.getEmployeeMasterDetails = function(projId) {
		var deferred = $q.defer();
		var projectEmployeeDetailsPromise = EmpRegisterService.getProjEmployees(projId);
		projectEmployeeDetailsPromise.then(function(data) {
			var empData = [];
			empData = data.empRegisterDtlTOs;
			
			var projectEmployeeDetailsPopup = service.employeeDetails(empData);
			projectEmployeeDetailsPopup.then(function(data) {
				var returnPopObj = {
					"employeeMasterTO" : data.empRegisterDtlTOs
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage("Error occured while selecting Employee Details", 'Error');
			})
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting Employee Details", "Error");
		});
		return deferred.promise;
	}	
	return service;
}]);
