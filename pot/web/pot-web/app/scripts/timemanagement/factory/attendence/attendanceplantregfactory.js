'use strict';

app.factory('AttendancePlantRegFactory', ["ngDialog", "$q", "$filter", "blockUI", "$timeout", "PlantAttendanceService", "GenericAlertService", function(ngDialog, $q, $filter,blockUI, $timeout, PlantAttendanceService, GenericAlertService) {
	var service = {};
	service.getPlantRegDetails = function(plantExistingMap, projId, plantAttendReq) {
		var deferred = $q.defer();
		var req = {
			"status" : 1,
			"projId" : projId,
			"crewId" : plantAttendReq.crewLabelKeyTO.id,
			"attendenceId" : plantAttendReq.attendenceId,
			"attendenceMonth" : plantAttendReq.attendenceMonth
		};
		PlantAttendanceService.getPlantRegDetails(req).then(function(data) {
			var popup = service.openPopup(plantExistingMap, data.plantRegLabelKeyTOs, projId, plantAttendReq);
			popup.then(function(data) {
				deferred.resolve(data);
			}, function(error) {
				GenericAlertService.alertMessage('An error occurred while fetching Plant details', "Error");
			});
		}, function(error) {
			GenericAlertService.alertMessage('An error occurred while fetching Plant details', "Error");
		});
		return deferred.promise;

	}, service.openPopup = function(plantExistingMap, plantRegLabelKeyTOs, projId, plantAttendReq) {
		var deferred = $q.defer();
		var plantPopup = ngDialog.open({
			template : 'views/timemanagement/attendance/attendanceplantregdtl.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom1',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				$scope.plantRegLabelKeyTOs = plantRegLabelKeyTOs;
				$scope.plantExistingMap = plantExistingMap;
				var selectedPlantRegTOs = [];
				$scope.selectPlantRegDtls = function(plantRegLabelKeyTO) {
					if (plantRegLabelKeyTO.select) {
						selectedPlantRegTOs.push(plantRegLabelKeyTO);
					} else {
						selectedPlantRegTOs.splice(selectedPlantRegTOs.indexOf(plantRegLabelKeyTO), 1);
					}
				}, $scope.addPlantRegToAttendance = function() {
					var plantRegList = [];
					angular.forEach(selectedPlantRegTOs, function(value, key) {
						plantRegList.push({
							"attandanceId" : plantAttendReq.attendenceId,
							"plantId" : value.id,
							"projId" : projId,
							"crewId" : plantAttendReq.crewLabelKeyTO.id,
							"status" : 1
						});
					});
					if (plantRegList.length <= 0) {
						GenericAlertService.alertMessage('Please select Atleast one row', "Warning");
						return;
					}
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
							for (const selectedPlantRegTO of selectedPlantRegTOs) {
								if (selectedPlantRegTO.id === value.plantId) {
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
					});
				}
			} ]
		});
		return deferred.promise;
	};
	return service;
}]);