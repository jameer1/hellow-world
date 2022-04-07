'use strict';
app.factory('AttendancePlantCreateFactory', ["$q", "ngDialog", "blockUI", "PlantAttendanceService", "PlantAttendanceCopyFactory", "AttendancePlantRegFactory", "GenericAlertService", "generalservice", function($q, ngDialog,blockUI, PlantAttendanceService, PlantAttendanceCopyFactory, 
	AttendancePlantRegFactory, GenericAlertService, generalservice) {
	var dateWisePopUp;
	var createEmpservice = {};

	createEmpservice.getPlantCreateDetails = function(searchProject, plantAttendReq, plantAttendanceDays, plantAttendanceMap, plantDetailsMap) {
		var deferred = $q.defer();
		dateWisePopUp = ngDialog.open({
			template : 'views/timemanagement/attendance/plantcreateattendancepopup.html',
			className : 'ngdialog-theme-plain ng-dialogueCustom0',
			closeByDocument : false,
			showClose : false,
			controller : [ '$scope', function($scope) {
				var selectedPlantList = [];
				$scope.searchProject = searchProject;
				$scope.projId = searchProject.projId;
				$scope.plantAttendReq = plantAttendReq;
				$scope.plantAttendanceDays = plantAttendanceDays;
				$scope.plantAttendanceMap = plantAttendanceMap;
				$scope.plantDetailsMap = plantDetailsMap;
				$scope.plantAttendenceDetails = [];
				$scope.crewName = plantAttendReq.crewLabelKeyTO.code;
				$scope.attendenceMonth = plantAttendReq.attendenceMonth;
				$scope.plantAttendanceTypes = generalservice.plantAttendanceTypes;
				$scope.lessFlag = 0;
				$scope.moreFlag = 0;

				$scope.checkAttendance = function(attendanceTypeTO) {
					var inputvar = attendanceTypeTO.code.toUpperCase();
					var keyId = 0;
					var mapCode = null;
					angular.forEach($scope.plantAttendanceMap, function(value, key) {
						mapCode = value.code.toUpperCase();
						if (mapCode == inputvar) {
							keyId = value.id;
						}
					});
					if (keyId > 0) {
						attendanceTypeTO.id = keyId;
					} else {
						attendanceTypeTO.id = null;
					}
					attendanceTypeTO.code = inputvar;
				}

				

				 $scope.savePlantAttendanceRecords = function() {
					 if ($scope.plantAttendenceDetails <= 0 ) {
						GenericAlertService.alertMessage("please add atleast one plant to save  ", "Warning");
						return;
					}					 
					var attendReq = {
						"status" : 1,
						"projId" : $scope.searchProject.projId,
						"crewId" : $scope.plantAttendReq.crewLabelKeyTO.id,
						"attendenceId" : $scope.plantAttendReq.attendenceId,
						"attendenceMonth" : $scope.attendenceMonth,
						"plantAttendenceTOs" : $scope.plantAttendenceDetails
					};
					blockUI.start();
					PlantAttendanceService.savePlantAttendanceRecords(attendReq).then(function(data) {
						blockUI.stop();
						var resultData = GenericAlertService.alertMessageModal("Plant Attendance saved successfully", 'Info');
						resultData.then(function() {
							angular.forEach(data.plantAttendenceTOs, function(value, key) {
								angular.forEach(value.plantAttendenceDtlMap, function(value1, key1) {
									if ($scope.plantAttendanceMap[value1.attendanceTypeTO.id] != null) {
										value1.attendanceTypeTO.code = $scope.plantAttendanceMap[value1.attendanceTypeTO.id].code;
									}
								});
							});
							$scope.closeThisDialog();
							deferred.resolve(data);
						});
					}, function(error) {
						blockUI.stop();
						GenericAlertService.alertMessage("Error occured while gettting  Plant Attendance details", 'Error');
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
				}, $scope.addPlantRegDetails = function() {
					var plantPopUp = [];
					var plantExistingMap = [];
					angular.forEach($scope.plantAttendenceDetails, function(value, key) {
						plantExistingMap[value.plantId] = true;
					});
					plantPopUp = AttendancePlantRegFactory.getPlantRegDetails(plantExistingMap, $scope.projId, plantAttendReq);
					plantPopUp.then(function(data) {
						angular.forEach(data.plantAttendenceTOs, function(value, key) {
							$scope.plantAttendenceDetails.push(value);
						});
					}, function(error) {
						GenericAlertService.alertMessage("Error occured while gettting  Plants", 'Error');
					});
				}, $scope.getPlantCopyTemplate = function() {
					var plantAttendRegMap = [];
					var plantIds = [];
					angular.forEach($scope.plantAttendenceDetails, function(value, key) {
						plantAttendRegMap[value.plantId] = true;
						plantIds.push(value.plantId);
					});
					var popup = PlantAttendanceCopyFactory.copyAttendancePlantDetails($scope.projId, plantAttendReq, plantAttendRegMap);
					popup.then(function(data) {
						var showIgnoringDuplicateMessage = false;
						angular.forEach(data.plantAttendenceTOs, function(value, key) {
							if (!plantIds.includes(value.plantId))
								$scope.plantAttendenceDetails.push(value);
							else
								showIgnoringDuplicateMessage = true;
						});
						if (showIgnoringDuplicateMessage)
							GenericAlertService.alertMessage("Duplicate plant(s) were ignored", 'Warning');
					}, function(error) {
						GenericAlertService.alertMessage("Error occurred while copying employee  details", 'Error');
					})
				},
				$scope.rowselect = function(plantAttendance) {
					if (plantAttendance.select) {
						selectedPlantList.push(plantAttendance);
					} else {
						selectedPlantList.splice(selectedPlantList.indexOf(plantAttendance), 1);
					}
				}, $scope.deletePlantRecords = function() {
					if (selectedPlantList.length == 0) {
						GenericAlertService.alertMessage('Please select atleast one row to delete', "Warning");

					} else

					if (selectedPlantList.length < $scope.plantAttendenceDetails.length) {
						angular.forEach(selectedPlantList, function(value, key) {
							$scope.plantAttendenceDetails.splice($scope.plantAttendenceDetails.indexOf(value), 1);

						});
						selectedPlantList = [];
						GenericAlertService.alertMessage('Rows are deleted Successfully', "Info");
					} else if (selectedPlantList.length > 1) {
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					} else if (selectedPlantList.length == 1) {
						selectedPlantList = [];
						GenericAlertService.alertMessage('Please leave atleast one row', "Warning");
					}
				}

			} ]
		});
		return deferred.promise;
	}
	return createEmpservice;
}]);
