'use strict';

app.config(["$stateProvider", function($stateProvider) {
	$stateProvider.state("empleave&attend", {
		url : '/empleave&attend',
		data : {
			roles : []
		},
		views : {
			'content@' : {
				templateUrl : 'views/projresources/projempreg/leave&attendence/empleaveattendance.html',
				controller : 'EmpAttendanceController'
			}
		}
	})
}]).controller("EmpAttendanceController", ["$rootScope", "$scope", "$q", "$state", "ngDialog", "EmpLeaveAttendenceFactory", "EmpLeaveBalanceFactory", "GenericAlertService", "$location", "EmpRegisterService", "generalservice", function($rootScope, $scope, $q, $state, ngDialog, EmpLeaveAttendenceFactory, EmpLeaveBalanceFactory,GenericAlertService, $location, EmpRegisterService, generalservice) {

	$scope.empLeaveAttendanceYearTOs = [];
	$scope.empLeaveAccuredTOs = [];
	$scope.empYearWiseLeaveCountMap = [];
	$scope.leaveCodeMap = generalservice.empLeaveTypes;
	angular.forEach($scope.leaveCodeMap, function(value, key) {
		if (value.catagory == 2) {
			$scope.empLeaveAccuredTOs.push(value);
		}
	});

	var editEmpLeaveAttendence = [];
	
	$scope.getempLeaveAttendanceYearTOs = function() {
		if ($rootScope.selectedEmployeeData == null || $rootScope.selectedEmployeeData == undefined) {
			GenericAlertService.alertMessage("Please select the Employee", "Warning");
			return;
		}
		var req = {
			"status" : 1,
			"empId" : $rootScope.selectedEmployeeData.id,
		};
		EmpRegisterService.getEmpLeaveAttendenceDtls(req).then(function(data) {			
			$scope.empLeaveAttendanceYearTOs = data.empLeaveAttendanceYearTOs;
			$scope.empYearWiseLeaveCountMap = data.empYearWiseLeaveCountMap;
		}, function(error) {
			GenericAlertService.alertMessage("Error occured while getting leave and attendance details", 'Error');
		});
	}, $scope.getempLeaveAttendanceYearTOs();
	$scope.empAttendanceRowSelect = function(attendance) {
		if (attendance.select) {
			editEmpLeaveAttendence.push(attendance);
		} else {
			editEmpLeaveAttendence.pop(attendance);
		}
	}, $scope.calculateEmpLeaveAttendanceTotal = function(empLeaveAttendanceTOs) {
		var leaveCount = 0;
		angular.forEach(empLeaveAttendanceTOs, function(value, key) {
			leaveCount = leaveCount + parseFloat(value.displayNamesMap['total'] || 0);
		});
		return leaveCount;
	}, $scope.calculateEmpTotalLeaves = function(empLeaveAccuredTOs) {
		var leaveCount = 0;
		angular.forEach(empLeaveAccuredTOs, function(value, key) {
			leaveCount = leaveCount + parseFloat(value.totalLeaves || 0);
		});
		return leaveCount;
	}, 	
	$scope.addEmpLeaveAttendance = function(actionType) {	
		
		if ($rootScope.selectedEmployeeData != undefined && $rootScope.selectedEmployeeData != null) {
		if (actionType == 'Edit' && editEmpLeaveAttendence <= 0) {
			GenericAlertService.alertMessage("Please select atleast one row to modify", 'Warning');
			return;
		}

		var leaveAttendencePopUp = EmpLeaveAttendenceFactory.getEmpLeaveAttendanceDetails(actionType, editEmpLeaveAttendence,$scope.empLeaveAttendanceYearTOs);
		leaveAttendencePopUp.then(function(data) {
			$scope.empLeaveAttendanceYearTOs=data.empLeaveAttendanceYearTOs;
			$scope.getempLeaveAttendanceYearTOs();
			editEmpLeaveAttendence =[];
		
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while fetching emp attendence details", 'Error');
		})
		}
	}, $scope.viewEmpLeaveBalanceDetails = function(empLeaveAttendenceYearTO) {
	
		var popups = EmpLeaveBalanceFactory.getEmpLeaveBalanceDetails($scope.empLeaveAccuredTOs, empLeaveAttendenceYearTO);
		popups.then(function(data) {
		}, function(error) {
			GenericAlertService.alertMessage("Error occurred while getting employee leave balance details", 'Error');
		});
	}

}]);
