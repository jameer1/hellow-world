'use strict';
app.factory('EmpLeaveAttendenceFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpRegisterService", "ProjLeaveTypeService", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpRegisterService, ProjLeaveTypeService, GenericAlertService, generalservice) {
	var service = {};
	service.getEmpLeaveAttendanceDetails = function(actionType, editEmpLeaveAttendence, tableData) {

		var deferred = $q.defer();

		var req = service.openPopup(actionType, editEmpLeaveAttendence, tableData);
		req.then(function (data) {
			deferred.resolve(data);
		});

		return deferred.promise;
	}, service.openPopup = function(actionType, editEmpLeaveAttendence, tableData) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/projresources/projempreg/leave&attendence/empleaveattendancepopup.html',
			closeByDocument : false,
			showClose : false,
			className:'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.action = actionType;
				$scope.empLeaveAttendanceYearTOs = [];
				$scope.empLeaveAccuredTOs = [];
				$scope.tableData = tableData;
				$scope.yearTos = [];
				$scope.leaveCountValues = [];
				var currDate = new Date();
			//	var currYear = currDate.getFullYear();
                var currYear = currDate.getFullYear()+10;
                const enroll = new Date($rootScope.selectedEmployeeData.projEmpRegisterTO.enrollmentDate);
				var years = enroll.getFullYear();
			//	var pastYear = currYear - 10;
			    var pastYear = years;
				for (var i = pastYear; i <= currYear; i++) {
					$scope.yearTos.push(i)
				}
				angular.forEach(generalservice.empLeaveTypes, function(value, key) {
					if (value.catagory == 2) {
						$scope.empLeaveAccuredTOs.push({
							"leaveTypeTO" : value,
							"leaveType" : value.desc,
							"leaveCode" : value.code,
							"status" : 1
						});
					}
				});
				var selectedempLeaveAttendanceYearTOs = [];
				var addObject = {
					"select" : false,
					"status" : 1,
					"period" : 'Days',
					"empRegId" : $rootScope.selectedEmployeeData.id,
					"year" : null,
					"empLeaveAccuredTOs" : $scope.empLeaveAccuredTOs
				};
				if (actionType === 'Add') {
					$scope.empLeaveAttendanceYearTOs.push(angular.copy(addObject));
				} else {

					$scope.empLeaveAttendanceYearTOs = angular.copy(editEmpLeaveAttendence);

					editEmpLeaveAttendence = [];
				}

				$scope.addRows = function() {

					$scope.empLeaveAttendanceYearTOs.push(angular.copy(addObject));

				},

				$scope.selectEmpLeaveAttendance = function(empLeaveAttendenceTO) {
					if (empLeaveAttendenceTO.select) {
						selectedempLeaveAttendanceYearTOs.push(empLeaveAttendenceTO);
					} else {
						selectedempLeaveAttendanceYearTOs.pop(empLeaveAttendenceTO);
					}
				}, $scope.deleteRows = function() {
					if (selectedempLeaveAttendanceYearTOs.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
					} else if (selectedempLeaveAttendanceYearTOs.length < $scope.empLeaveAttendanceYearTOs.length) {
						angular.forEach(selectedempLeaveAttendanceYearTOs, function(value, key) {
							$scope.empLeaveAttendanceYearTOs.splice($scope.empLeaveAttendanceYearTOs.indexOf(value), 1);
						});
						selectedempLeaveAttendanceYearTOs = [];

					} else {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}, $scope.calculateEmpTotalLeaves = function(empLeaveAccuredTOs) {
					$scope.leaveCount = 0;

					angular.forEach(empLeaveAccuredTOs, function(value, key) {
						if (value.totalLeaves != undefined && value.totalLeaves != null) {
							$scope.leaveCount = $scope.leaveCount + parseFloat(value.totalLeaves || 0);
						}
					});
					$scope.leaveCountValues.push($scope.leaveCount);
					return $scope.leaveCount;
				}, $scope.calculateEmpTotalLeavesForYear = function(index) {
					$scope.total = 0;
					for (var i = 0; i <= index; i++) {
						$scope.total = $scope.total + parseFloat($scope.leaveCountValues[i]);
					}
					return $scope.total;
				}, $scope.saveEmpLeaveAttendance = function() {
					$scope.dates = [];
					angular.forEach($scope.empLeaveAttendanceYearTOs, function(value, key) {
						$scope.dates.push(value.year);
					});
					for (var i = 0; i < $scope.dates.length; i++) {
						if ($scope.dates[i] == $scope.dates[i + 1] || $scope.dates[i] == $scope.dates[i + 2]) {
							GenericAlertService.alertMessage('Please Select Different Years', "Warning");
							return;
						}
					}

					$scope.rowslength = tableData.length + $scope.empLeaveAttendanceYearTOs.length;

					if (actionType === 'Add') {

						if ($scope.rowslength > 3) {
							GenericAlertService.alertMessage('Can not Add more Than Three years', "Warning");
							return;
						}
						if ($scope.validateLeaveYears()) {
							return;
						}

					}

					var req = {
						"empLeaveAttendanceYearTOs" : $scope.empLeaveAttendanceYearTOs,
						"status" : 1,
						"empRegId" : $rootScope.selectedEmployeeData.id,
					}
					console.log(req,'reqqqq');
					EmpRegisterService.saveEmpLeaveAttendenceDtls(req).then(function(data) {

						// var succMsg = GenericAlertService.alertMessageModal('Employee Leave and Attendence Detail(s) is/are save successfully', 'Info');
						var succMsg = GenericAlertService.alertMessageModal('Employee Leave and Attendance Detail(s) saved successfully', 'Info');
						succMsg.then(function() {
							$scope.closeThisDialog();
							deferred.resolve(data);

						});
					}, function(error) {
						GenericAlertService.alertMessage('Employee Leave and Attendance Detail(s) is/are Failed to Save ', "Error");
					});
				}

				$scope.validateLeaveYears = function() {

					var flag1 = false;
					angular.forEach($scope.empLeaveAttendanceYearTOs, function(value, key) {
						$scope.date = value.year;

					});

					angular.forEach($scope.tableData, function(value, key) {
						$scope.date1 = value.year;

						if ($scope.date == $scope.date1) {
							flag1 = true;
							return;
						}

					});

					if (flag1) {
						GenericAlertService.alertMessage($scope.date + '  is already exists ', "Warning");
						return true;
					} else {
						return false;
					}
				}

			} ]
		});
		return deferred.promise;
	}
	return service;
}]);
