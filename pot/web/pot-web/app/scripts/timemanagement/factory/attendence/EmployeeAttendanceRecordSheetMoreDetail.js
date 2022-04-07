'use strict';

app.factory('EmployeeAttendanceRecordSheetMoreDetail', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "EmpAttendanceService", "EmpNotificationFactory", "AttendanceEmpRegFactory", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, $timeout, blockUI, EmpAttendanceService, EmpNotificationFactory, AttendanceEmpRegFactory, GenericAlertService, generalservice) {
	var attendancePopup;
	var service = {};
	service.openPopup = function(empAttendanceMstrTO) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/attendance/employeeattendancerecordsheetmoredetail.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			controller : [ '$scope', function($scope) {
				$scope.clickForward = function (moreFlag) {
					if ($scope.moreFlag <= 31) {
						$scope.moreFlag = moreFlag + 7;
					}
				}, $scope.clickBackward = function (moreFlag) {
					if ($scope.moreFlag > 0) {
						$scope.moreFlag = moreFlag - 7;
					}
				},
				$scope.moreFlag = 0;
				empAttendanceMstrTO.attendenceMonth = $filter('date')(empAttendanceMstrTO.attendenceMonth, "MMM-yyyy");
				$scope.empAttendanceMstrTO = empAttendanceMstrTO;
				$scope.empLeaveTypes = generalservice.empLeaveTypes;
				var attendReq = {
					"status": 1,
					"projId": empAttendanceMstrTO.projId,
					"crewId": empAttendanceMstrTO.crewId,
					"attendenceMonth": empAttendanceMstrTO.attendenceMonth,
					"attendenceId": empAttendanceMstrTO.id
				};
				EmpAttendanceService.getEmpAttendanceDetails(attendReq).then(function (data) {
					$scope.empAttendenceDetails = data.empAttendenceTOs;
					$scope.empDemobilizationDateMap = data.empDemobilizationDateMap;

					angular.forEach($scope.empAttendenceDetails, function (value, key) {
						var totalCount = 0;
						angular.forEach(value.empAttendenceDtlMap, function (value1, key1) {
							if (value1.attendanceTypeTO.code) {
								totalCount += 1;
							}
						});
						value.totalCount = totalCount;
					});
					if ($scope.empAttendenceDetails.length <= 0) {
						GenericAlertService.alertMessage("Employees are not available for the selected crew", 'Warning');
					}
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while gettting  Employees Attendance details", 'Error');
				});
				var getEmpAttendReq = {
					"status": 1,
					"projId": empAttendanceMstrTO.projId,
					"crewId": empAttendanceMstrTO.crewId,
					"attendenceMonth": empAttendanceMstrTO.attendenceMonth,
				};
				EmpAttendanceService.getAttendanceOnLoad(getEmpAttendReq).then(function (data) {
					$scope.empAttendanceDays = data.attendenceDays;
					$scope.attendenceDayMap = data.attendenceDayMap;
				}, function (error) {
					GenericAlertService.alertMessage("Error occured while gettting Attendance Month", 'Error');
				});
				$scope.saveEmpAttendanceRecords = function () {
					$scope.empFlag = false;
					var attendReq = {
						"status": 1,
						"projId": empAttendanceMstrTO.projId,
						"crewId": empAttendanceMstrTO.crewId,
						"attendenceId": empAttendanceMstrTO.id,
						"attendenceMonth": empAttendanceMstrTO.attendenceMonth,
						"empAttendenceTOs": $scope.empAttendenceDetails
					};
					blockUI.start();
					EmpAttendanceService.saveEmpAttendanceRecords(attendReq).then(function (data) {
						blockUI.stop();
						GenericAlertService.alertMessage("Attendance saved successfully", 'Info');
						$scope.closeThisDialog();
					}, function (error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while gettting Employees Attendance  details", 'Error');
					});
				},
				$scope.requestForAddtinalTime = function () {
					var searchProject = {
							projId: empAttendanceMstrTO.projId,
							projName: empAttendanceMstrTO.name
					};
					var empAttendReq = {
						"status": 1,
						"crewLabelKeyTO": {
							"id": empAttendanceMstrTO.crewId,
							"code": empAttendanceMstrTO.crewName,
							"name": null
						},
						"attendenceId": null,
						"attendenceCode": null,
						"attendenceMonth": empAttendanceMstrTO.attendenceMonth
					};
					var popup = EmpNotificationFactory.getEmpNotificationDetails(searchProject, empAttendReq);
					popup.then(function (data) {
					}, function (error) {
						GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
					})
				},
				$scope.addEmployeeDetails = function () {
					if ($scope.empFlag) {
						GenericAlertService.confirmMessageModal('Do you want to Save Changes', 'Warning', 'YES', 'NO').then(function () {
							$scope.saveEmpAttendanceRecords();
						}, function (error) {
							$scope.empFlag = false;
							$scope.addEmployeeDetails(empAttendReq, projId);
						})
						return;
					}
					var empPopup = [];
					var empExistingMap = [];
					angular.forEach($scope.empAttendenceDetails, function (value, key) {
						empExistingMap[value.empId] = true;
					});
					var empAttendReq = {
						"status": 1,
						"crewLabelKeyTO": {
							"id": empAttendanceMstrTO.crewId,
							"code": empAttendanceMstrTO.crewName,
							"name": null
						},
						"attendenceId": null,
						"attendenceCode": null,
						"attendenceMonth": empAttendanceMstrTO.attendenceMonth
					};
					empPopup = AttendanceEmpRegFactory.getEmpRegDetails(empExistingMap, empAttendReq, empAttendanceMstrTO.projId);
					empPopup.then(function (data) {
						$scope.empFlag = true;
						angular.forEach(data.empAttendenceTOs, function (value, key) {
							$scope.empAttendenceDetails.push(value);
						});
					}, function (error) {
						GenericAlertService.alertMessage("Error occured while gettting  Employee Data", 'Error');
					});
				}
				
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);