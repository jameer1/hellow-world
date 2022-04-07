'use strict';

app.factory('EmpAttendSheetsPopUpFactory', ["ngDialog", "$q", "$filter", "$timeout", "EmpAttendanceService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, EmpAttendanceService, GenericAlertService) {
	var attendancePopup;
	var service = {};
	service.getEmpAttendenceSheets = function(projId,crewId) {
		var deferred = $q.defer();
		var getEmpAttendReq = {
			"status" : 1,
			"projId" : projId,
			"crewId" : crewId
		};
		EmpAttendanceService.getEmpAttendanceSheets(getEmpAttendReq).then(function(data) {
			var popupdata = service.openPopup(data.empRegLabelKeyTOs,projId);
			popupdata.then(function(data) {
				var returnPopObj = {
					"attendanceObj" :data.attendanceObj,
					"attendenceDays":data.attendenceDays
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage('An error occurred while getting attendance sheets', "Error");
			});

		});
		return deferred.promise;

	}, service.openPopup = function(empRegLabelKeyTOs,projId) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/attendance/empattendancesheets.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.empAttendenceSheets = empRegLabelKeyTOs;
				$scope.getMonthNames = function(dt) {
					var monthList = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
					return monthList[dt.getMonth()];
				}, $scope.getMonthDateFormat = function(attendenceMonth) {
					var date = new Date(attendenceMonth);
					var monthName = $scope.getMonthNames(date);
					return (monthName + "-" + date.getFullYear());
				}, 
				$scope.selectedAttendanceId = function(attendanceObj) {
					var req = {
						"status" : 1,
						"projId" : projId,
						"attendenceMonth" : $scope.getMonthDateFormat(attendanceObj.name)
					};
					EmpAttendanceService.getAttendanceDays(req).then(function(data) {
						var returnPopObj = {
							"attendanceObj" : attendanceObj,
							"attendenceDays":data.attendenceDays
						};
						deferred.resolve(returnPopObj);
						$scope.closeThisDialog();

					}, function(error) {
						GenericAlertService.alertMessage('An error occurred while getting attendance sheets', "Error");
					});
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);