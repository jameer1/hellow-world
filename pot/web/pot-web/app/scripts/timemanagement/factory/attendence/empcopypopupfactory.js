'use strict';

app.factory('EmpCopyPopupFactory', ["ngDialog", "$q", "$filter", "$timeout", "$rootScope", "EmpAttendanceService", "ProjectCrewPopupService", "EmpAttendSheetsPopUpFactory", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, $rootScope, EmpAttendanceService, ProjectCrewPopupService, EmpAttendSheetsPopUpFactory, GenericAlertService) {
	var service = {};
	service.copyAttendanceEmpDetails = function(projId, empAttendReq, empRegLabelKeyTOs, empExistingMap) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/timemanagement/attendance/empattendencecopy.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				$scope.empExistingMap = empExistingMap;
				$scope.crewLabelKeyTO = {
						"id" : null,
						"code" : null,
						"name" : null
				}
				$scope.searchFlag = false;
				$scope.attendanceKeyTO = {
					attendanceId : null,
					attendanceCode : null
				}
				
				var toMonth = new Date(empAttendReq.attendenceMonth);;
				$scope.attendanceKeyTO.attendanceCode=toMonth;
				$scope.attendanceKeyTO.attendanceCode.setMonth(toMonth.getMonth()-1);
				
				$scope.selectAll = false;
				$scope.crewLabelKeyTO.code=empAttendReq.crewLabelKeyTO.code;
				
				$scope.empRegLabelKeyTOs = [];
				var selectedEmpLabelKeyTOs = [];
				
				$scope.crewLabelKeyTO = [];
				$scope.crewLabelKeyTO = angular.copy(empAttendReq.crewLabelKeyTO);
				
				$scope.getMonthNames = function(dt) {
					var monthList = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
					return monthList[dt.getMonth()];
				}, $scope.getMonthDateFormat = function(attendenceMonth) {
					var date = new Date(attendenceMonth);
					var monthName = $scope.getMonthNames(date);
					return (monthName + "-" + date.getFullYear());
				},$scope.getCrewList = function(crewLabelKeyTO) {
					var crewReq = {
						"status" : 1,
						"projId" : projId
					};
					var crewSerivcePopup = ProjectCrewPopupService.getCrewPopup(crewReq);
					crewSerivcePopup.then(function(data) {
						crewLabelKeyTO.id = data.projCrewTO.id;
						crewLabelKeyTO.code = data.projCrewTO.code;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while getting Crew  Details", 'Error');
					});

				}, $scope.getEmpAttendanceSheets = function() {
					if ($scope.crewLabelKeyTO.id <= 0) {
						GenericAlertService.alertMessage("Please select crew ID to get employee details", 'Error');
						return;
					}
					var empAttendPopUp = [];
					empAttendPopUp = EmpAttendSheetsPopUpFactory.getEmpAttendenceSheets(projId, $scope.crewLabelKeyTO.id);
					empAttendPopUp.then(function(data) {
						$scope.attendanceKeyTO.attendanceCode = data.attendanceObj.code;
						$scope.attendanceKeyTO.attendanceId = data.attendanceObj.id;
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting attendence sheet", 'Error');
					});
				}, $scope.getEmpDetails = function() {
					var x = angular.element(document.getElementById("prevMonth")); 
					$scope.textbox = x.val();
					var attendanceReq = {
						"status" : 1,
						"projId" : projId,
						"crewId" : $scope.crewLabelKeyTO.id,
						"crewCode" : $scope.crewLabelKeyTO.code,
						"currCrewId" : $scope.crewLabelKeyTO.id,
						"attendenceId" : $scope.attendanceKeyTO.attendanceId,
						"attendenceMonth" : $scope.textbox,
						"attendenceMonth1" : empAttendReq.attendenceMonth,
						"attendanceKeyTO" : $scope.attendanceKeyTO.attendanceCode
					};
					var empResults = EmpAttendanceService.copyAttendanceEmpDetails(attendanceReq);
					empResults.then(function(data) {
						$scope.empRegLabelKeyTOs = data.empRegLabelKeyTOs;
						if ($scope.empRegLabelKeyTOs.length <= 0) {
							$scope.searchFlag = true;
						} else {
							$scope.searchFlag = false;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting attendence sheet", 'Error');
					});
				}, $scope.selectEmpRegDtls = function(empRegLabelKeyTO) {
					if (empRegLabelKeyTO.select) {
						selectedEmpLabelKeyTOs.push(empRegLabelKeyTO);
					} else {
						selectedEmpLabelKeyTOs.splice(selectedEmpLabelKeyTOs.indexOf(empRegLabelKeyTO),1);
					}
				}, $scope.selectAllEmps = function() {
					if ($scope.selectAll) {
						angular.forEach($scope.empRegLabelKeyTOs, function(value, key) {
							value.select = true;
							selectedEmpLabelKeyTOs.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.empRegLabelKeyTOs, function(value, key) {
							value.select = false;
						});
						selectedEmpLabelKeyTOs = [];
					}
				}, $scope.saveAttendanceEmpDetails = function(empRegisterDtlTO) {
					var empRegList = [];
					angular.forEach(selectedEmpLabelKeyTOs, function(value, key) {
						empRegList.push({
							"attandanceId" : empAttendReq.attendenceId,
							"empId" : value.id,
							"crewId" : empAttendReq.crewLabelKeyTO.id,
							"status" : 1
						});
					});
					
					if (empRegList.length <= 0) {
						GenericAlertService.alertMessage('Please select Atleast one row', "Warning");
						return;
					}
					
					var saveReg = {
						"empAttendenceTOs" : empRegList,
						"status" : 1,
						"projId" : projId,
						"crewId" : empAttendReq.crewLabelKeyTO.id,
						"attendenceId" : empAttendReq.attendenceId,
						"attendenceMonth" : $scope.getMonthDateFormat(empAttendReq.attendenceMonth)
					};
					EmpAttendanceService.addEmpToAttendanceRecord(saveReg).then(function(data) {
						angular.forEach(data.empAttendenceTOs, function(value) {
							for (const empReg of selectedEmpLabelKeyTOs) {
								if (value.empId === empReg.id) {
									value.empCode = empReg.code;
									value.empClassType = empReg.displayNamesMap.classType;
									value.empCmpCatgName = empReg.displayNamesMap.cmpCatgName;
									value.empFirstName = empReg.displayNamesMap.firstName;
									value.gender = empReg.displayNamesMap.gender;
									value.empLastName = empReg.displayNamesMap.lastName;
									value.procureCatg = empReg.displayNamesMap.procureCatg;
									break;
								}
							}
						});
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function(error) {
						GenericAlertService.alertMessage('An error occurred while adding Employee details', "Error");
					});
				}

			} ]
		});
		return deferred.promise;

	}
	return service;

}]);
