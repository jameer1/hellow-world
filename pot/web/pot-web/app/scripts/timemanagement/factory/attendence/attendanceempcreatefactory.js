'use strict';
app.factory('AttendanceEmp1CreateFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "EmpAttendanceService", "EmpCopyPopupFactory", "EmpAttendenaceLeaveFactory", "AttendanceEmpRegFactory", "GenericAlertService", "generalservice", function(ngDialog, $q, $filter, blockUI,$timeout, $rootScope,EmpAttendanceService,
		EmpCopyPopupFactory, EmpAttendenaceLeaveFactory, AttendanceEmpRegFactory, GenericAlertService, generalservice) {
	var dateWisePopUp;
	var createEmpservice = {};

	createEmpservice.createAttendanceRecords = function(searchProject, empAttendReq, empAttendanceDays, attendenceDayMap, empLeaveTypeMap, empDetailsMap) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/attendance/empcreateattendencepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empAttendenceDetails = [];
				var selectedEmpList =[];
				$scope.empLeaveTypes = generalservice.empLeaveTypes;
				$scope.projId = searchProject.projId;
				$scope.parentName = searchProject.parentName;
				$scope.projName = searchProject.projName;

				$scope.crewName = empAttendReq.crewLabelKeyTO.code;
				$scope.attendenceMonth = empAttendReq.attendenceMonth;
				$scope.empAttendReq=empAttendReq;
				
				$scope.empAttendanceDays = empAttendanceDays;
				$scope.attendenceDayMap = attendenceDayMap;
				$scope.empLeaveTypeMap = empLeaveTypeMap;
				$scope.empDetailsMap = empDetailsMap;
				$scope.empAttendenceSheets = [];

				$scope.moreFlag = 0;
				$scope.lessFlag = 0;

				$scope.getEmpCopyTemplate = function() {
					var empExistingMap = [];
					var empIds = [];
					angular.forEach($scope.empAttendenceDetails, function(value, key) {
						empExistingMap[value.empId] = true;
						empIds.push(value.empId);
					});
					var popup = EmpCopyPopupFactory.copyAttendanceEmpDetails($scope.projId, empAttendReq,empExistingMap);
					popup.then(function(data) {
						var showIgnoringDuplicateMessage = false;
						angular.forEach(data.empAttendenceTOs, function(value, key) {
							if (!empIds.includes(value.empId))
								$scope.empAttendenceDetails.push(value);
							else
								showIgnoringDuplicateMessage = true;
						});
						if (showIgnoringDuplicateMessage)
							GenericAlertService.alertMessage("Duplicate employee(s) were ignored", 'Warning');
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
					})
				},

				$scope.addEmployeeDetails = function() {

					var empPopup = [];
					var empExistingMap = [];
					angular.forEach($scope.empAttendenceDetails,function(value,key)
						{
						empExistingMap[value.empId]=true;
						});
					empPopup = AttendanceEmpRegFactory.getEmpRegDetails(empExistingMap,empAttendReq, $scope.projId);
					empPopup.then(function(data) {
						angular.forEach(data.empAttendenceTOs,function(value,key)
							{
							$scope.empAttendenceDetails.push(value);
							});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting  Employee Data", 'Error');
					});
				},$scope.saveEmpAttendanceRecords = function() {
					if ($scope.empAttendenceDetails <= 0 ) {
						GenericAlertService.alertMessage("please add atleast one employee to save  ", "Warning");
						return;
					}
					var attendReq = {
						"status" : 1,
						"projId" : $scope.projId,
						"crewId" : $scope.empAttendReq.crewLabelKeyTO.id,
						"attendenceId" : $scope.empAttendReq.attendenceId,
						"attendenceMonth" : $scope.attendenceMonth,
						"empAttendenceTOs" : $scope.empAttendenceDetails
					};
					blockUI.start();
					EmpAttendanceService.saveEmpAttendanceRecords(attendReq).then(function(data) {
						blockUI.stop();
						var resultData= GenericAlertService.alertMessageModal("Attendance saved successfully", 'Info');
						resultData.then(function() {
							angular.forEach(data.empAttendenceTOs, function(value, key) {
								angular.forEach(value.empAttendenceDtlMap, function(value1, key1) {
									if($scope.empLeaveTypeMap[value1.attendanceTypeTO.id]!=null){
									value1.attendanceTypeTO.code = $scope.empLeaveTypeMap[value1.attendanceTypeTO.id].code;
								}
								});
							});
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while gettting Employees Attendance  details", 'Error');
					});

				},

				$scope.clickForward = function(moreFlag) {
					if ($scope.moreFlag <= 31) {
						$scope.moreFlag = moreFlag + 7;
					}
				}, $scope.clickBackward = function(moreFlag) {
					if ($scope.moreFlag > 0) {
						$scope.moreFlag = moreFlag - 7;
					}
				},
				 $scope.rowselect = function(empAttendence) {
					if (empAttendence.select) {
						selectedEmpList.push(empAttendence);

					} else

					{
						selectedEmpList.splice(selectedEmpList.indexOf(empAttendence), 1);
					}
				},
				$scope.deleteEmpRecords = function() {
					if (selectedEmpList.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");
						
					}else
					
					if (selectedEmpList.length < $scope.empAttendenceDetails.length) {
						angular.forEach(selectedEmpList, function(value, key) {
							$scope.empAttendenceDetails.splice($scope.empAttendenceDetails.indexOf(value), 1);

						});
						selectedEmpList=[];
					GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
					} else if(selectedEmpList.length > 1){
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}else if(selectedEmpList.length == 1){
						selectedEmpList=[];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	}
	return createEmpservice;
}]);
