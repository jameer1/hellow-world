'use strict';

app.factory('PlantAttendanceCopyFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "$rootScope", "PlantAttendanceService", "ProjectCrewPopupService", "PlantAttendSheetsFactory", "GenericAlertService", function(ngDialog, $q, $filter, blockUI, $timeout, $rootScope, PlantAttendanceService, ProjectCrewPopupService, PlantAttendSheetsFactory, GenericAlertService) {
	var service = {};
	service.copyAttendancePlantDetails = function(projId, plantAttendReq, plantRegLabelKeyTOs, plantAttendRegMap) {
		var deferred = $q.defer();
		var popup = ngDialog.open({
			template : 'views/timemanagement/attendance/plantattendencecopy.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom2',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.plantRegLabelKeyTOs = plantRegLabelKeyTOs;
				$scope.plantAttendRegMap = plantAttendRegMap;
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

				var toMonth = new Date(plantAttendReq.attendenceMonth);
				$scope.attendanceKeyTO.attendanceCode=toMonth;
				$scope.attendanceKeyTO.attendanceCode.setMonth(toMonth.getMonth()-1);

				$scope.selectAll = false;
				$scope.crewLabelKeyTO.code=plantAttendReq.crewLabelKeyTO.code;
				
				$scope.plantRegLabelKeyTOs = [];
				var selectedPlantLabelKeyTOs = [];
				
				$scope.crewLabelKeyTO = [];
				$scope.crewLabelKeyTO = angular.copy(plantAttendReq.crewLabelKeyTO);

				$scope.getCrewList = function(crewLabelKeyTO) {
					$scope.searchFlag = false;
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

				}, $scope.getPlantAttendanceSheets = function() {
					$scope.searchFlag = false;
					if ($scope.crewLabelKeyTO.id <= 0) {
						GenericAlertService.alertMessage("Please select crew to get attendence ID(S)", 'Warning');
					} else {
						var plantAttendPopUp = PlantAttendSheetsFactory.getPlantAttendenceSheets(projId, $scope.crewLabelKeyTO.id);
						plantAttendPopUp.then(function(data) {
							$scope.attendanceKeyTO.attendanceCode = data.attendanceObj.code;
							$scope.attendanceKeyTO.attendanceId = data.attendanceObj.id;
						}, function(error) {
							GenericAlertService.alertMessage("Error occured while selecting attendence sheet", 'Error');
						});
					}
				}, $scope.getPlantDetails = function() {
					/*
					if ($scope.crewLabelKeyTO.id <= 0 || $scope.attendanceKeyTO.attendanceId <= 0) {
						GenericAlertService.alertMessage("Please select crew ID  and Attendance ID to get plant details", 'Error');
						return;
					}
					*/
					$scope.prevDate =  $filter("date")(new Date($scope.attendanceKeyTO.attendanceCode), "MMM-yyyy");
					var attendanceReq = {
						"status" : 1,
						"projId" : projId,
						"crewId" : $scope.crewLabelKeyTO.id,
						"attendenceId" : $scope.attendanceKeyTO.attendanceId,
						"attendenceMonth" : $scope.prevDate
					};

					var plantResults = PlantAttendanceService.copyAttendancePlantDetails(attendanceReq);
					plantResults.then(function(data) {
						$scope.searchFlag = true;
						$scope.plantRegLabelKeyTOs = data.plantRegLabelKeyTOs;
						if ($scope.plantRegLabelKeyTOs.length <= 0) {
							$scope.searchFlag = true;
						} else {
							$scope.searchFlag = false;
						}
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while selecting attendence sheet", 'Error');
					});
				}
				$scope.selectPlant = function(plantObj) {
					if (plantObj.select) {
						selectedPlantLabelKeyTOs.push(plantObj);
					} else {
						selectedPlantLabelKeyTOs.pop(plantObj);
					}
				}, $scope.selectAllPlants = function() {
					if ($scope.selectAll) {
						angular.forEach($scope.plantRegLabelKeyTOs, function(value, key) {
							value.select = true;
							selectedPlantLabelKeyTOs.push(angular.copy(value));
						});
					} else {
						angular.forEach($scope.plantRegLabelKeyTOs, function(value, key) {
							value.select = false;
						});
						selectedPlantLabelKeyTOs = [];
					}
				}, $scope.savePlantRegDetails = function() {
					var plantRegList = [];
					angular.forEach(selectedPlantLabelKeyTOs, function(value, key) {
						plantRegList.push({
							"attandanceId" : plantAttendReq.attendenceId,
							"plantId" : value.id,
							"projId" : projId,
							"crewId" : plantAttendReq.crewLabelKeyTO.id,
							"status" : 1
						});
					});

					var saveReg = {
						"plantAttendenceTOs" : plantRegList,
						"status" : 1,
						"projId" : projId,
						"crewId" : plantAttendReq.crewLabelKeyTO.id,
						"attendenceId" : plantAttendReq.attendenceId,
						"attendenceMonth" : plantAttendReq.attendenceMonth
					};
					blockUI.start();
					PlantAttendanceService.addPlantToAttendanceRecord(saveReg).then(function(data) {
						blockUI.stop();
						angular.forEach(data.plantAttendenceTOs, function(value) {
							for (const selectedPlantRegTO of selectedPlantLabelKeyTOs) {
								if (value.plantId === selectedPlantRegTO.id) {
									value.plantName = selectedPlantRegTO.name;
									value.plantCode = selectedPlantRegTO.code;
									value.classType = selectedPlantRegTO.displayNamesMap.classType;
									value.cmpCatgName = selectedPlantRegTO.displayNamesMap.cmpCatgName;
									value.plntManfature = selectedPlantRegTO.displayNamesMap.plntManfature;
									value.plntModel = selectedPlantRegTO.displayNamesMap.plntModel;
									value.plntRegNo = selectedPlantRegTO.displayNamesMap.plntRegNo;
									value.procureCatg = selectedPlantRegTO.displayNamesMap.procureCatg;
								}
							}
						});
						deferred.resolve(data);
						$scope.closeThisDialog();
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage('An error occurred while adding Plant details', "Error");
					})
				}

			} ]
		});
		return deferred.promise;

	}
	return service;

}]);
