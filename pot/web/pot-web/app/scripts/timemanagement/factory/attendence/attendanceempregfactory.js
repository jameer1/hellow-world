'use strict';

app.factory('AttendanceEmpRegFactory', ["ngDialog", "$q", "$rootScope", "$filter", "$timeout", "EmpAttendanceService", "GenericAlertService", function(ngDialog, $q, $rootScope, $filter, $timeout, EmpAttendanceService, GenericAlertService) {
	var service = {};

	service.getEmpRegDetails = function(empExistingMap,empAttendReq, projId) {

		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId,
			"crewId" : empAttendReq.crewLabelKeyTO.id,
			"attendenceId" : empAttendReq.attendenceId,
			"attendenceMonth" : empAttendReq.attendenceMonth,
		};
		EmpAttendanceService.getNonAttendanceEmpRegDetails(req).then(function(data) {
			var empDtlsPopup = service.openPopup(empExistingMap,data.empRegLabelKeyTOs, empAttendReq, projId);
			empDtlsPopup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage('An error occurred while fetching Employee details', "Error");
			});
		}, function(error) {
			GenericAlertService.alertMessage('An error occurred while fetching Employee details', "Error");
		});
		return deferred.promise;

	}, service.openPopup = function(empExistingMap ,empRegLabelKeyTOs, empAttendReq, projId) {
		var deferred = $q.defer();
		var empPopup = ngDialog.open({
			template : 'views/timemanagement/attendance/attendanceempregdtl.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.empRegLabelKeyTOs = empRegLabelKeyTOs;
				var selectedEmpRegTOs = [];
				$scope.empExistingMap=empExistingMap;
				$scope.getMonthNames = function(dt) {
					var monthList = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
					return monthList[dt.getMonth()];
				}, $scope.getMonthDateFormat = function(attendenceMonth) {
					var date = new Date(attendenceMonth);
					var monthName = $scope.getMonthNames(date);
					return (monthName + "-" + date.getFullYear());
				}, $scope.selectEmpRegDtls = function(empRegLabelKeyTO) {
					if (empRegLabelKeyTO.select) {
						selectedEmpRegTOs.push(empRegLabelKeyTO);
					} else {
						selectedEmpRegTOs.splice(selectedEmpRegTOs.indexOf(empRegLabelKeyTO), 1);
					}
				}, $scope.addEmpRegToAttendance = function(empRegisterDtlTO) {
					var empRegList = [];
					angular.forEach(selectedEmpRegTOs, function(value, key) {
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
							for (const empReg of selectedEmpRegTOs) {
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
					$scope.closeThisDialog();
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);