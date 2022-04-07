'use strict';

app.factory('PlantAttendanceRecordSheetMoreDetail', ["ngDialog", "$q", "$filter", "$timeout", "blockUI", "GenericAlertService", "generalservice", "PlantAttendanceService", "AttendancePlantRegFactory", "PlantNotificationFactory", function(ngDialog, $q, $filter, $timeout, blockUI, GenericAlertService, generalservice, PlantAttendanceService, AttendancePlantRegFactory, PlantNotificationFactory) {
	var attendancePopup;
	var service = {};
	service.openPopup = function(plantAttendanceMstrTO) {
		var deferred = $q.defer();
		attendancePopup = ngDialog.open({
			template : 'views/timemanagement/attendance/plantattendancerecordsheetmoredetail.html',
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
				plantAttendanceMstrTO.attendanceMonth = $filter('date')(plantAttendanceMstrTO.attendanceMonth, "MMM-yyyy");
				$scope.plantAttendanceMstrTO = plantAttendanceMstrTO;
				$scope.plantAttendanceDaysDD = generalservice.plantAttendanceTypes;
				var attendReq = {
					"status" : 1,
					"projId" : plantAttendanceMstrTO.projId,
					"crewId" : plantAttendanceMstrTO.crewId,
					"attendenceMonth" : plantAttendanceMstrTO.attendanceMonth,
					"attendenceId" : plantAttendanceMstrTO.id
				};
				PlantAttendanceService.getPlantAttendanceDetails(attendReq).then(function(data) {
					$scope.plantAttendenceDetails = data.plantAttendenceTOs;
					$scope.plantDemobilizationDateMap=data.plantDemobilizationDateMap;
					angular.forEach($scope.plantAttendenceDetails, function(value, key) {
						var totalCount = 0;
						angular.forEach(value.plantAttendenceDtlMap, function(value1, key1) {
							if (value1.attendanceTypeTO.code) {
								totalCount += 1;
							}
						});
						value.totalCount = totalCount;
					});
					if ($scope.plantAttendenceDetails.length <= 0) {
						GenericAlertService.alertMessage("Plants are not avaialble for the selected project", 'Warning');
					}
				}, function(error) {
					GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
				});
				var getPlantAttendReq = {
					"status" : 1,
					"projId" : plantAttendanceMstrTO.projId,
					"crewId" : plantAttendanceMstrTO.crewId,
					"attendenceMonth" : plantAttendanceMstrTO.attendanceMonth
				};
				PlantAttendanceService.getAttendanceOnLoad(getPlantAttendReq).then(function(data) {
					$scope.plantAttendanceDays = data.attendenceDays;
					$scope.attendenceDayMap = data.attendenceDayMap;
				},
				function(error) {
					GenericAlertService.alertMessage("Error occured while gettting  Attendance ID", 'Error');
				});
				$scope.savePlantAttendanceRecords = function() {
					$scope.plantFlag = false;
					var attendReq = {
						"status" : 1,
						"projId" : plantAttendanceMstrTO.projId,
						"crewId" : plantAttendanceMstrTO.crewId,
						"attendenceId" : plantAttendanceMstrTO.id,
						"attendenceMonth" : plantAttendanceMstrTO.attendanceMonth,
						"plantAttendenceTOs" : $scope.plantAttendenceDetails
					};
					blockUI.start();
					PlantAttendanceService.savePlantAttendanceRecords(attendReq).then(function(data) {
						blockUI.stop();
						GenericAlertService.alertMessage("Attendance saved successfully", 'Info');
						$scope.closeThisDialog();
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
					});
				},
				$scope.addPlantRegDetails = function() {
					var plantPopUp = [];
					var plantExistingMap = [];
					angular.forEach($scope.plantAttendenceDetails,function(value,key) {
						plantExistingMap[value.plantId]=true;
					});
					var plantAttendReq = {
						"status" : 1,
						"crewLabelKeyTO" : {
							"id" : plantAttendanceMstrTO.crewId,
							"code" : null,
							"name" : null
						},
						"attendenceId" : plantAttendanceMstrTO.id,
						"attendenceCode":null,
						"attendenceMonth" : plantAttendanceMstrTO.attendanceMonth
					};
					plantPopUp = AttendancePlantRegFactory.getPlantRegDetails(plantExistingMap, plantAttendanceMstrTO.projId, plantAttendReq);
					plantPopUp.then(function(data) {
						angular.forEach(data.plantAttendenceTOs,function(value,key) {
							$scope.plantAttendenceDetails.push(value);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting  Plants", 'Error');
					});
				},
				$scope.requestForAddtinalTime = function() {
					var searchProject = {
						projId: plantAttendanceMstrTO.projId,
						projName: plantAttendanceMstrTO.name
					};
					var plantAttendReq = {
						"status" : 1,
						"crewLabelKeyTO" : {
							"id" : plantAttendanceMstrTO.crewId,
							"code" : plantAttendanceMstrTO.crewName,
							"name" : null
						},
						"attendenceId" : plantAttendanceMstrTO.id,
						"attendenceCode":null,
						"attendenceMonth" : plantAttendanceMstrTO.attendanceMonth
					};
					var popup = PlantNotificationFactory.getPlantNotificationDetails(searchProject, plantAttendReq);
					popup.then(function(data) {
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
					})
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);