'use strict';

app.factory('PlantAttendSheetsFactory', ["ngDialog", "$q", "$filter", "$timeout", "PlantAttendanceService", "EmpAttendanceService", "GenericAlertService", function(ngDialog, $q, $filter, $timeout, PlantAttendanceService, EmpAttendanceService, GenericAlertService) {
	var service = {};
	service.getPlantAttendenceSheets = function(projId, crewId) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId,
			"crewId" : crewId
		};
		PlantAttendanceService.getPlantAttendanceSheets(req).then(function(data) {
			var popup = service.openPopup(data.plantRegLabelKeyTOs);
			popup.then(function(data) {
				var returnPopObj = {
					"attendanceObj" : data.attendanceObj,
					"attendenceDays" : data.attendenceDays
				};
				deferred.resolve(returnPopObj);
			}, function(error) {
				GenericAlertService.alertMessage('An error occurred while fetching Employee details', "Error");
			});
		});
		return deferred.promise;
	}, service.openPopup = function(plantRegLabelKeyTOs) {
		var deferred = $q.defer();
		var plantAttendPopUp = ngDialog.open({
			template : 'views/timemanagement/attendance/plantattendancesheets.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			controller : [ '$scope', function($scope) {
				$scope.plantAttendenceSheets = plantRegLabelKeyTOs;
				$scope.getMonthNames = function(dt) {
					var monthList = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
					return monthList[dt.getMonth()];
				}, $scope.getMonthDateFormat = function(attendenceMonth) {
					var date = new Date(attendenceMonth);
					var monthName = $scope.getMonthNames(date);
					return (monthName + "-" + date.getFullYear());
				}, $scope.selectedAttendanceId = function(attendanceObj) {
					var req = {
						"status" : 1,
						"attendenceMonth" : $scope.getMonthDateFormat(attendanceObj.name)
					};
					EmpAttendanceService.getAttendanceDays(req).then(function(data) {
						var returnPopObj = {
							"attendanceObj" : attendanceObj,
							"attendenceDays" : data.attendenceDays
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