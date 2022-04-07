'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("employeecontacts", {
		url : '/employeecontacts',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/contactdetails/empcontactdetails.html',
				controller : 'EmpContactsController'
			}
		}
	})
}]).controller("EmpContactsController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "$location", "EmpRegisterService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, $location, EmpRegisterService) {

	$scope.empContactDtlTOs = [];
	$scope.getEmpContactDetails = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the employee", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
		};
		EmpRegisterService.getEmpContactDetails(req).then(function(data) {
			$scope.empContactDtlTOs = data.empContactDtlTOs;
			if ($scope.empContactDtlTOs <= 0) {
				$scope.predefinedContactDetails();
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting employee contact details", 'Error');
		});
	}, $scope.getEmpContactDetails();
	$scope.predefinedContactDetails = function() {
		$scope.empContactDtlTOs.push({
			"empRegId" : $rootScope.selectedEmployeeData.id,
			"status" : 1
		});
	}, $scope.saveEmpContactDetails = function() {
		// if ($scope.contactDetailsForm.$valid) {
		// 	alert('Validations done successfully');
		// }
		var req = {
			"empContactDtlTOs" : $scope.empContactDtlTOs,
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
		};
		EmpRegisterService.saveEmpContactDetails(req).then(function(data) {
			// GenericAlertService.alertMessage('Employee Contact Detail(s) is/are ' + data.message, data.status);
			GenericAlertService.alertMessage('Employee Contact Detail(s) saved successfully',"Info");
			$scope.empContactDtlTOs = data.empContactDtlTOs;
		}, function(error) {
			GenericAlertService.alertMessage('Employee Contact Detail(s) is/are Failed to Save ', "Info");
		});
	},
	$scope.$on("resetEmpContact", function() {
		$scope.empContactDtlTOs = [];
	});
	
}]);