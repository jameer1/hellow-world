'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empnextofkin", {
		url : '/empnextofkin',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/nok/empnok.html',
				controller : 'EmpNextOfKinController'
			}
		}
	})
}]).controller("EmpNextOfKinController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "GenericAlertService", "$location", "EmpRegisterService", function($rootScope, $scope, $q, $state, ngDialog, GenericAlertService, $location, EmpRegisterService) {

	$scope.empNokTOs = [];

	$scope.getEmpNOKDetails = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the employee", "Warning");
			return;
		}

		if ($rootScope.selectedEmployeeData.projId == null && $rootScope.selectedEmployeeData.projId == undefined) {
			return;
		}
		var req = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
			"projId" : $rootScope.selectedEmployeeData.projId
		};
		EmpRegisterService.getEmpNok(req).then(function(data) {
			$scope.empNokTOs = data.empNokTOs;
			if ($scope.empNokTOs <= 0) {
				$scope.predefinedNokDetails();
			}
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting  Next Of Kin Details", 'Error');
		});

	}, $scope.getEmpNOKDetails();

	$scope.predefinedNokDetails = function() {
		$scope.empNokTOs.push({
			"contactType" : 'Primary',
			"empRegId" : $rootScope.selectedEmployeeData.id,
			"status" : 1
		}, {
			"contactType" : 'Secondary',
			"empRegId" : $rootScope.selectedEmployeeData.id,
			"status" : 1
		}, {
			"contactType" : 'Tertiary',
			"empRegId" : $rootScope.selectedEmployeeData.id,
			"status" : 1
		});
	}, $scope.saveEmpNOKDetails = function() {
		var req = {
			"empNokTOs" : $scope.empNokTOs,
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id
		};
		EmpRegisterService.saveEmpNok(req).then(function(data) {
			// GenericAlertService.alertMessage('Employee Next Of kin Detail(s) is/are  ' + data.message, data.status);
			GenericAlertService.alertMessage('Employee Next of Kin Detail(s) saved successfully  ',"Info" );
			$scope.empNokTOs = data.empNokTOs;
		}, function(error) {
			GenericAlertService.alertMessage('Employee Next Of kin  Detail(s) is/are Failed to Save ', "Info");
		});
	},
	$scope.$on("resetNOK", function() {
		$scope.empNokTOs = [];
	});
}]);